package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.GameObject;
import cz.zcu.fav.ups.snake.model.PhysicsComponent;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;

import java.util.List;

import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 * Třída představující logiku hada
 */
public class SnakePhysicsComponent implements PhysicsComponent {

    private int world_width;
    private int world_height;

    @Override
    public void init(World world) {
        world_width = world.getWidth();
        world_height = world.getHeight();
    }

    @Override
    public void handlePhysics(GameObject object, long lag) {
        final Snake snake = (Snake) object;
        final List<Tail> tails = snake.tailList;

        if (tails.size() > 0) {
            tails.remove(0);
        }

        tails.add(new Tail(snake.pos.copy(), snake.tailGraphicsComponent));

        final Vector2D newPos = Vector2D.mul(snake.dir, snake.vel).mul(SIZE);
        snake.pos.add(newPos);

        if (snake.pos.x > world_width) {
            snake.pos.x = -world_width;
        }
        if (snake.pos.x < -world_width) {
            snake.pos.x = world_width;
        }
        if (snake.pos.y > world_height) {
            snake.pos.y = -world_height;
        }
        if (snake.pos.y < -world_height) {
            snake.pos.y = world_height;
        }
    }
}
