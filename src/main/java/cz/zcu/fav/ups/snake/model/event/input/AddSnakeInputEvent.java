package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.snake.*;
import cz.zcu.fav.ups.snake.model.snake.tail.TailCircleGraphicsComponent;

import static cz.zcu.fav.ups.snake.model.Protocol.*;

/**
 * Třída představující event přidávající hada do hry
 */
public class AddSnakeInputEvent implements InputEvent {

    private final Snake snake;

    public AddSnakeInputEvent(String[] snakeInfo) {
        snake = new Snake(
                snakeInfo[INDEX_SNAKE_ID],
                snakeInfo[INDEX_SNAKE_USERNAME],
                Integer.parseInt(snakeInfo[INDEX_SNAKE_SCORE]),
                new SnakeNetworkInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeNetworkGraphicsComponent(),
                new Vector2D(Double.parseDouble(snakeInfo[INDEX_SNAKE_POS_X]), Double.parseDouble(snakeInfo[INDEX_SNAKE_POS_Y])),
                new Vector2D(Double.parseDouble(snakeInfo[INDEX_SNAKE_DIR_X]), Double.parseDouble(snakeInfo[INDEX_SNAKE_DIR_Y])),
                new TailCircleGraphicsComponent()
        );
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;

        world.addSnake(snake);
    }

    @Override
    public String getUserID() {
        return snake.getID();
    }

    @Override
    public String getDescription() {
        return "AddSnakeInputEvent";
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
