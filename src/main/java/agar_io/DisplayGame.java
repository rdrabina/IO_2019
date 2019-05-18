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
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import static constant.Constants.*;

public class DisplayGame extends JPanel implements ActionListener {
    private Rectangle outerArea;
    public static int WIDTH=Constants.WINDOW_WIDTH;
    public static int HEIGHT=Constants.WINDOW_HEIGHT;
    private int numoffoods=1000;
    private int eatenFoodCounter=0;
    private Player player;
    private JViewport vPort;
    private Food food;
    private long time;
    public Menu menu;
    private Point pointplayer;
    public static GameState state = GameState.MENU;

    public DisplayGame() throws IOException {
        Timer timer=new Timer(30,this);
        menu = new Menu(this);
        time = System.nanoTime();
        addMouseListener(menu);
        setFocusable(true);
        requestFocusInWindow();
        player= new Player();
        food = new Food(numoffoods);
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

            food.drawFood(g2);
            player.drawPlayers(g2);
            pointplayer= new Point((int)(player.getX()),(int)(player.getY()));
            menu.setPoint(pointplayer);
            didBallIntersect();
            printInfoBall(g2);
            whoWon();
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
    public void whoWon(){
//        if(player.getPlayer().height>player2.getPlayer().height&&player.getPlayer().getBounds().intersects(player2.getPlayer().getBounds())){
//            state=GameState.WIN;
//        }
//        else if(player.getPlayer().height<player2.getPlayer().height&&player.getPlayer().getBounds().intersects(player2.getPlayer().getBounds())){
//            state=GameState.LOSE;
//        }
    }
    public void didBallIntersect(){

//        Thread thread = new Thread(){
//            public void run(){
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                food = new Food(food, numoffoods);
//            }
//        };


        for (int i = 0; i < food.getFoods().length; i++) {
            if(food.getFoods()[i]!=null && player.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())){
                food.getFoods()[i] = null;
                player.increaseSize();
                eatenFoodCounter++;
                if (eatenFoodCounter > 0.2 * numoffoods){
//                    thread.start();
                    food = new Food(food, numoffoods);
                }

            }
        }


        for (int i = 0; i < food.getFoods().length; i++) {
//            if(food.getFoods()[i]!=null && player2.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())){
//                food.getFoods()[i] = null;
//                player2.increaseSize();
//                eatenFoodCounter++;
//            }
        }
    }



    public void printInfoBall(Graphics2D g2){
        g2.setColor(Color.ORANGE);
        double a= TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        Font font= new Font("klavika",Font.BOLD,15);
        g2.setFont(font);
        g2.drawString("SPEED: "+new DecimalFormat("##.##").format(player.getVelocity()),(int)(player.getX()-350), (int)(player.getY()-300));
        g2.drawString("RADIUS OF BALL: "+Math.floor(player.getPlayer().height),(int)(player.getX()-350), (int)(player.getY()-280));
        g2.drawString("TIME: "+a, (int)(player.getX()-350),  (int)(player.getY()-260));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameState.GAME.equals(state)){

            Point mousePosition=getMousePosition();
            if(Objects.isNull(mousePosition))return;

            double dx = mousePosition.x - player.getPlayer().x - player.getPlayer().width/2;
            double dy = mousePosition.y - player.getPlayer().y - player.getPlayer().height/2;

            if (dx*dx+dy*dy >12) {

                double angle=Math.atan2(dy, dx);

                if (isMouseOutsideOfPlayerCircle(mousePosition)){
                    if (canPlayerMoveX(dx, mousePosition)) {
                        player.getPlayer().x += (int)(player.getVelocity()*Math.cos(angle));
                    }
                    if (true) {
                        player.getPlayer().y += (int)(player.getVelocity()*Math.sin(angle));
                    }
                    Point view = new Point((int)player.getPlayer().x-currentWidth/2,(int)player.getPlayer().y-currentHeight/2);
                    vPort.setViewPosition(view);
                }
            }
            repaint();
        }
    }

//    private boolean canPlayerMove(Point mousePosition) {
//        return isMouseOutsideOfPlayerCircle(mousePosition) && isPlayerInFrontOfABound();
//    }

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
    public Player getPlayer() {
        return player;
    }
    public void setplayer(Player player) {
        this.player = player;
    }
//    public Player getPlayer2() {
//        return player2;
//    }
//    public void setPlayer2(Player player2) {
//        this.player2 = player2;
//    }
    public Food getFood() {
        return food;
    }
    public void setFood(Food food) {
        this.food = food;
    }


}
