package helpers.command;
import game.Game;

public class AddPlayerCommand implements Command {

    private AddPlayerData addPlayerData;

    public AddPlayerCommand(AddPlayerData addPlayerData) {
        this.addPlayerData = addPlayerData;
    }

    @Override
    public void execute(Game game)
    {
        //TODO add player
    }
}
