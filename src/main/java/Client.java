import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sergey on 21.10.2016.
 */
public class Client {
    //
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8000"));
    private static Channel channel;
    private static Bootstrap b;
    private static Desk desk;
    private static int mark = Desk.X;
    private static MainFrame mainFrame;
    private static EventLoopGroup group;

    public static void main(String[] args) throws Exception {
        //
        mainFrame = new MainFrame();
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(
                                new ObjectEncoder(),
                                new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                new ClientHandler());
                    }
                });

        //=====================================================
        mainFrame.getNewGameButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        mainFrame.getMarkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (mark == Desk.X) {
                    mark = Desk.O;
                    mainFrame.getMarkButton().setText("O");
                } else {
                    mark = Desk.X;
                    mainFrame.getMarkButton().setText("X");
                }
            }
        });

    }

    private static void startNewGame() {
        mainFrame.setInfo("");
        mainFrame.unlockMarkButton(false);
        if (channel != null) {
            try {
                channel.writeAndFlush(new Package(Package.CLOSE_GAME)).sync();
                channel.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            channel = b.connect(HOST, PORT).sync().channel();
            desk = new Desk(mark);
            mainFrame.setDesk(desk);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendStep(int i){
        if (channel != null){
            channel.writeAndFlush(new Package(Package.STEP,i));
        }
        mainFrame.setInfo("");
    }

    public static void makeOpponentStep(int step) {
        desk.applyOpponentStep(step);
        mainFrame.repaint();
        mainFrame.getDeskPanel().revalidate();
    }

    public static void yourStep() {
        mainFrame.setInfo("Ваш ход");
        desk.unlockButtons(true);
    }

    public static void closeGame(String message) {
        desk.unlockButtons(false);
        if (channel != null){
            channel.close();
            channel = null;
        }
        JOptionPane.showMessageDialog(mainFrame, message);
        mainFrame.setInfo("Игра окончена");
        mainFrame.unlockMarkButton(true);
    }
    public static void exit(){
        if (channel != null){
            try {
                channel.close().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        }
    }
}


