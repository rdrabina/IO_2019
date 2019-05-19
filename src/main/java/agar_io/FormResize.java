package agar_io;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static constant.Constants.*;
// This kind of form reacts on changes in window's size
public class FormResize extends JFrame {


    public FormResize(String title) {
        setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(title);
        setVisible(true);

        getContentPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                CURRENT_WIDTH = c.getWidth();
                CURRENT_HEIGHT = c.getHeight();
            }
        });
    }

}
