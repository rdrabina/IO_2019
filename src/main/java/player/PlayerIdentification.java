package player;

import java.io.Serializable;

public class PlayerIdentification implements Serializable {

    private String nick;

    private String faculty;

    public PlayerIdentification(String nick, String faculty) {
        this.nick = nick;
        this.faculty = faculty;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
