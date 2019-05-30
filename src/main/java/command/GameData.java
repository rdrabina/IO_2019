package command;

import java.util.List;

class AddPlanktonData {
    public List<Integer> coordinates;
    public Integer weight;
}
class RemovePlanktonData {
    public List<Integer> coordinates;
}
class AddPlayerData {
    public String login;
    public List<Integer> coordinates;
    public Integer size;
    public Double direction;
    public Integer velocity;
}
class RemovePlayerData {
    public String login;
}
class UpdatePlayerData {
    public String login;
    public List<Integer> coordinates;
    public Integer weight;
    public Double direction;
    public Integer velocity;
}

public class GameData {
    public List<AddPlanktonData> addPlankton;
    public List<RemovePlanktonData> removePlankton;
    public List<AddPlayerData> addPlayer;
    public List<RemovePlayerData> removePlayer;
    public List<UpdatePlayerData> updatePlayer;
}
