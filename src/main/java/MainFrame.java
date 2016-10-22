import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Sergey on 21.10.2016.
 */
public class MainFrame extends JFrame {
    private JPanel rootPanel;
    private JButton newGameButton;
    private JButton markButton;
    private JPanel deskPanel;
    private JLabel info;
    private JButton close;
    private JPanel topPanel;
    private int pX, pY;

    public MainFrame() throws HeadlessException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setSize(220, 270);
        setResizable(false);
        setLocationRelativeTo(null);
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        setUndecorated(true);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Client.exit();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Client.exit();
            }
        });
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        topPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Get x,y and store them
                pX = e.getX();
                pY = e.getY();
            }

            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,
                        getLocation().y + me.getY() - pY);
            }
        });

        topPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {

                setLocation(getLocation().x + me.getX() - pX,
                        getLocation().y + me.getY() - pY);
            }
        });
    }


    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getMarkButton() {
        return markButton;
    }

    public JPanel getDeskPanel() {
        return deskPanel;
    }

    public JButton getClose() {
        return close;
    }


    public void setDesk(Desk desk) {
        deskPanel.removeAll();
        deskPanel.add(desk);
        deskPanel.repaint();
        deskPanel.revalidate();
    }

    public void clearDesk() {
        deskPanel.removeAll();
        deskPanel.repaint();
        deskPanel.revalidate();
    }

    public void setInfo(String text){
        info.setText(text);
    }

    public void unlockMarkButton(boolean b) {
        markButton.setEnabled(b);
    }
}
