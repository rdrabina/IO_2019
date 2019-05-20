package command;


import game.Game;
import helpers.Position;

public class RemovePlanktonCommand implements Command {

    private final Position position;

    public RemovePlanktonCommand(Position position) {
        this.position = position;
    }

    @Override
    public void execute(Game game) { game.getFood().removeFood(position); }
}
