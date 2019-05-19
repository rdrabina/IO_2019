package agar_io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Random;

import static constant.Constants.*;

public class Player implements Serializable {
    final private int id;
    final private Ellipse2D.Double sprite;
    final private Color playerColor;

    private double velocity;
    private int size;

    public Player(int id, Position position, double velocity, int size) {
        Random random = new Random();

        playerColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        sprite = new Ellipse2D.Double(position.x, position.y, 25, 25);

        this.id = id;
        this.velocity = velocity;
        this.size = size;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(playerColor);
        g2.fill(sprite);
    }

    public void setSize(int size) {
        this.size = size;
        sprite.height = sprite.width = size;
        velocity -= 0.03;
    }
    public int getSize() {
        return size;
    }

    public Ellipse2D.Double getSprite() {
        return sprite;
    }

    public double getX(){
        return sprite.x;
    }
    public double getY(){
        return sprite.y;
    }

    public double getVelocity() {
        return velocity;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}