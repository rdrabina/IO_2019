package agar_io.command;

import agar_io.Game;

import java.util.LinkedList;
import java.util.List;

public class Invoker {

    private final List<Command> commands;

    public Invoker() {
        this.commands = new LinkedList<>();
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public void executeCommands(Game game) {
        this.commands.forEach(c -> c.execute(game));
    }
}
