package helpers.command;
import game.Game;

public class RemovePlayerCommand implements Command {

    private String login;

    public RemovePlayerCommand(String login) {
        this.login = login;
    }

    @Override
    public void execute(Game game) {
        //TODO remove player with this login
    }
}
