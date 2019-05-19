package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class ServerAccesor {
    private OutputStreamWriter outputStream;
    private DataInputStream inputStream;

    public synchronized void setStreams(DataInputStream inputStream, OutputStreamWriter outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public synchronized byte[] recv() throws IOException {
        byte[] buff = new byte[1024];
        int count;
        if (inputStream != null)
        {
            count = inputStream.read(buff);
            buff = Arrays.copyOfRange(buff, 0, count);
        }
        return buff;
    }

    public synchronized void send(String msg) throws IOException {
        if (outputStream == null) return;
        outputStream.write(msg);
        outputStream.flush();
    }
}
