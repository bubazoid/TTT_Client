import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Sergey on 21.10.2016.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Package pack = (Package) msg;
        if (pack != null) {
            switch (pack.getType()) {
                case Package.MESSAGE:
                    System.out.println(pack.getMessage());
                    break;
                case Package.STEP:
                    Client.makeOpponentStep(pack.getStep());
                    break;
                case Package.YOUR_STEP:
                    Client.yourStep();
                    break;
                case Package.CLOSE_GAME:
                    Client.closeGame(pack.getMessage());
                    break;
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
