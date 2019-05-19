package game;

import helpers.FormResize;
import client.GameClient;
import player.PlayerIdentification;

import javax.swing.*;

import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import static constant.Constants.*;

public class DisplayGame {
    private static JFrame frame;

    public static void displayGame(PlayerIdentification ind) {
        frame= new FormResize(TITLE);
        JScrollPane pane= new JScrollPane();
        JViewport vport= new JViewport();
        Semaphore semaphore = new Semaphore(1);

        Game panel= new Game(semaphore);

        GameClient client = new GameClient("localhost", 9998, ind, panel, semaphore);
        client.start();

        frame.setVisible(true);
        pane.setViewport(vport);
        vport.add(panel);
        frame.add(pane);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        panel.setvPort(vport);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void finishGame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
