package constant;

import menu.College;

import java.awt.*;
import java.util.Arrays;

public class Constants {

    public static final String TITLE = "Agar.io 2019";
    public static final String FONT = "Arial";
    public static final int WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT;
    public static final int MAP_WIDTH = 4000;
    public static final int MAP_HEIGHT = 3000;
    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 50;
    public static final int SPACE_BETWEEN_BUTTONS_HEIGHT = 100;
    public static final int ELEMENT_OFFSET = 100;
    public static final int ACTIVE_WIDTH_START;
    public static final int ACTIVE_WIDTH_STOP;
    public static final int ACTIVE_HEIGHT_START;
    public static final int ACTIVE_HEIGHT_STOP;
    public static final int MAX_FOOD_AMOUNT = 500;
    public static final double SIZE_CHANGE = 0.5;
    public static final double VELOCITY_CHANGE = 0.1;
    public static final int COLOR_AMOUNT = 100;
    public static final String[] COLLEGES;

    public static int CURRENT_WIDTH = 2000;
    public static int CURRENT_HEIGHT = 1600;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WINDOW_WIDTH = (int) screenSize.getWidth();
        WINDOW_HEIGHT = (int) screenSize.getHeight();
        ACTIVE_WIDTH_START = WINDOW_WIDTH / 2;
        ACTIVE_WIDTH_STOP = MAP_WIDTH - WINDOW_WIDTH / 2;
        ACTIVE_HEIGHT_START = WINDOW_HEIGHT / 2;
        ACTIVE_HEIGHT_STOP = MAP_HEIGHT - WINDOW_HEIGHT / 2;

        COLLEGES = Arrays.stream(College.values())
                .map(College::name)
                .toArray(String[]::new);
    }
}
