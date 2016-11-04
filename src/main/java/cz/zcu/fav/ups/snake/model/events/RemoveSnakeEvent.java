package cz.zcu.fav.ups.snake.model.events;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.World;

/**
 * Třída představující event - odebrat hada
 */
public class RemoveSnakeEvent implements GameEvent {

    private final int uid;

    public RemoveSnakeEvent(int uid) {
        this.uid = uid;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;

        world.removeSnake(uid);
    }

    @Override
    public int getUserId() {
        return uid;
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
