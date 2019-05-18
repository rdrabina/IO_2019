package agar_io.Model;

import java.awt.geom.Ellipse2D;


public class Player {

    public String id;

    private Ellipse2D.Double playerModel;
    private Position position;
    private double velocity;
    private double size;

    Player(Position position) {
        this.position = position;
        this.size = 25;
        this.velocity = 5;
        this.playerModel = new Ellipse2D.Double(position.getX(), position.getY(), size, size);
    }

    private Position currentPosition( ) {
        return this.position;
    }

    private void moveTo(Position position) {
        this.position = position;
    }

    private Ellipse2D.Double getModel() {
        return this.playerModel;
    }

}
