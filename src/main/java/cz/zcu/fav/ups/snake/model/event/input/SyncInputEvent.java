package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.snake.Snake;

import java.util.List;
import java.util.Map;

import static cz.zcu.fav.ups.snake.model.Protocol.*;

/**
 * Synchronizační event
 */
public class SyncInputEvent implements InputEvent {

    private final List<String[]> snakesInfo;

    public SyncInputEvent(List<String[]> snakesInfo) {
        this.snakesInfo = snakesInfo;
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;
        Map<String, Snake> snakes = world.getSnakesOnMap();

        snakesInfo.stream()
                .filter(snakeInfo -> snakes.get(snakeInfo[INDEX_SNAKE_ID]) != null)
                .forEach(snakeInfo -> {
                    Snake snake = snakes.get(snakeInfo[INDEX_SNAKE_ID]);
                    snake.pos.set(Double.parseDouble(snakeInfo[INDEX_SNAKE_POS_X]), Double.parseDouble(snakeInfo[INDEX_SNAKE_POS_Y]));
//            snake.dir.set(Double.parseDouble(snakeInfo[INDEX_SNAKE_DIR_X]), Double.parseDouble(snakeInfo[INDEX_SNAKE_DIR_Y]));
                    snake.setScore(Integer.parseInt(snakeInfo[INDEX_SNAKE_SCORE]));
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
