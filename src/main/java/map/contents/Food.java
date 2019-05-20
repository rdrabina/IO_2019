package map.contents;

import constant.Constants;
import helpers.Position;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Food {

    private Map<Position, Ellipse2D.Double> planktons = new HashMap<>();
    private List<Color> colors;

    public Food() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        colors = IntStream.range(0, Constants.COLOR_AMOUNT)
                .mapToObj(i -> new Color(current.nextInt(255), current.nextInt(255), current.nextInt(255)))
                .collect(Collectors.toList());
    }

    public Food(List<Position> positions) {
        this();
        addFood(positions);
    }

    public void addFood(List<Position> positions) {
        positions.forEach(this::addSingleFood);
    }

    public void addSingleFood(Position position) {
        planktons.put(position, new Ellipse2D.Double(position.x, position.y, 9.3, 9.3));
    }

    public void removeFood(Position position) {
        planktons.remove(position);
    }

    public void draw(Graphics2D g2){
        planktons.forEach((p, d) -> {
            g2.setColor(colors.get(p.hashCode() % 100));
            g2.fill(d);
        });
    }
}