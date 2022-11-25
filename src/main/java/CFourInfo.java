import java.io.Serializable;

public class CFourInfo implements Serializable {
    public boolean p1turn;
    public boolean p1won;
    public boolean p2won;
    public boolean have2players;
    public int p1moveX;
    public int p1moveY;
    public int p2moveX;
    public int p2moveY;
    public String p1Messages;
    public String p2Messages;
    public String errorMessages;

    static final long serialVersionUID = 55L;

    CFourInfo(){
        p1turn = true;
        p1won = false;
        p2won = false;
        have2players = false;
        p1moveX = -1;
        p1moveY = -1;
        p2moveX = -1;
        p2moveY = -1;
        p1Messages = "";
        p2Messages = "";
        errorMessages = "";
    }
}
