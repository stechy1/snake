package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.Vector2D;
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

    private final List<double[]> snakesInfo;

    public SyncInputEvent(List<double[]> snakesInfo) {
        this.snakesInfo = snakesInfo;
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;
        Map<Integer, Snake> snakes = world.getSnakesOnMap();

        snakesInfo.forEach(snakeInfo -> {
            int index = (int)snakeInfo[0];
            Vector2D pos = new Vector2D(snakeInfo[1], snakeInfo[2]);
            Vector2D dir = new Vector2D(snakeInfo[3], snakeInfo[4]);
            int score = (int)snakeInfo[5];

            Snake snake = snakes.get(index);
            snake.pos.set(pos);
//            snake.dir.set(dir);
            snake.setScore(score);
        });

    }

    @Override
    public int getUserID() {
        return -1;
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
