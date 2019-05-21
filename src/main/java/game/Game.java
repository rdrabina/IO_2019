package game;
import leaderboard.Leaderboard;
import map.contents.Building;
import map.contents.Food;
import menu.Menu;
import helpers.Position;
import player.Player;
import player.PlayerIdentification;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import javax.swing.*;

import static constant.Constants.*;
import static game.GameState.MENU;

public class Game extends JPanel implements ActionListener {
    private JViewport vPort;
    private Building building;
    private long gameTime;
    public Menu menu;
    private Position pointplayer;
    public static GameState state = MENU;

    private final HashMap<String, Player> players = new HashMap<>();
    private final Player player;
    private final Food food = new Food();

    private final Semaphore semaphore;

    public Game(Semaphore semaphore, PlayerIdentification identification) {
        this.semaphore = semaphore;

        Timer timer=new Timer(20,this);
        menu = new Menu(this);
        gameTime = System.nanoTime();
        addMouseListener(menu);
        setFocusable(true);
        requestFocusInWindow();
        player = new Player(identification, new Position(ACTIVE_WIDTH_START, ACTIVE_HEIGHT_START), 5, 5, 1);
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
                DisplayGame.finishGame();
                state = MENU;
                menu.displayWin(player.getSize());
                break;
            case LOSE:
                DisplayGame.finishGame();
                state = MENU;
                menu.displayLost(player.getSize());
                break;
        }
    }

    private void displayGame(Graphics2D g2) {
        food.draw(g2);
        player.drawPlayer(g2);
        pointplayer= player.getPlayerPosition();
        printInfoBall(g2, player);

        Leaderboard.printLeaderboard(g2, players, player);
        players.values().forEach(p -> p.drawPlayer(g2));

        building.drawBuildings(g2);
        menu.setPlayerPosition(pointplayer);
        g2.dispose();
    }

    private void printInfoBall(Graphics2D g2, Player player) {
        Optional<Player.Ball> firstExistingBall = player.getFirstExistingBall();
        firstExistingBall.ifPresent(ball -> ball.printInfoBall(g2, gameTime));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameState.GAME.equals(state)){
            semaphore.acquireUninterruptibly();
            moveControlledPlayer();
            moveOtherPlayers();
            repaint();
            semaphore.release();
        }
    }

    private void moveControlledPlayer() {
        Point mousePosition=getMousePosition();
        if(Objects.isNull(mousePosition)) return;

        double dx = mousePosition.x - player.getPlayer().x - player.getPlayer().width/2;
        double dy = mousePosition.y - player.getPlayer().y - player.getPlayer().height/2;

        if (dx*dx+dy*dy >12) {
            double angle=Math.atan2(dy, dx);
            player.setAngle(angle);

            if (player.isMouseOutsideOfPlayerCircle(mousePosition)){
                movePlayer(player);

                Point view = new Point((int)player.getPlayer().x-CURRENT_WIDTH/2,(int)player.getPlayer().y-CURRENT_HEIGHT/2);
                vPort.setViewPosition(view);
            }
        }
    }

    private void moveOtherPlayers() {
        players.values().forEach(this::movePlayer);
    }

    private void movePlayer(Player player) {
        double dx = player.getVelocity()*Math.cos(player.getAngle());
        double dy = player.getVelocity()*Math.sin(player.getAngle());

        if (player.canPlayerMoveX(dx)) {
            if(!building.isBuildingOnPlayerXWay(player, dx))
                player.getPlayer().x += (int)(dx);
        }
        if (player.canPlayerMoveY(dy)) {
            if(!building.isBuildingOnPlayerYWay(player, dy))
                player.getPlayer().y += (int)(dy);
        }
    }

    public Food getFood() {
        return food;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }
    public Player getPlayer() {
        return player;
    }
}
