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

    @Override
    public void execute(Game game) {

        //todo extension receiving faculty from server
        if (!updatePlayerData.login.equalsIgnoreCase(game.getPlayer().getIdentification().getNick())) {
            //faculty hardcoded
            PlayerIdentification playerIdentification = new PlayerIdentification(updatePlayerData.login, College.AGH);
            Position position = new Position(updatePlayerData.coordinates.get(0), updatePlayerData.coordinates.get(1));
            Player player = new Player(playerIdentification, position, updatePlayerData.size, updatePlayerData.velocity,
                    updatePlayerData.angle);
            game.getPlayers().put(updatePlayerData.login, player);
        } else {
            Player player = game.getPlayer();
            player.setAngle(updatePlayerData.angle);
            player.updateSize(updatePlayerData.size);
            player.updateVelocity(updatePlayerData.size);
            player.updatePosition(new Position(updatePlayerData.coordinates.get(0), updatePlayerData.coordinates.get(1)));
        }
    }
}
