package agar_io;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.Random;

import org.json.*;

public class Building implements Serializable {

    private Rectangle2D.Double[] buildings;
    private String[] buildingsName;
    private Color[] buildingsColors;
    private JSONArray arrayBuildings;


    Building() {
        arrayBuildings = JSONParser.parse();
        buildings = new Rectangle2D.Double[arrayBuildings.length()];
        buildingsName = new String[arrayBuildings.length()];
        buildingsColors = new Color[arrayBuildings.length()];
        callOnce();
    }


    public void callOnce(){
        randomBuildingsInitializer();
        initializeBuildings();
    }

    public void randomBuildingsInitializer(){
        Random a=new Random();

        for (int i = 0; i < buildingsColors.length; i++) {
            buildingsColors[i]=new Color(a.nextInt(255),a.nextInt(255),a.nextInt(255));
        }
    }

    public void drawBuildings(Graphics2D g2){
        for (int i = 0; i < buildings.length; i++) {
                g2.setColor(buildingsColors[i]);
                g2.fill(buildings[i]);
                g2.setColor(Color.WHITE);
                g2.drawString(buildingsName[i], (int)(buildings[i].x + buildings[i].width/2), (int) (buildings[i].y + buildings[i].height/2));
        }
    }

    public void initializeBuildings(){
        JSONObject a;
        int startX;
        int startY;
        int endX;
        int endY;

        for (int i = 0; i < buildings.length; i++) {

            a = arrayBuildings.getJSONObject(i);

            startX = a.getInt("startX");
            startY = a.getInt("startY");
            endX = a.getInt("endX");
            endY = a.getInt("endY");

            buildingsName[i] = a.getString("name");
            buildings[i] = new Rectangle2D.Double( startX, startY, endX-startX, endY-startY);
        }
    }


    public Rectangle2D.Double[] getBuildings() {
        return buildings;
    }
    public void setBuildings(Rectangle2D.Double[] buildings) {
        this.buildings = buildings;
    }
    public Color[] getBuildingsColors() {
        return buildingsColors;
    }
    public void setBuildingsColors(Color[] buildingsColors) {
        this.buildingsColors = buildingsColors;
    }
}