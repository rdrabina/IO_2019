package helpers.command;

import helpers.Position;

public class CommandFactory {
    private GameData gameData;
    private Invoker invoker;

    public CommandFactory(GameData gameData, Invoker invoker)
    {
        this.gameData = gameData;
        this.invoker = invoker;
    }

    public void getAddPlanktonCommands()
    {
        for(AddPlanktonData planktonData: gameData.addPlanktonData)
        {
            Integer x = planktonData.coordinates.get(0);
            Integer y = planktonData.coordinates.get(1);
            Position position = new Position(x, y);
            AddPlanktonCommand addPlanktonCommand = new AddPlanktonCommand(position);
            this.invoker.addCommand(addPlanktonCommand);
        }
    }

    public void getRemovePlanktonCommands()
    {
        for(RemovePlanktonData removePlankton: gameData.removePlanktonData)
        {
            Integer x = removePlankton.coordinates.get(0);
            Integer y = removePlankton.coordinates.get(1);
            Position position = new Position(x, y);
            RemovePlanktonCommand removePlanktonCommand = new RemovePlanktonCommand(position);
            this.invoker.addCommand(removePlanktonCommand);
        }
    }


    public void getAddPlayerCommands()
    {
        for(AddPlayerData addPlayerData: gameData.addPlayerData)
        {
            AddPlayerCommand addPlayerCommand = new AddPlayerCommand(addPlayerData);
            this.invoker.addCommand(addPlayerCommand);
        }
    }

    public void getRemovePlayerCommands()
    {
        for(RemovePlayerData removePlayerData: gameData.removePlayerData)
        {
            RemovePlayerCommand removePlayerCommand = new RemovePlayerCommand(removePlayerData.login);
            this.invoker.addCommand(removePlayerCommand);
        }
    }

    public Invoker getInvoker() {return invoker;}

}
