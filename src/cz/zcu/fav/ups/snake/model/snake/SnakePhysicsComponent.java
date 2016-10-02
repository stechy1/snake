package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.PhysicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;

import java.util.List;

import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 *
 */
public class SnakePhysicsComponent implements PhysicsComponent {

    private int total = 4;

    @Override
    public void init(World world) {

    }

    @Override
    public void handlePhysics(BaseObject object, long lag) {
        Snake snake = (Snake) object;
        List<Tail> tails = snake.tailList;

        snake.dir.normalize();

        if (total == tails.size()) {
            for (int i = 0; i < tails.size() - 1; i++) {
                tails.set(i, tails.get(i + 1));
            }
        }

        tails.set(total - 1, new Tail(snake.pos, null, null, snake.tailGraphicsComponent));

        Vector2D newPos = snake.dir.copy().mul(snake.vel.copy()).mul(SIZE);
        snake.pos.add(newPos);
    }

    public int getTotal() {
        return total + 1;
    }
}
