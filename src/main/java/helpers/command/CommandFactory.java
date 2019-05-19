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

    public List<AddPlankton> getPlanktonCommands()
    {
        List<AddPlankton> addPlanktonCommands = new ArrayList<>();

        for(AddPlanktonData planktonData: gameData.addPlanktonData)
        {
            Integer x = planktonData.coordinates.get(0);
            Integer y = planktonData.coordinates.get(1);
            Position position = new Position(x, y);
            AddPlankton addPlanktonCommand = new AddPlankton(position);
            addPlanktonCommands.add(addPlanktonCommand);
        }
        return addPlanktonCommands;
    }

}
