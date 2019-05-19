package player;

import helpers.ColorHelper;
import helpers.Position;

import static constant.Constants.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Player implements Serializable {

    private Color playerColor;
    private List<Ball> ballList = new ArrayList<>();

    private double angle;
    private double velocity;
    private int size;

    public Player(Position position, int size, int velocity, double angle){
        ballList.add(new Ball(size, position));
        this.velocity = velocity;
        this.angle = angle;
        this.size = size;

        playerColor = ColorHelper.getRandomColor();
    }

    public class Ball {
        private int size;
        private Ellipse2D.Double sprite;

        Ball(int size, Position pos) {
            sprite = new Ellipse2D.Double(pos.x, pos.y, 25, 25);
            setSize(size);
        }

        private void setSize(int size) {
            this.size = size;
            sprite.width = SIZE_CHANGE * (size - 5) + 25;
            sprite.height = sprite.width;
        }

        public int getSize() {
            return size;
        }

        public void printInfoBall(Graphics2D g2, long time) {
            g2.setColor(Color.ORANGE);
            double a= TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
            Font font= new Font(FONT,Font.BOLD,15);
            g2.setFont(font);
            g2.drawString("SPEED: " + new DecimalFormat("##.##").format(getVelocity()),(int)(getX() - 350), (int)(getY()-300));
            g2.drawString("RADIUS OF BALL: "+ Math.floor(sprite.height),(int)(getX()-350), (int)(getY() - 280));
            g2.drawString("TIME: "+a, (int)(getX()-350), (int)(getY() - 260));
        }

        public void draw(Graphics2D g2) {
            g2.setColor(playerColor);
            g2.fill(sprite);
        }

        public double getX(){
            return sprite.x;
        }
        public double getY(){
            return sprite.y;
        }

        public Ellipse2D.Double getSprite() {
            return sprite;
        }
    }

    public void drawPlayer(Graphics2D g2){
        ballList.forEach(b -> b.draw(g2));
    }

    public boolean isMouseOutsideOfPlayerCircle(Point mousePosition) {
        double x = getFirstExistingBall().get().getX();
        double y = getFirstExistingBall().get().getY();
        return mousePosition.getX() < x || mousePosition.getX() > x
                || mousePosition.getY() < y || mousePosition.getY() > y;
    }

    public boolean canPlayerMoveX(double dx) {
        double x = getFirstExistingBall().get().getX();
        return (x <= ACTIVE_WIDTH_START && dx > 0)
                || (x >= ACTIVE_WIDTH_START && x < ACTIVE_WIDTH_STOP)
                || (x >= ACTIVE_WIDTH_STOP && dx < 0);
    }

    public boolean canPlayerMoveY(double dy) {
        double y = getFirstExistingBall().get().getY();
        return (y <= ACTIVE_HEIGHT_START && dy > 0)
                || (y >= ACTIVE_HEIGHT_START && y < ACTIVE_HEIGHT_STOP)
                || (y >= ACTIVE_HEIGHT_STOP && dy < 0);
    }

    public Ellipse2D.Double getPlayer() {
        return getFirstExistingBall().get().getSprite();
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
        return new Position(getFirstExistingBall().get().getX(), getFirstExistingBall().get().getY());
    }

    public double getAngle() {
        return angle;
    }

    public int getSize() {
        return size;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}