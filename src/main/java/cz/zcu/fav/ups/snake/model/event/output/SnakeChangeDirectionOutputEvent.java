package cz.zcu.fav.ups.snake.model.event.output;

import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.event.OutputEvent;

/**
 * Výstupní event představující data, která se pošlou serveru
 */
public class SnakeChangeDirectionOutputEvent implements OutputEvent {

    private final Vector2D dir;

    public SnakeChangeDirectionOutputEvent(Vector2D dir) {
        this.dir = dir;
    }

    @Override
    public byte[] getData() {
        return (String.format("changedir:%s|%s;", dir.x, dir.y)).getBytes();
    }

    @Override
    public String getDescription() {
        return "SnakeChangeDirectionOutputEvent";
    }
}
