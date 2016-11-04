package cz.zcu.fav.ups.snake.model.events;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.snake.Snake;

/**
 * Třída představuje herní event - změna směru hada
 */
public class SnakeChangeDirectionEvent implements GameEvent {

    private final int uid;
    private final Vector2D dir;

    public SnakeChangeDirectionEvent(int uid, Vector2D dir) {
        this.uid = uid;
        this.dir = dir;
    }

    @Override
    public byte[] getBytes() {
        return (String.format("changedir:%s|%s;", dir.x, dir.y)).getBytes();
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        Snake snake = (Snake) updatable;
        snake.dir.set(dir);
    }

    @Override
    public int getUserId() {
        return uid;
    }

    @Override
    public EventType getType() {
        return EventType.GAME_OBJECT;
    }
}
