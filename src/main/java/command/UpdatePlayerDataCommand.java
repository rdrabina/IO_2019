package command;

import game.Game;

public class UpdatePlayerDataCommand implements Command
{
    private UpdatePlayerData updatePlayerData;

    public UpdatePlayerDataCommand(UpdatePlayerData updatePlayerData) {
        this.updatePlayerData = updatePlayerData;
    }

    @Override
    public void execute(Game game)
    {
        //TODO update player data
    }
}
