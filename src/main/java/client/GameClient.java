package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import game.Game;
import player.Player;
import player.PlayerIdentification;
import com.jsoniter.*;

public class GameClient extends Thread{

    private Socket socket;
    private OutputStreamWriter  outputStream;
    private DataInputStream inputStream;

    private final String serverAdd;
    private final int serverPort;
    private final String login;
    private final String faculty;

    private Game game;

    public GameClient(String addres, int port, PlayerIdentification ind, Game game) {
        this.serverAdd = addres;
        this.serverPort = port;
        this.login = ind.getNick();
        this.faculty = ind.getFaculty();
        this.game = game;
    }

    @Override
    public void run() {
        try {
            System.out.println("Game client started");
            initConnection();
            login();
            listenServer();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initConnection() throws IOException{
        socket = new Socket(serverAdd, serverPort);
        outputStream = new OutputStreamWriter(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());

        System.out.println("Connected to the server");
    }

    private void login() throws IOException{
        String loginJson =
                "{" +
                        "\"login\": \"" + login + "\"" +
                "}";
        outputStream.write(loginJson);
        outputStream.flush();
    }

    private void listenServer() throws IOException{
        while (true) {
            byte[] msg = recv();
            String m = new String(msg).replace("'", "\"");
            System.out.println(m);

            GameData gameData = JsonIterator.deserialize(m, GameData.class);
            updateServer(game.getPlayer());
        }
    }

    private void updateServer(Player player) {
        String serverUpdate = "{" +
                    "'update': [] , " +
                    "'coordinates': [" +  player.getFirstExistingBall().get().getX() + ", " +
                                        player.getFirstExistingBall().get().getY() + "]," +
                    "'direction': " + player.getAngle() +
                "}";
        try {
            send(serverUpdate);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private byte[] recv() throws IOException {
        byte[] buff = new byte[1024];
        int count;
        if (inputStream != null)
        {
            count = inputStream.read(buff);
            buff = Arrays.copyOfRange(buff, 0, count);
        }
        return buff;
    }

    private void send(String msg) throws IOException {
        outputStream.write(msg);
        outputStream.flush();
    }
}
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

class GameData {
    public List<AddPlanktonData> addPlanktonData;
    public List<RemovePlanktonData> removePlanktonData;
    public List<AddPlayerData> addPlayerData;
    public List<RemovePlayerData> removePlayerData;
    public List<UpdatePlayerData> updatePlayerData;
}