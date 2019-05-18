package agar_io;

import static constant.Constants.*;
import game.GameState;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Menu implements MouseListener {
    private Rectangle playButton;
    private Rectangle quitButton;
    private boolean enabled = true;
    private DisplayGame displayGame;
    private Point pointPlayer1;
    public String[] args;

    public Menu(DisplayGame displayGame) {
        this.displayGame = displayGame;
        initializeButtons();
    }

    private void initializeButtons() {
        playButton = new Rectangle(calculateElementsStartWidth(),calculatePlayButtonStartHeight(),
                BUTTON_WIDTH, BUTTON_HEIGHT);

        quitButton = new Rectangle(calculateElementsStartWidth(), calculateQuitButtonStartHeight(),
                BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public void setArgs(String[] A){
        args = A;
    }

    public void render(Graphics2D g2) throws IOException {

        BufferedImage img = ImageIO.read(new File("100LAT_AGH.jpg"));
//        int w = img.getWidth(null);
//        int h = img.getHeight(null);
//        BufferedImage bi = new
//                BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        g2.drawImage(img, DisplayGame.WIDTH/2-334, 0, 667, 250, null);


        Font font= new Font(FONT, Font.BOLD,50);
        g2.setFont(font);
//        g2.setColor(Color.YELLOW);
//        g2.fillOval(DisplayGame.WIDTH/2-73, DisplayGame.HEIGHT/2-250, 150, 150);
//        g2.setColor(Color.ORANGE);
        g2.drawString("AGH.IO", DisplayGame.WIDTH/2-80, 300);
        g2.setColor(Color.YELLOW);
        g2.fillOval(calculateElementsStartWidth(), calculateOvalStartHeight(), OVAL_WIDTH, OVAL_HEIGHT);
        g2.setColor(Color.ORANGE);
//        g2.drawString(TITLE, calculateOvalStartWidth(), TITLE_HEIGHT);
        g2.setColor(Color.BLACK);
        g2.drawString("Play", playButton.x, playButton.y+40);
        g2.drawString("Quit", quitButton.x, quitButton.y+40);
    }
    public void setPoint(Point pointPlayer1){
        this.pointPlayer1=pointPlayer1;
    }

    public void setDisplayGame(DisplayGame dg){
        displayGame = dg;
    }
    public void player1Won(Graphics2D g2){
        g2.setColor(Color.GREEN);
        Font font= new Font(FONT, Font.BOLD,50);
        g2.setFont(font);
        g2.drawString("YOU WON", pointPlayer1.x-100, pointPlayer1.y);
    }
    public void player2Won(Graphics2D g2){
        g2.setColor(Color.RED);
        Font font= new Font(FONT, Font.BOLD,50);
        g2.setFont(font);
        g2.drawString("YOU LOST", pointPlayer1.x-100, pointPlayer1.y);
    }

    private int calculateElementsStartWidth() {
        return WINDOW_WIDTH /2;
    }

    private int calculatePlayButtonStartHeight() {
        return WINDOW_HEIGHT/2 - 4 * BUTTON_HEIGHT;
    }

    private int calculateQuitButtonStartHeight() {
        return calculatePlayButtonStartHeight() + SPACE_BETWEEN_BUTTONS_HEIGHT;
    }

    private int calculateOvalStartWidth() {
        return calculateElementsStartWidth() - ELEMENT_OFFSET;
    }

    private int calculateOvalStartHeight() {
        return calculateQuitButtonStartHeight() + SPACE_BETWEEN_BUTTONS_HEIGHT;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (enabled) {
            int xMouse = e.getX();
            int yMouse = e.getY();
            handleClick(xMouse, yMouse);
        }
    }

    private void handleClick(int xMouse, int yMouse) {
        if (resolvePlayClick(xMouse, yMouse)) {
//                    if(args.length == 0){
            Server server = new Server(displayGame);
            Thread thread = new Thread(server);
            thread.start();
//                    }
//                    else{
//                        Client client = new Client(args[0] ,displayGame);
//                        System.out.println(args[0]);
//                        Thread thread = new Thread(client);
//                        thread.start();
//                    }
            DisplayGame.state = GameState.GAME;
            enabled=false;
        } else if (resolveQuitClick(xMouse, yMouse)) {
            System.exit(1);
        }
    }

    private boolean resolvePlayClick(int xMouse, int yMouse) {
        return xMouse >= calculateElementsStartWidth() && xMouse <= calculateButtonsWidthToClick()
                && yMouse >= calculatePlayButtonStartHeight() && yMouse <= calculateButtonPlayHeightToClick();
    }

    private boolean resolveQuitClick(int xMouse, int yMouse) {
        return xMouse >= calculateElementsStartWidth() && xMouse <= calculateButtonsWidthToClick()
                && yMouse >= calculateQuitButtonStartHeight() && yMouse <= calculateButtonQuitHeightToClick();
    }

    private int calculateButtonsWidthToClick() {
        return calculateElementsStartWidth() + BUTTON_WIDTH;
    }

    private int calculateButtonPlayHeightToClick() {
        return calculatePlayButtonStartHeight() + BUTTON_HEIGHT;
    }

    private int calculateButtonQuitHeightToClick() {
        return calculateQuitButtonStartHeight() + BUTTON_HEIGHT;
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

}