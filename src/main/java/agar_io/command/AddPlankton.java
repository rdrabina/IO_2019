package agar_io.command;

import agar_io.Game;
import agar_io.Position;

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
