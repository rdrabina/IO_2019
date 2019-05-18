package agar_io.Model;


import java.util.List;

public class Game {

    private List<Player> players;
    private List<Food> food;

    // Players
    public List<Player> getPlayers() {
        return players;
    }

    public void removePlayer(String id) {
        players.removeIf(player -> player.id.equals(id));
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    // Food
    public void removeFood(Position p) {
        food.removeIf(f -> f.position.equals(p));
    }

    public void addFood(Food f) {
        food.add(f);
    }


}
