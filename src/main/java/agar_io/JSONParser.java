package agar_io;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class JSONParser {
    private final static String JSON_PATH = "building.json";

    static JSONArray parse(){
        StringBuilder sb;
        try {
            InputStream is = new FileInputStream(JSON_PATH);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line;
            line = buf.readLine();
            sb = new StringBuilder();
            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String fileAsString = sb.toString();

        JSONObject obj = new JSONObject(fileAsString);

        return obj.getJSONArray("buildings");
    }

}
