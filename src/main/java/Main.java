import client.GameClient;
import game.GameState;

public class Main {

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.run();
        try{
        while (true) Thread.sleep(100);
        } catch (Exception e) {}
    }
}
