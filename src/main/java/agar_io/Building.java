package agar_io;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.*;

public class Building implements Serializable {

    private List<Rectangle2D.Double> buildings;
    private List<String> buildingsName;
    private List<Color> buildingsColors;
    private JSONArray arrayBuildings;


    Building() {
        arrayBuildings = JSONParser.parse();
        buildings = new ArrayList<>();
        buildingsName = new ArrayList<>();
        buildingsColors = new ArrayList<>();
        callOnce();
    }


    public void callOnce(){
        randomBuildingsColorInitializer();
        initializeBuildings();
    }

    public void randomBuildingsColorInitializer(){
        buildingsColors = IntStream.range(0, arrayBuildings.length())
                .mapToObj(i -> ColorHelper.getRandomColor())
                .collect(Collectors.toList());
    }

    public void drawBuildings(Graphics2D g2){
        IntStream.range(0, buildings.size()).forEach(i -> {
            g2.setColor(buildingsColors.get(i));
            g2.fill(buildings.get(i));
            g2.setColor(Color.WHITE);
            g2.drawString(buildingsName.get(i), (int)(buildings.get(i).x + buildings.get(i).width/2),
                    (int) (buildings.get(i).y + buildings.get(i).height/2));
        });
    }

    public void initializeBuildings(){
        IntStream.range(0, arrayBuildings.length()).forEach(i -> {
            JSONObject a;
            int startX;
            int startY;
            int endX;
            int endY;
            a = arrayBuildings.getJSONObject(i);

            startX = a.getInt("startX");
            startY = a.getInt("startY");
            endX = a.getInt("endX");
            endY = a.getInt("endY");

            buildingsName.add(i, a.getString("name"));
            buildings.add(i, new Rectangle2D.Double( startX, startY, endX-startX, endY-startY));
        });
    }
}