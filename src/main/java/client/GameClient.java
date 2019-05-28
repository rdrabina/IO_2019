package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

import command.CommandFactory;
import command.GameData;
import command.Invoker;
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
    private Invoker invoker;

    private final Semaphore semaphore;
    private Game game;

    public GameClient(String addres, int port, Game game, Semaphore semaphore) {
        this.semaphore = semaphore;
        this.serverAdd = addres;
        this.serverPort = port;
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
    }

    private void initConnection() throws IOException{
        socket = new Socket(serverAdd, serverPort);
        outputStream = new OutputStreamWriter(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());

        System.out.println("Connected to the server");
    }

    private void login() throws IOException {
        Player player = game.getPlayer();
        PlayerIdentification identification = player.getIdentification();
        String loginJson =
                "{" +
                        "\"login\": \"" + identification.getNick() + "\"" +
                        "\"college\": \"" +  identification.getCollege() + "\"" +
                "}";
        send(loginJson);
    }

    private void listenServer() throws IOException{
        while (true) {
            String m = recv().replace("'", "\"");
            String msg = m.substring(1, m.length()-1);
            System.out.println(msg);

            GameData gameData = JsonIterator.deserialize(msg, GameData.class);
            CommandFactory commandFactory = new CommandFactory(gameData, invoker);

            commandFactory.generateCommandsFromGameData();
            invoker = commandFactory.getInvoker();

            semaphore.acquireUninterruptibly();
            invoker.executeCommands(game);
            semaphore.release();

            updateServer(game.getPlayer());
        }
    }

    private void updateServer(Player player) {
        semaphore.acquireUninterruptibly();

        String serverUpdate = "{" +
                    "\"update\": [] , " +
                    "\"coordinates\": [" +  player.getFirstExistingBall().get().getX() + ", " +
                                        player.getFirstExistingBall().get().getY() + "]," +
                    "\"direction\": " + player.getAngle() +
                "}";
        semaphore.release();
        try {
            send(serverUpdate);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String recv() throws IOException {
        int len = 1024;
        byte[] buff = new byte[len];
        int count;
        StringBuilder b = new StringBuilder();
        do {
            count = inputStream.read(buff);
            b.append(new String(Arrays.copyOfRange(buff, 0, count)));
        } while (count == len);
        return b.toString();
    }

    private void send(String msg) throws IOException {
        outputStream.write(msg);
        outputStream.flush();
    }
}
