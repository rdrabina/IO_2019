package command;

import game.Game;
import helpers.Position;
import player.Player;
import player.PlayerIdentification;


public class AddPlayerCommand implements Command {

    private AddPlayerData addPlayerData;

    public AddPlayerCommand(AddPlayerData addPlayerData) {
        this.addPlayerData = addPlayerData;
    }

    @Override
    public void execute(Game game) {
        if (!addPlayerData.login.equalsIgnoreCase(game.getPlayer().getIdentification().getNick())) {
            PlayerIdentification playerIdentification = new PlayerIdentification(addPlayerData.login, "");
            Position position = new Position(addPlayerData.coordinates.get(0), addPlayerData.coordinates.get(1));
            Player player = new Player(playerIdentification, position, addPlayerData.size, addPlayerData.velocity, addPlayerData.angle);
            game.getPlayers().put(addPlayerData.login, player);
        }
    }
}
