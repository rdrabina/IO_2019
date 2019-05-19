package helpers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.StringJoiner;

public class JSONParser {
    private final static String JSON_PATH = "building.json";

    public static JSONArray parse(){
        StringJoiner sj;
        try {
            InputStream is = new FileInputStream(JSON_PATH);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line;
            line = buf.readLine();
            sj = new StringJoiner("\n");
            while(line != null){
                sj.add(line);
                line = buf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String fileAsString = sj.toString();

        JSONObject obj = new JSONObject(fileAsString);

        return obj.getJSONArray("buildings");
    }

}
