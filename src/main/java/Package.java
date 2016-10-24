/**
 * Created by Sergey on 21.10.2016.
 */
public class Package implements java.io.Serializable{
    public static final int MESSAGE = 1;
    public static final int STEP = 2;
    public static final int CLOSE_GAME = 3;
    public static final int YOUR_STEP = 4;
    private String message;
    private int type;
    private int step;

    public Package(String message) {
        type = MESSAGE;
        this.message = message;
    }

    public Package(int type,String message) {
        this.type = type;
        this.message = message;
    }

    public Package(int type,int step) {
        this.type = type;
        this.step = step;
    }
    public Package(int type) {
        this.type = type;
    }



    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return message;
    }

    public int getStep() {
        return step;
    }

    public int getType() {
        return type;
    }
}
