package client;

import java.io.*;
import java.net.Socket;
//import com.jsoniter;
import com.jsoniter.*;

public class GameClient extends Thread{
    private final String address = "192.168.43.45";
    private final int port = 9999;

    private Socket socket;
    OutputStreamWriter  outputStream;
    DataInputStream inputStream;


    @Override
    public void run() {
        try {
            System.out.println("Game client started");
            socket = new Socket(address, port);
            outputStream = new OutputStreamWriter(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());

            System.out.println("Connected to the server");
            outputStream.write("{\"aa\":34}");
            outputStream.flush();
            byte[] buff = new byte[1024];
            while (inputStream.read(buff) != 0) {
//                JsonIterator.deserialize(msg, int[].class);
                Jsoniter json = Jsoniter.parse(buff);
//                JsonSt
                System.out.println(json.currentBuffer());
                Thread.sleep(1000);
                outputStream.write("{\"aa\":34}");
                outputStream.flush();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
