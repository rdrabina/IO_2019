package map.contents;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import helpers.ColorHelper;
import helpers.JSONParser;
import org.json.*;
import player.Player;

public class Building implements Serializable {

    private List<Rectangle2D.Double> buildings;
    private List<String> buildingsName;
    private List<Color> buildingsColors;
    private JSONArray arrayBuildings;


    public Building() {
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

    public boolean isBuildingOnPlayerXWay(Player player, Double dx) {
        return IntStream.range(0, buildings.size())
                .anyMatch(i -> isPlayerIntersectsWithBuildingAndTryingMoveOnIt_X(player, dx, i));
    }

    private boolean isPlayerIntersectsWithBuildingAndTryingMoveOnIt_X (Player player, Double dx, int i) {

    return player.getPlayer().getBounds().intersects(buildings.get(i).getBounds())
            && (buildings.get(i).x > player.getPlayer().x && dx > 0
            || (buildings.get(i).x + buildings.get(i).width - player.getPlayer().width) < player.getPlayer().x && dx < 0);
}

    public boolean isBuildingOnPlayerYWay(Player player, Double dy) {
        return IntStream.range(0, buildings.size())
                .anyMatch(i -> isPlayerIntersectsWithBuildingAndTryingMoveOnIt_Y(player, dy, i));
    }

    private boolean isPlayerIntersectsWithBuildingAndTryingMoveOnIt_Y (Player player, Double dy, int i) {

        return player.getPlayer().getBounds().intersects(buildings.get(i).getBounds())
                && (buildings.get(i).y > player.getPlayer().y && dy > 0
                || (buildings.get(i).y + buildings.get(i).height - player.getPlayer().height) < player.getPlayer().y && dy < 0);
    }


}