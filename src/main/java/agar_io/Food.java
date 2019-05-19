package agar_io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Food {

    private Map<Position, Ellipse2D.Double> planktons = new HashMap<>();
    private Color[] colors = new Color[100];

    public Food()
    {
        Random rand = new Random();
        for (int i = 0; i < 100; i++)
            colors[i] = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    public Food(List<Position> positions)
    {
        this();
        addFood(positions);
    }

    public void addFood(List<Position> positions) {
        positions.forEach(p -> planktons.put(p, new Ellipse2D.Double(p.x, p.y, 9.3, 9.3)));
    }

    public void removeFood(List<Position> positions) {
        positions.forEach(p -> planktons.remove(p));
    }

    public void draw(Graphics2D g2){
        planktons.forEach((p, d) -> {
            g2.setColor(colors[p.hashCode() % 100]);
            g2.fill(d);
        });
    }
}