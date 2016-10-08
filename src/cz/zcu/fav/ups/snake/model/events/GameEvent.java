package cz.zcu.fav.ups.snake.model.events;

/**
 *
 */
public abstract class GameEvent {

    /**
     * @return Vrátí byty, které představují jeden herní event
     */
    public abstract byte[] getBytes();
}
