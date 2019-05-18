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
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import static constant.Constants.*;

public class DisplayGame extends JPanel implements ActionListener {
    private Rectangle outerArea;
    public static int WIDTH=Constants.WINDOW_WIDTH;
    public static int HEIGHT=Constants.WINDOW_HEIGHT;
    private int numoffoods=1000;
    private int eatenFoodCounter=0;
    private Players player1;
    private JViewport vPort;
    private Food food;
    private long time;
    private Poisons poison;
    public Menu menu;
    private Point pointPlayer1;
    public static GameState state = GameState.MENU;

    public DisplayGame() throws IOException {
        Timer timer=new Timer(30,this);
        menu = new Menu(this);
        time = System.nanoTime();
        addMouseListener(menu);
        setFocusable(true);
        requestFocusInWindow();
        player1= new Players();
        player2= new Players();
        poison= new Poisons(numoffoods/2);
        food = new Foods(numoffoods);
        Dimension newSize = new Dimension(2000, 1600);
    //    outerArea= new Rectangle(0, 0, 200, 500);
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
//            g.drawImage(image, 0, 0, this);

//            poison.drawPoisons(g2);
            food.drawFood(g2);
            player1.drawPlayers(g2);
            player2.drawPlayers(g2);
            pointPlayer1= new Point((int)(player1.getX()),(int)(player1.getY()));
            menu.setPoint(pointPlayer1);
            didBallIntersect();
            printInfoBall(g2);
            whoWon();
         //   g2.draw(outerArea);
            g2.dispose();
        }
        else if(state==GameState.WIN){
            menu.player1Won(g2);
        }
        else if(state==GameState.LOSE){
            menu.player2Won(g2);
        }
    }
    public void whoWon(){
        if(player1.getPlayer().height>player2.getPlayer().height&&player1.getPlayer().getBounds().intersects(player2.getPlayer().getBounds())){
            state=GameState.WIN;
        }
        else if(player1.getPlayer().height<player2.getPlayer().height&&player1.getPlayer().getBounds().intersects(player2.getPlayer().getBounds())){
            state=GameState.LOSE;
        }
    }
    public void didBallIntersect(){

//        Thread thread = new Thread(){
//            public void run(){
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                food = new Foods(food, numoffoods);
//            }
//        };


        for (int i = 0; i < food.getFoods().length; i++) {
            if(food.getFoods()[i]!=null && player1.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())){
                food.getFoods()[i] = null;
                player1.increaseSize();
                eatenFoodCounter++;
                if (eatenFoodCounter > 0.2 * numoffoods){
//                    thread.start();
                    food = new Foods(food, numoffoods);
                }

            }
        }


        for (int i = 0; i < food.getFoods().length; i++) {
            if(food.getFoods()[i]!=null && player2.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())){
                food.getFoods()[i] = null;
                player2.increaseSize();
                eatenFoodCounter++;
            }
        }
    }



    public void printInfoBall(Graphics2D g2){
        g2.setColor(Color.ORANGE);
        double a= TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        Font font= new Font("klavika",Font.BOLD,15);
        g2.setFont(font);
        g2.drawString("SPEED: "+new DecimalFormat("##.##").format(player1.getVelocity()),(int)(player1.getX()-350), (int)(player1.getY()-300));
        g2.drawString("RADIUS OF BALL: "+Math.floor(player1.getPlayer().height),(int)(player1.getX()-350), (int)(player1.getY()-280));
        g2.drawString("TIME: "+a, (int)(player1.getX()-350),  (int)(player1.getY()-260));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(state==GameState.GAME){

            Point mousePosition=getMousePosition();
            if(mousePosition==null)return;

            double dx = mousePosition.x - player1.getPlayer().x - player1.getPlayer().width/2;
            double dy = mousePosition.y - player1.getPlayer().y - player1.getPlayer().height/2;

            if(dx*dx+dy*dy >12){

                double angle=Math.atan2(dy, dx);

                if(mousePosition.getX()<player1.getPlayer().getBounds().getMinX()||
                        mousePosition.getX()>player1.getPlayer().getBounds().getMaxX()||
                        mousePosition.getY()<player1.getPlayer().getBounds().getMinY()||
                        mousePosition.getY()>player1.getPlayer().getBounds().getMaxY()){

                    player1.getPlayer().x += (int)(player1.getVelocity()*Math.cos(angle));
                    player1.getPlayer().y += (int)(player1.getVelocity()*Math.sin(angle));
                    Point view = new Point((int)player1.getPlayer().x-currentWidth/2,(int)player1.getPlayer().y-currentHeight/2);
                    vPort.setViewPosition(view);

                }
            }
            repaint();
        }
    }
    public Players getPlayer1() {
        return player1;
    }
    public void setPlayer1(Players player1) {
        this.player1 = player1;
    }
    public Players getPlayer2() {
        return player2;
    }
    public void setPlayer2(Players player2) {
        this.player2 = player2;
    }
    public Foods getFood() {
        return food;
    }
    public void setFood(Foods food) {
        this.food = food;
    }
    public Poisons getPoison() {
        return poison;
    }
    public void setPoison(Poisons poison) {
        this.poison = poison;
    }


}
