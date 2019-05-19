package agar_io;

import constant.Constants;
import game.GameState;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import static constant.Constants.*;
import static game.GameState.*;

public class Game extends JPanel implements ActionListener {
    private Player player;
    private JViewport vPort;
    private Food food;
    private Building building;
    private long gameTime;
    public Menu menu;
    private Position pointplayer;
    public static GameState state = GameState.MENU;

    public Game() {
        Timer timer=new Timer(20,this);
        menu = new Menu(this);
        gameTime = System.nanoTime();
        addMouseListener(menu);
        setFocusable(true);
        requestFocusInWindow();
        player= new Player(new Position(2, 2), 5, 5);
        food = new Food();
        building = new Building();
        Dimension newSize = new Dimension(MAP_WIDTH, MAP_HEIGHT);
        setPreferredSize(newSize);
        timer.start();
    }
    public void setvPort(JViewport vPort) {
        this.vPort = vPort;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.GRAY);

        switch (state) {
            case MENU:
                menu.displayMenu(g2);
                break;
            case GAME:
                displayGame(g2);
                break;
            case WIN:
//                menu.playerWon(g2);
                break;
            case LOSE:
                menu.displayLost(30);
                break;
        }
    }

    private void displayGame(Graphics2D g2) {
        food.draw(g2);
        building.drawBuildings(g2);
        player.drawPlayer(g2);
        pointplayer= player.getPlayerPosition();
        menu.setPlayerPosition(pointplayer);
        printInfoBall(g2, player);
        g2.dispose();
    }

    private void printInfoBall(Graphics2D g2, Player player) {
        Optional<Player.Ball> firstExistingBall = player.getFirstExistingBall();
        Position playerPosition = player.getPlayerPosition();
        firstExistingBall.ifPresent(ball -> ball.printInfoBall(g2, gameTime));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameState.GAME.equals(state)){

            Point mousePosition=getMousePosition();
            if(Objects.isNull(mousePosition))return;

            double dx = mousePosition.x - player.getPlayer().x - player.getPlayer().width/2;
            double dy = mousePosition.y - player.getPlayer().y - player.getPlayer().height/2;
            if (dx*dx+dy*dy >12) {
                state = LOSE;
                double angle=Math.atan2(dy, dx);

                if (player.isMouseOutsideOfPlayerCircle(mousePosition)){
                    if (player.canPlayerMoveX(dx)) {
                        player.getPlayer().x += (int)(player.getVelocity()*Math.cos(angle));
                    }
                    if (player.canPlayerMoveY(dy)) {
                        player.getPlayer().y += (int)(player.getVelocity()*Math.sin(angle));
                    }
                    Point view = new Point((int)player.getPlayer().x-CURRENT_WIDTH/2,(int)player.getPlayer().y-CURRENT_HEIGHT/2);
                    vPort.setViewPosition(view);
                }
            }
            repaint();
        }
    }

}
