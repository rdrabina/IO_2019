package command;

import game.Game;
import helpers.Position;
import menu.College;
import player.Player;
import player.PlayerIdentification;

public class UpdatePlayerDataCommand implements Command{
    private UpdatePlayerData updatePlayerData;

    public UpdatePlayerDataCommand(UpdatePlayerData updatePlayerData) {
        this.updatePlayerData = updatePlayerData;
    }

    private boolean isCurrentPlayer (Game game)
    {
        String nick = game.getPlayer().getIdentification().getNick();
        String receivedNick = updatePlayerData.login;
        if(nick.equalsIgnoreCase(receivedNick))
            return true;
        else
            return false;
    }

    @Override
    public void execute(Game game) {
        System.out.println("update player data command");
        //todo extension receiving faculty from server
        if (isCurrentPlayer(game)) {
            Player player = game.getPlayer();
            player.setAngle(updatePlayerData.direction);
            player.updateSize(updatePlayerData.weight);
            player.updateVelocity(updatePlayerData.weight);
            player.updatePosition(new Position(updatePlayerData.coordinates.get(0), updatePlayerData.coordinates.get(1)));
            game.updatePlayer(player);
        } else {
            //faculty hardcoded
            PlayerIdentification playerIdentification = new PlayerIdentification(updatePlayerData.login, College.AGH);
            Position position = new Position(updatePlayerData.coordinates.get(0), updatePlayerData.coordinates.get(1));
            Player player = new Player(playerIdentification, position, updatePlayerData.weight, updatePlayerData.velocity,
                    updatePlayerData.direction);
            game.getPlayers().put(updatePlayerData.login, player);
        }
    }
}
