package leaderboard;

import constant.Constants;
import helpers.Position;
import player.Player;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static constant.Constants.FONT;

public class Leaderboard {

    static HashMap<String, Player> map = new HashMap<>();

    static void initializeMap() {
        map.put("login1", new Player(null, new Position(500.0, 500.0), 20, 20, 20.0));
        map.put("login2", new Player(null, new Position(500.0, 500.0), 25, 20, 20.0));
        map.put("login3", new Player(null, new Position(500.0, 500.0), 15, 20, 20.0));
        map.put("login4", new Player(null, new Position(500.0, 500.0), 30, 20, 20.0));
        map.put("login5", new Player(null, new Position(500.0, 500.0), 100, 20, 20.0));
    }

    private static class MapHelper {
        String login;
        int size;

        public MapHelper(String login, int size) {
            this.login = login;
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public static void printLeaderboard(Graphics2D g2, HashMap<String, Player> players, Player player) {
        initializeMap();
        players = map;
        g2.setColor(Color.BLUE);
        Font font= new Font(FONT, Font.BOLD,25);
        g2.setFont(font);
        AtomicInteger atomicInteger = new AtomicInteger(- Constants.ACTIVE_HEIGHT_START + 100);
        g2.drawString("LEADERBOARD: ", (int) (player.getPlayer().x + Constants.ACTIVE_WIDTH_START - 300),
                (int) (player.getPlayer().y + atomicInteger.getAndAdd(50)));
        List<MapHelper> mapHelpers = players.entrySet().stream()
                .map(entry -> new MapHelper(entry.getKey(), entry.getValue().getFirstExistingBall().get().getSize()))
                .sorted(Comparator.comparing(MapHelper::getSize).reversed())
                .limit(3)
                .collect(Collectors.toList());

        IntStream.range(1, mapHelpers.size() + 1)
                .forEach(i -> g2.drawString(i + ". " + mapHelpers.get(i - 1).login + " size: " + mapHelpers.get(i - 1).size,
                        (int) (player.getPlayer().x + Constants.ACTIVE_WIDTH_START - 250), (int) (player.getPlayer().y + atomicInteger.getAndAdd(35))));
    }
}
