package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;

/**
 * Třída představující event - odebrat hada
 */
public class RemoveSnakeInputEvent implements InputEvent {

    private final int uid;

    public RemoveSnakeInputEvent(int uid) {
        this.uid = uid;
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;
        world.removeSnake(uid);
    }

    @Override
    public int getUserID() {
        return uid;
    }

    @Override
    public String getDescription() {
        return "RemoveSnakeInputEvent";
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
