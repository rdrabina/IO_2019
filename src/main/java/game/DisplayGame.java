package game;

import client.ServerAccesor;
import helpers.FormResize;
import client.GameClient;
import player.PlayerIdentification;

import javax.swing.*;

import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import static constant.Constants.CURRENT_HEIGHT;
import static constant.Constants.CURRENT_WIDTH;
import static constant.Constants.TITLE;

public class DisplayGame {
    private static JFrame frame;

    public static void displayGame(PlayerIdentification ind) {
        JFrame frame= new FormResize(TITLE);
        JScrollPane pane= new JScrollPane();
        JViewport vport= new JViewport();

        ServerAccesor accesor = new ServerAccesor();

        Game panel= new Game(accesor, ind);

        GameClient client = new GameClient("localhost", 9998, ind, panel, accesor);
        client.run();

        frame.setVisible(true);
        pane.setViewport(vport);
        vport.add(panel);
        frame.add(pane);
        frame.setSize(CURRENT_WIDTH, CURRENT_HEIGHT);
        panel.setvPort(vport);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void finishGame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
