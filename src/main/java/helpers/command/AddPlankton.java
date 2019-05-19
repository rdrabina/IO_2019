package helpers.command;


import game.Game;
import helpers.Position;

public class AddPlankton implements Command {

    private final Position position;

    public AddPlankton(Position position) {
        this.position = position;
    }

    @Override
    public void execute(Game game) {
        game.getFood().addSingleFood(position);
    }
}
