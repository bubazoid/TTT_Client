import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergey on 21.10.2016.
 */
public class Desk extends JPanel {
    public static final int X = 1;
    public static final int O = 0;
    private int myMark = X;

    MyJButton[] buttons = new MyJButton[9];

    public Desk(int mark) {
        this.myMark = mark;
        setSize(220, 220);
        setPreferredSize(new Dimension(220, 220));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout());
        for (int i = 0; i <= 8; i++) {
            buttons[i] = new MyJButton(i, this);
            add(buttons[i]);
        }
        unlockButtons(false);
    }

    public void setAction(int i) {
        Client.sendStep(i);
        unlockButtons(false);
    }

    public int getMyMark() {
        return myMark;
    }

    public void unlockButtons(boolean flag) {
        for (int i = 0; i <= 8; i++) {
            buttons[i].setEnabled(flag);
        }
    }

    public void applyOpponentStep(int step) {
        buttons[step].setState();
    }
}
