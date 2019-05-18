package client;

import java.io.*;
import java.net.Socket;
//import com.jsoniter;

public class GameClient extends Thread{
    private final String address = "localhost";
    private final int port = 9999;

    private Socket socket;
    OutputStreamWriter  outputStream;
    BufferedReader inputStream;


    @Override
    public void run() {
        try {
            System.out.println("ready to conn");
            socket = new Socket(address, port);
            outputStream = new OutputStreamWriter(socket.getOutputStream());
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("conn");
            outputStream.write("{\"aa\":34}");
            outputStream.flush();
            String msg;
            while ((msg = inputStream.readLine()) != null) {
//                JsonIterator.deserialize(msg, int[].class);
                System.out.println(msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
