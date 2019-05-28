package player;

import menu.College;

import java.io.Serializable;

public class PlayerIdentification implements Serializable {

    private String nick;

    private College college;

    public PlayerIdentification(String nick, College college) {
        this.nick = nick;
        this.college = college;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}
