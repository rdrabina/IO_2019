package command;


import game.Game;
import helpers.Position;

public class AddPlanktonCommand implements Command {

    private final Position position;

    public AddPlanktonCommand(Position position) {
        this.position = position;
    }

    @Override
    public void execute(Game game) {
        game.getFood().addSingleFood(position);
    }
}
