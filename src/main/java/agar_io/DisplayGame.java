package agar_io;

import javax.swing.*;

import static constant.Constants.CURRENT_HEIGHT;
import static constant.Constants.CURRENT_WIDTH;
import static constant.Constants.TITLE;

public class DisplayGame {

    public static void displayGame() {
        JFrame frame= new FormResize(TITLE);
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
