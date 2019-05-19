package agar_io;

import constant.Constants;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Ball {

    public static void printInfoBall(Graphics2D g2, Player player, long time) {
        g2.setColor(Color.ORANGE);
        double a= TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
        Font font= new Font("klavika",Font.BOLD,15);
        g2.setFont(font);
        g2.drawString("SPEED: "+new DecimalFormat("##.##").format(player.getVelocity()),(int)(player.getX()-350), (int)(player.getY()-300));
        g2.drawString("RADIUS OF BALL: "+ Math.floor(player.getPlayer().height),(int)(player.getX()-350), (int)(player.getY()-280));
        g2.drawString("TIME: "+a, (int)(player.getX()-350),  (int)(player.getY()-260));
    }

    public static void didBallIntersect(Food food, Player player){

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

//        Arrays.stream(food.getFoods())
//                .filter(x -> shouldFoodBeFiltered())
        for (int i = 0; i < food.getFoods().length; i++) {
            if(food.getFoods()[i]!=null && player.getPlayer().getBounds().intersects(food.getFoods()[i].getBounds())){
                food.getFoods()[i] = null;
                player.increaseSize();
//                eatenFoodCounter++;
//                if (eatenFoodCounter > 0.2 * Constants.MAX_FOOD_AMOUNT){
////                    thread.start();
//                    food = new Food(food, Constants.MAX_FOOD_AMOUNT);
//                }

            }
        }
    }
}
