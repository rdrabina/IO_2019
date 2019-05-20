package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

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
    private final ServerAccesor accesor;
    private Invoker invoker;

    private final Semaphore semaphore;
    private Game game;

    public GameClient(String addres, int port, PlayerIdentification ind, Game game, Semaphore semaphore) {
        this.semaphore = semaphore;
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
    }

    private void listenServer() throws IOException{
        while (true) {
            String m = recv().replace("'", "\"");
            String msg = m.substring(0, m.length()-1);

            GameData gameData = JsonIterator.deserialize(msg, GameData.class);
            CommandFactory commandFactory = new CommandFactory(gameData, invoker);

            commandFactory.generateCommandsFromGameData();
            invoker = commandFactory.getInvoker();
            invoker.executeCommands(game);
        }
    }

    private void updateServer(Player player) {
        semaphore.acquireUninterruptibly();

        String serverUpdate = "{" +
                    "'update': [] , " +
                    "'coordinates': [" +  player.getFirstExistingBall().get().getX() + ", " +
                                        player.getFirstExistingBall().get().getY() + "]," +
                    "'direction': " + player.getAngle() +
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
        byte[] buff = new byte[1024];
        int count;
        StringBuilder b = new StringBuilder();
        while ((count = inputStream.read(buff)) > 0)
            b.append(new String(Arrays.copyOfRange(buff, 0, count)));
        return b.toString();
    }

    private void send(String msg) throws IOException {
        outputStream.write(msg);
        outputStream.flush();
    }
}
