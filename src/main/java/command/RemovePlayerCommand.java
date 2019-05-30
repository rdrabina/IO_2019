package command;
import game.Game;

public class RemovePlayerCommand implements Command {

    private String login;

    public RemovePlayerCommand(String login) {
        this.login = login;
    }

    @Override
    public void execute(Game game) {
        System.out.println("remove player command");
        //TODO remove player with this login
    }
}
