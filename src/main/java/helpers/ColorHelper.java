package helpers;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class ColorHelper {

    public static Color getRandomColor() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return new Color(current.nextInt(255), current.nextInt(255), current.nextInt(255));
    }
}
