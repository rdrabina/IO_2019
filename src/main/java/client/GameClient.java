package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jsoniter.*;

public class GameClient extends Thread{
    private final String address = "192.168.43.45";
    private final int port = 9999;

    private Socket socket;
    OutputStreamWriter  outputStream;
    DataInputStream inputStream;

    private final String serverAdd;
    private final int serverPort;
    private String login;

    public GameClient(String addres, int port) {
        super();
        this.serverAdd = addres;
        this.serverPort = port;
    }

    @Override
    public void run() {
        try {
            System.out.println("Game client started");
            initConnection();
            login("logggin");
            listenServer();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initConnection() throws IOException{
        socket = new Socket(address, port);//serverAdd, serverPort);
        outputStream = new OutputStreamWriter(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());

        System.out.println("Connected to the server");
    }

    private void login(String login) throws IOException{
        String loginJson =
                "{" +
                        "\"login\": \"" + login + "\"" +
                "}";
        outputStream.write(loginJson);
        outputStream.flush();
    }

    private void listenServer() throws IOException{
        byte[] buff = new byte[1024];
        int count;
        while ((count = inputStream.read(buff)) != 0) {
            byte[] msg = Arrays.copyOfRange(buff, 0, count);

            GameData gameData = JsonIterator.deserialize(msg, GameData.class);
            for (PlayerData pd: gameData.play)
                System.out.println(pd.id + " " + pd.x + " " + pd.y);
            for (PlanktonData pn: gameData.plan)
                System.out.println(pn.x + " " + pn.y);
        }
    }
}
class PlayerData {
    public int id;
    public int wght;
    public int x;
    public int y;
    public int xV;
    public int yV;
}
class PlanktonData {
    public int x;
    public int y;
}
class GameData {
    public List<PlayerData> play;
    public List<PlanktonData> plan;
}