package agar_io;

import constant.Constants;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static constant.Constants.*;

public class Player implements Serializable {

    private Ellipse2D.Double player;
    private Color playerColor;
    private List<Ball> ballList = new ArrayList<>();

    private double velocity;

    public Player(Position position, int size, int velocity){
        ballList.add(new Ball(size));
        player = new Ellipse2D.Double(position.x, position.y, 25, 25);
        this.velocity = velocity;

        ThreadLocalRandom current = ThreadLocalRandom.current();
        playerColor = new Color(current.nextInt(255), current.nextInt(255), current.nextInt(255));
    }

    public class Ball {
        private int size;

        Ball(int size) {
            setPlayerSize(size);
        }

        private void setPlayerSize(int size) {
            this.size = size;
            player.width = SIZE_CHANGE * (size - 5) + 25;
            player.height = SIZE_CHANGE * (size - 5) + 25;
        }

        public void printInfoBall(Graphics2D g2, long time) {
            g2.setColor(Color.ORANGE);
            double a= TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
            Font font= new Font(Constants.FONT,Font.BOLD,15);
            g2.setFont(font);
            g2.drawString("SPEED: " + new DecimalFormat("##.##").format(getVelocity()),(int)(getX() - 350), (int)(getY()-300));
            g2.drawString("RADIUS OF BALL: "+ Math.floor(player.height),(int)(getX()-350), (int)(getY() - 280));
            g2.drawString("TIME: "+a, (int)(getX()-350), (int)(getY() - 260));
        }
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

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Optional<Player.Ball> getFirstExistingBall() {
        return Optional.of(ballList.get(0));
    }

    public Position getPlayerPosition() {
        return new Position(player.x, player.y);
    }
}