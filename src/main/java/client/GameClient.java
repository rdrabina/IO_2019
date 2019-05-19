package client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import game.Game;
import helpers.command.Command;
import helpers.command.Invoker;
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
    private final ServerAccesor accesor;
    private Invoker invoker;

    private Game game;

    public GameClient(String addres, int port, PlayerIdentification ind, Game game, ServerAccesor accesor) {
        this.serverAdd = addres;
        this.serverPort = port;
        this.login = ind.getNick();
        this.faculty = ind.getFaculty();
        this.game = game;
        this.accesor = accesor;
        this.invoker = new Invoker();
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

        accesor.setStreams(inputStream, outputStream);
    }

    private void listenServer() throws IOException{
        while (true) {
            byte[] msg = accesor.recv();
            String m = new String(msg).replace("'", "\"");
            System.out.println(m);

            GameData gameData = JsonIterator.deserialize(msg, GameData.class);
//            updateModel(gameData);
            //TODO parse string to list of commands -> command factory
            //TODO receive list of commands
            //TODO for command in commands: invoker.storeCommand
            ArrayList<Command> commandList = new ArrayList<>();
            //TODO commandList = ...
            for(Command command: commandList)
                invoker.addCommand(command);
            invoker.executeCommands(game);
        }
    }

    private byte[] recv() throws IOException {
        byte[] buff = new byte[1024];
        int count = inputStream.read(buff);
        return Arrays.copyOfRange(buff, 0, count);
    }
//    private void updateModel(GameData data) {

//    }

    public static void updateServer(ServerAccesor accesor, Player player) {
        String serverUpdate = "{" +
                    "coordinates: [" +  player.getFirstExistingBall().get().getX() + ", " +
                                        player.getFirstExistingBall().get().getY() + "]," +
                    "direction: " + player.getAngle() +
                "}";
        try {
            accesor.send(serverUpdate);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
