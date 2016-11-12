package cz.zcu.fav.ups.snake.model;

/**
 * Přepravka obsahující informace o jídle
 */
public final class FoodInfo {

    public final int id;
    public final Vector2D pos;

    public FoodInfo(double[]values) {
        id = (int) values[0];
        pos = new Vector2D(values[1], values[2]);
    }
}
