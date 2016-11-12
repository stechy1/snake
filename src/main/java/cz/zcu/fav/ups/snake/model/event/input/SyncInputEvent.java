package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.SnakeInfo;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.snake.Snake;

import java.util.List;
import java.util.Map;

/**
 * Synchronizační event
 */
public class SyncInputEvent implements InputEvent {

    private final List<SnakeInfo> snakesInfo;

    public SyncInputEvent(List<SnakeInfo> snakesInfo) {
        this.snakesInfo = snakesInfo;
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;
        Map<String, Snake> snakes = world.getSnakesOnMap();

        snakesInfo.forEach(snakeInfo -> {
            Snake snake = snakes.get(snakeInfo.id);
            snake.pos.set(snakeInfo.pos);
//            snake.dir.set(dir);
            snake.setScore(snakeInfo.score);
        });

    }

    @Override
    public String getUserID() {
        return "";
    }

    @Override
    public String getDescription() {
        return "SyncEvent";
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
