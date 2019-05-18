package client;

import java.io.*;
import java.net.Socket;

public class GameClient extends Thread{
    private final String address = "192.168.43.45";
    private final int port = 50000;


//    private ObjectOutputStream outputStream;
//    private ObjectInputStream inputStream;

    private Socket socket;


    @Override
    public void run() {
        try {
            System.out.println("ready to conn");
            socket = new Socket(address, port);
            System.out.println("conn");
//            outputStream = new ObjectOutputStream(socket.getOutputStream());
//            inputStream = new ObjectInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("conn2");
            out.write("aaa");
            while (true)
            {
                String msg = in.readLine();
                System.out.println(msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
