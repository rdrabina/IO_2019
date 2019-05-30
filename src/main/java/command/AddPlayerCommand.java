package command;

import game.Game;
import helpers.Position;
import menu.College;
import player.Player;
import player.PlayerIdentification;


public class AddPlayerCommand implements Command {

    private AddPlayerData addPlayerData;

    public AddPlayerCommand(AddPlayerData addPlayerData) {
        this.addPlayerData = addPlayerData;
    }

    @Override
    public void execute(Game game) {
        System.out.println("add player command");
        if (!addPlayerData.login.equalsIgnoreCase(game.getPlayer().getIdentification().getNick())) {
            //todo like in other commands
            PlayerIdentification playerIdentification = new PlayerIdentification(addPlayerData.login, College.AGH);
            Position position = new Position(addPlayerData.coordinates.get(0), addPlayerData.coordinates.get(1));
            Player player = new Player(playerIdentification, position, addPlayerData.size, addPlayerData.velocity, addPlayerData.direction);
            game.getPlayers().put(addPlayerData.login, player);
        }
    }
}
