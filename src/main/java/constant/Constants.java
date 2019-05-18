package constant;

import java.awt.*;

public class Constants {

    public static final String TITLE = "Agar.io 2019";
    public static final String FONT = "Arial";
    public static final int WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT;
    public static final int MAP_WIDTH = 4000;
    public static final int MAP_HEIGHT = 3000;
    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 50;
    public static final int OVAL_WIDTH = 150;
    public static final int OVAL_HEIGHT = 150;
    public static final int SPACE_BETWEEN_BUTTONS_HEIGHT = 100;
    public static final int ELEMENT_OFFSET = 100;
    public static final int TITLE_HEIGHT = 200;
    
    public static int currentWidth = 2000;
    public static int currentHeight = 1600;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WINDOW_WIDTH = (int) screenSize.getWidth();
        WINDOW_HEIGHT = (int) screenSize.getHeight();
    }
}
