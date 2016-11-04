package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.*;
import cz.zcu.fav.ups.snake.model.events.GameEvent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SCALED_SIZE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 * Třída představující logiku hada
 */
public class SnakePhysicsComponent implements PhysicsComponent {

    @Override
    public void handlePhysics(GameObject object, long lag) {
        final Snake snake = (Snake) object;
        final LinkedList<Tail> tails = snake.tailList;

        if (tails.size() > 0) {
            tails.removeFirst();
        }

        tails.addLast(new Tail(snake.pos.copy(), snake.tailGraphicsComponent));

        final Vector2D newPos = Vector2D.mul(snake.dir, snake.vel).mul(SIZE);
        snake.pos.add(newPos);
    }
}
