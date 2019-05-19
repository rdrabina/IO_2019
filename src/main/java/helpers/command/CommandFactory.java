package helpers.command;

import helpers.Position;

import java.util.ArrayList;
import java.util.List;

public class CommandFactory {
    private GameData gameData;

    public CommandFactory(GameData gameData)
    {
        this.gameData = gameData;
    }

    public List<AddPlanktonCommand> getPlanktonCommands()
    {
        List<AddPlanktonCommand> addPlanktonCommands = new ArrayList<>();

        for(AddPlanktonData planktonData: gameData.addPlanktonData)
        {
            Integer x = planktonData.coordinates.get(0);
            Integer y = planktonData.coordinates.get(1);
            Position position = new Position(x, y);
            AddPlanktonCommand addPlanktonCommand = new AddPlanktonCommand(position);
            addPlanktonCommands.add(addPlanktonCommand);
        }
        return addPlanktonCommands;
    }

    public List<RemovePlanktonCommand> removePlanktonCommands()
    {
        List<RemovePlanktonCommand> removePlanktonCommands = new ArrayList<>();

        for(RemovePlanktonData removePlankton: gameData.removePlanktonData)
        {
            Integer x = removePlankton.coordinates.get(0);
            Integer y = removePlankton.coordinates.get(1);
            Position position = new Position(x, y);
            RemovePlanktonCommand removePlanktonCommand = new RemovePlanktonCommand(position);
            removePlanktonCommands.add(removePlanktonCommand);
        }
        return removePlanktonCommands;
    }



}
