package agar_io;

import javax.swing.*;

import java.awt.event.WindowEvent;

import static constant.Constants.CURRENT_HEIGHT;
import static constant.Constants.CURRENT_WIDTH;
import static constant.Constants.TITLE;

public class DisplayGame {
    private static JFrame frame;

    public static void displayGame() {
        frame= new FormResize(TITLE);
        JScrollPane pane= new JScrollPane();
        JViewport vport= new JViewport();
        Game panel= new Game();

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
