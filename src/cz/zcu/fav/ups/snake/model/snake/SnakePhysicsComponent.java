package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.PhysicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SCALED_SIZE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 * Třída představující logiku hada
 */
public class SnakePhysicsComponent implements PhysicsComponent {

    @Override
    public void handlePhysics(BaseObject object, long lag) {
        final Snake snake = (Snake) object;
        final LinkedList<Tail> tails = snake.tailList;

        snake.dir.normalize();

        if (tails.size() > 0) {
            tails.removeFirst();
        }

        final Vector2D tailPos = Vector2D.sub(snake.pos, snake.dir.x * SCALED_SIZE, snake.dir.y * SCALED_SIZE);
        tails.addLast(new Tail(tailPos, snake.tailGraphicsComponent));

        final Vector2D newPos = Vector2D.mul(snake.dir, snake.vel).mul(SIZE);
        snake.pos.add(newPos);
    }
}
