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
    public Double angle;
    public Integer velocity;
}
class RemovePlayerData {
    public String login;
}
class UpdatePlayerData {
    public String login;
    public List<Integer> coordinates;
    public Integer size;
    public Double angle;
    public Integer velocity;
}

public class GameData {
    public List<AddPlanktonData> addPlanktonData;
    public List<RemovePlanktonData> removePlanktonData;
    public List<AddPlayerData> addPlayerData;
    public List<RemovePlayerData> removePlayerData;
    public List<UpdatePlayerData> updatePlayerData;
}
