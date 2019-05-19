package agar_io;

import constant.Constants;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Food implements Serializable {
    private Ellipse2D.Double foods[];
    private Color foodColors[];

    Food(int numoffoods){
        foods= new Ellipse2D.Double[numoffoods];
        foodColors= new Color[numoffoods];
        callOnce();
    }

    Food(Food foods, int numoffoods){
        this.foods = foods.getFoods();
        this.foodColors = foods.getFoodColors();
        callMore();
    }


    public void callOnce(){
        randomFoodColorInitializer();
        initializeFoods();
    }

    public void callMore(){
        initializeFoods();
    }


    public void randomFoodColorInitializer(){
        Random a=new Random();

        for (int i = 0; i < foodColors.length; i++) {
            foodColors[i]=new Color(a.nextInt(255),a.nextInt(255),a.nextInt(255));
        }

    }
    public void drawFood(Graphics2D g2){

        for (int i = 0; i < foods.length; i++) {
            if(foods[i]!=null){
                g2.setColor(foodColors[i]);
                g2.fill(foods[i]);
            }
        }
    }
//    public void initializeFoods(){
//        Random a=new Random();
//        for (int i = 0; i < foods.length; i++) {
//            foods[i]=new Ellipse2D.Double(a.nextInt(400), a.nextInt(300), 9.3, 9.3);
//        }
//    }

    public void initializeFoods(){
        for (int i = 0; i < foods.length; i++) {
            if(foods[i] == null){
                foods[i]= new Ellipse2D.Double(
                        ThreadLocalRandom.current().nextInt(Constants.ACTIVE_WIDTH_START, Constants.ACTIVE_WIDTH_STOP),
                        ThreadLocalRandom.current().nextInt(Constants.ACTIVE_HEIGHT_START, Constants.ACTIVE_HEIGHT_STOP), 10, 10);
            }
        }
    }

    public Ellipse2D.Double[] getFoods() {
        return foods;
    }
    public void setFoods(Ellipse2D.Double[] foods) {
        this.foods = foods;
    }
    public Color[] getFoodColors() {
        return foodColors;
    }
    public void setFoodColors(Color[] foodColors) {
        this.foodColors = foodColors;
    }
}