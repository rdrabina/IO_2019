package agar_io;

import constant.Constants;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static constant.Constants.*;

public class Player implements Serializable {

    private Ellipse2D.Double player;
    private Color playerColor;

    private double velocity;
    private int size;

    public Player(Position position, int size, int velocity){

        setSize(size);
        player=new Ellipse2D.Double(position.x, position.y, 25, 25);
        this.velocity = velocity;

        ThreadLocalRandom current = ThreadLocalRandom.current();
        playerColor= new Color(current.nextInt(255), current.nextInt(255), current.nextInt(255));
    }

    public void drawPlayer(Graphics2D g2){
        g2.setColor(playerColor);
        g2.fill(player);
    }

    public boolean isMouseOutsideOfPlayerCircle(Point mousePosition) {
        double x = getX();
        double y = getY();
        return mousePosition.getX() < x || mousePosition.getX() > x
                || mousePosition.getY() < y || mousePosition.getY() > y;
    }

    public boolean canPlayerMoveX(double dx) {
        double x = player.getX();
        return (x <= Constants.WINDOW_WIDTH / 2 && dx > 0)
                || (x >= Constants.WINDOW_WIDTH / 2 && x < Constants.MAP_WIDTH - Constants.WINDOW_WIDTH / 2)
                || (x >= Constants.MAP_WIDTH - Constants.WINDOW_WIDTH / 2 && dx < 0);
    }

    public boolean canPlayerMoveY(double dy) {
        double y = getY();
        return (y <= Constants.ACTIVE_HEIGHT_START && dy > 0)
                || (y >= Constants.ACTIVE_HEIGHT_START && y < Constants.ACTIVE_HEIGHT_STOP)
                || (y >= Constants.ACTIVE_HEIGHT_STOP && dy < 0);
    }

    public Ellipse2D.Double getPlayer() {
        return player;
    }

    public double getX(){
        return player.x;
    }
    public double getY(){
        return player.y;
    }
    public double getVelocity() {
        return velocity;
    }

    public void setSize(int size) {
        this.size = size;
        player.width = player.height = SIZE_CHANGE * (size - 5) + 25;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}