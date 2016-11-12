package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.snake.Snake;

/**
 * Třída představuje herní event - změna směru hada
 */
public class SnakeChangeDirectionInputEvent implements InputEvent {

    private final int uid;
    private final Vector2D dir;

    public SnakeChangeDirectionInputEvent(int uid, Vector2D dir) {
        this.uid = uid;
        this.dir = dir;
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        Snake snake = (Snake) updatable;
        System.out.println("Měním směr hadovi: " + uid + "; " + dir);
        snake.dir.set(dir);
    }

    @Override
    public int getUserID() {
        return uid;
    }

    @Override
    public String getDescription() {
        return "SnakeChangeDirectionInputEvent";
    }

    @Override
    public EventType getType() {
        return EventType.GAME_OBJECT;
    }
}
