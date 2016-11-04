package cz.zcu.fav.ups.snake.model.events;

import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.snake.Snake;
import cz.zcu.fav.ups.snake.model.snake.SnakeGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.SnakeNetworkInputComponent;
import cz.zcu.fav.ups.snake.model.snake.SnakePhysicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.TailCircleGraphicsComponent;

/**
 * Třída představující event - přidání hada do hry
 */
public class AddSnakeEvent implements GameEvent {

    private final int uid;
    private final Snake snake;

    public AddSnakeEvent(int uid, double[] snakeInfo) {
        this.uid = uid;
        snake = new Snake(
                (int)snakeInfo[0],
                (int)snakeInfo[5],
                new SnakeNetworkInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeGraphicsComponent(),
                new Vector2D(snakeInfo[1], snakeInfo[2]),
                new Vector2D(snakeInfo[3], snakeInfo[4]),
                new TailCircleGraphicsComponent()
        );
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;

        world.addSnake(uid, snake);
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
