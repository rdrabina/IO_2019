package client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import agar_io.Food;
import agar_io.Game;
import agar_io.Player;
import agar_io.PlayerIdentification;
import agar_io.command.Command;
import agar_io.command.CommandFactory;
import agar_io.command.Invoker;
import com.jsoniter.*;

public class GameClient extends Thread{

    private Socket socket;
    private OutputStreamWriter  outputStream;
    private DataInputStream inputStream;

    private final String serverAdd;
    private final int serverPort;
    private String login;
    private String faculty;
    private Invoker invoker;

    private Game game;

    public GameClient(String addres, int port, PlayerIdentification ind, Game game) {
        this.serverAdd = addres;
        this.serverPort = port;
        this.login = ind.getNick();
        this.faculty = ind.getFaculty();
        this.game = game;
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

        String a = "{\"players\": {\"login23\": {\"coordinates\": [5, 5], \"weight\": 3, \"login\": \"login23\", " +
                "\"department\": \"s\", \"image\": \"s\", \"velocity\": 0, \"direction\": [0, 0]}}, \"plankton\": []}";

        GameData gameData = JsonIterator.deserialize(a, GameData.class);
        for (PlayerData pd: gameData.players.values())
            System.out.println(pd.coordinates + " " + pd.wieght + " " + pd.login);
        for (PlanktonData pn: gameData.plankton)
            System.out.println(pn.x + " " + pn.y);
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
            System.out.println(new String(msg));

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

    private void updateModel(GameData data) {

    }
}
class PlayerData {
    public List<Integer> coordinates;
    public int wieght;
    public String login;
    public String department;
    public String image;
    public int velocity;
}
class PlanktonData {
    public int x;
    public int y;
}
class GameData {
    public Map<String, PlayerData> players;
    public List<PlanktonData> plankton;
}