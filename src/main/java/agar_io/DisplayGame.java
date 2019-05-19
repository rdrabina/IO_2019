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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import static constant.Constants.*;

public class DisplayGame extends JPanel implements ActionListener {
    private Rectangle outerArea;
    public static int WIDTH=Constants.WINDOW_WIDTH;
    public static int HEIGHT=Constants.WINDOW_HEIGHT;
    private Player player;
    private JViewport vPort;
    private long time;
    public Menu menu;
    private Point pointplayer;
    public static GameState state = GameState.MENU;

    private final Food food = new Food();
    private final Map<Integer, Player> players = new HashMap<>();

    public DisplayGame() throws IOException {
        Timer timer=new Timer(30,this);
        menu = new Menu(this);
        time = System.nanoTime();
        addMouseListener(menu);
        setFocusable(true);
        requestFocusInWindow();
        player= new Player(0, new Position(0, 0), 5, 25);
        Dimension newSize = new Dimension(MAP_WIDTH, MAP_HEIGHT);
        outerArea= new Rectangle(0, 0, 200, 500);
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
//        setBackground(Color.GRAY);
//        g.drawImage(image, 0, 0, this);
        if(state==GameState.MENU){
            try {
                menu.render(g2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(state==GameState.GAME){

            food.draw(g2);
            player.draw(g2);
            pointplayer= new Point((int)(player.getX()),(int)(player.getY()));
            menu.setPoint(pointplayer);
            printInfoBall(g2);
            g2.draw(outerArea);
            g2.dispose();
        }
//        else if(state==GameState.WIN){
//            menu.playerWon(g2);
//        }
//        else if(state==GameState.LOSE){
//            menu.player2Won(g2);
//        }
    }


    public void printInfoBall(Graphics2D g2){
        g2.setColor(Color.ORANGE);
        double a= TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        Font font= new Font("klavika",Font.BOLD,15);
        g2.setFont(font);
        g2.drawString("SPEED: "+new DecimalFormat("##.##").format(player.getVelocity()),(int)(player.getX()-350), (int)(player.getY()-300));
        g2.drawString("RADIUS OF BALL: "+Math.floor(player.getSprite().height),(int)(player.getX()-350), (int)(player.getY()-280));
        g2.drawString("TIME: "+a, (int)(player.getX()-350),  (int)(player.getY()-260));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameState.GAME.equals(state)){

            Point mousePosition=getMousePosition();
            if(Objects.isNull(mousePosition))return;

            double dx = mousePosition.x - player.getSprite().x - player.getSprite().width/2;
            double dy = mousePosition.y - player.getSprite().y - player.getSprite().height/2;

            if (dx*dx+dy*dy >12) {

                double angle=Math.atan2(dy, dx);

                if (isMouseOutsideOfPlayerCircle(mousePosition)){
                    if (canPlayerMoveX(dx, mousePosition)) {
                        player.getSprite().x += (int)(player.getVelocity()*Math.cos(angle));
                    }
                    if (true) {
                        player.getSprite().y += (int)(player.getVelocity()*Math.sin(angle));
                    }
                    Point view = new Point((int)player.getSprite().x-currentWidth/2,(int)player.getSprite().y-currentHeight/2);
                    vPort.setViewPosition(view);
                }
            }
            repaint();
        }
    }

    private boolean isMouseOutsideOfPlayerCircle(Point mousePosition) {
        double x = player.getX();
        double y = player.getY();
        return mousePosition.getX() < x || mousePosition.getX() > x
                || mousePosition.getY() < y || mousePosition.getY() > y;
    }

    private boolean canPlayerMoveX(double dx, Point mousePosition) {
        double x = player.getX();
        System.out.println(x + " " + dx + " " + mousePosition.x + " " + mousePosition.y);
        return (x <= Constants.WINDOW_WIDTH / 2 && dx > 0) || x >= Constants.WINDOW_WIDTH / 2
                || (x >= Constants.MAP_WIDTH - Constants.WINDOW_WIDTH / 2 && dx < 0) || x<= Constants.MAP_WIDTH - Constants.WINDOW_WIDTH / 2;
    }


}
