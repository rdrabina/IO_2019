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
    private double velocity=5;
    private int eatenFoodCounter;
    Player(){
        ThreadLocalRandom current = ThreadLocalRandom.current();
        player=new Ellipse2D.Double(MAP_WIDTH / 2, MAP_HEIGHT / 2, 25, 25);
        playerColor= new Color(current.nextInt(255), current.nextInt(255), current.nextInt(255));
    }
    public void drawPlayer(Graphics2D g2){
        g2.setColor(playerColor);
        g2.fill(player);
    }
    public void increaseSize(){
        player.width += SIZE_CHANGE;
        player.height += SIZE_CHANGE;
        velocity -= VELOCITY_CHANGE;
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
    public void setPlayer(Ellipse2D.Double player) {
        player = player;
    }
    public double getVelocity() {
        return velocity;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}