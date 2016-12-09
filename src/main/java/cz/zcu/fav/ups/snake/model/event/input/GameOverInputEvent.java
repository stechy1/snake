package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;

/**
 *
 */
public class GameOverInputEvent implements InputEvent {

    private final String uuid;

    public GameOverInputEvent(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;

        world.stop();
    }

    @Override
    public String getUserID() {
        return uuid;
    }

    @Override
    public String getDescription() {
        return "GameOverEvent";
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
