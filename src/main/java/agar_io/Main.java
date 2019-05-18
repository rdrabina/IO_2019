package agar_io;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import java.io.IOException;

import static constant.Constants.*;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame frame= new FormResize("AGH.IO 2019");
        JScrollPane pane= new JScrollPane();
        JViewport vport= new JViewport();
        DisplayGame panel= new DisplayGame();

        panel.menu.setArgs(args);
        frame.setVisible(true);
        pane.setViewport(vport);
        vport.add(panel);
        frame.add(pane);
        frame.setSize(currentWidth, currentHeight);
        panel.setvPort(vport);
//        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
//        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
