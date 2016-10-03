package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.PhysicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;

import java.util.List;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 *
 */
public class SnakePhysicsComponent implements PhysicsComponent {

    @Override
    public void init(World world) {

    }

    @Override
    public void handlePhysics(BaseObject object, long lag) {
        Snake snake = (Snake) object;
        List<Tail> tails = snake.tailList;

        snake.dir.normalize();

        // Nesnědl jsem žádné jídlo
        if (snake.getTotal() == tails.size()) {
            for (int i = 0; i < tails.size() - 1; i++) {
                tails.set(i, tails.get(i + 1));
            }
        }

//        Vector2D tailPos = Vector2D.mul(snake.pos, SCALE).sub(SIZE / SCALE);
        Vector2D tailPos = Vector2D.sub(snake.pos, snake.dir.x * (SIZE / SCALE), snake.dir.y * (SIZE / SCALE));
        //System.out.println(tailPos);
        tails.set(snake.getTotal() - 1, new Tail(tailPos, snake.tailGraphicsComponent));

        Vector2D newPos = Vector2D.mul(snake.dir, snake.vel).mul(SIZE * SCALE);
        snake.pos.add(newPos);
    }
}
