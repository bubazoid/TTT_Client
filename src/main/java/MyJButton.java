import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sergey on 21.10.2016.
 */
public class MyJButton extends JButton{
    private final Desk desk;
    private int number;
    private int state = 0;


    public MyJButton(int i, final Desk desk) {
        this.desk = desk;
        this.number = i;
        setPreferredSize(new Dimension(66,66));
        setMaximumSize(new Dimension(66,66));
        setMaximumSize(new Dimension(66,66));
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (state == 0){
                    desk.setAction(number);
                    state = 1;
                    setText((desk.getMyMark() == Desk.X ? "X":"O"));
                    lockButton();
                }
            }
        });
    }
    public void lockButton(){
        setEnabled(false);
    }

    // Ход оппонента
    public void setState(){
        if (state == 0){
            setText((desk.getMyMark() == Desk.X ? "O":"X"));
            lockButton();
        }

    }
}
