package cz.zcu.fav.ups.snake.utils;

/**
 * Pomocná knihovní třída obsahující užitečné metody
 */
public class SnakeUtils {

    public static double lerp(double start, double stop, double amt) {
        return amt*(stop-start)+start;
    }

}
