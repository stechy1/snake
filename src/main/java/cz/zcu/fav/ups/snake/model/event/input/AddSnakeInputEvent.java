package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.snake.*;
import cz.zcu.fav.ups.snake.model.snake.tail.TailCircleGraphicsComponent;

/**
 * Třída představující event přidávající hada do hry
 */
public class AddSnakeInputEvent implements InputEvent {

    private final Snake snake;

    public AddSnakeInputEvent(double[] snakeInfo) {
        snake = new Snake(
                (int)snakeInfo[0],
                (int)snakeInfo[5],
                new SnakeNetworkInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeNetworkGraphicsComponent(),
                new Vector2D(snakeInfo[1], snakeInfo[2]),
                new Vector2D(snakeInfo[3], snakeInfo[4]),
                new TailCircleGraphicsComponent()
        );
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;

        world.addSnake(snake);
    }

    @Override
    public int getUserID() {
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
