package cz.zcu.fav.ups.snake.model;

/**
 * Přepravka obsahující informace o hadovi
 */
public final class SnakeInfo {

    public final String id;
    public String username;
    public final int score;
    public final Vector2D pos;
    public final Vector2D dir;

    public SnakeInfo(String id, double[] values) {
        this.id = id;
        this.pos = new Vector2D(values[0], values[1]);
        this.dir = new Vector2D(values[2], values[3]);
        this.score = (int)values[4];
    }
}
