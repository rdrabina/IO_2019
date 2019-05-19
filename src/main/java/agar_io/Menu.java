package agar_io;

import static constant.Constants.*;

import constant.Constants;
import game.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Menu implements MouseListener {
    private Rectangle playButton;
    private Rectangle quitButton;
    private boolean enabled = true;
    private Game game;
    private Point pointPlayer1;
    public String[] args;

    public Menu(Game game) {
        this.game = game;
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

    public void displayMenu(Graphics2D g2) {
        try {
            render(g2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g2) throws IOException {

        BufferedImage img = ImageIO.read(new File("100LAT_AGH.jpg"));
//        int w = img.getWidth(null);
//        int h = img.getHeight(null);
//        BufferedImage bi = new
//                BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        g2.drawImage(img, Constants.WINDOW_WIDTH /2-334, 0, 667, 250, null);


        Font font= new Font(FONT, Font.BOLD,50);
        g2.setFont(font);
        g2.drawString(TITLE, Constants.WINDOW_WIDTH/2-80, 300);
        g2.setColor(Color.BLACK);
        g2.drawString("Play", playButton.x, playButton.y+40);
        g2.drawString("Quit", quitButton.x, quitButton.y+40);
    }
    public void setPoint(Point pointPlayer1){
        this.pointPlayer1=pointPlayer1;
    }

    public void setGame(Game dg){
        game = dg;
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
            Game.state = GameState.GAME;
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