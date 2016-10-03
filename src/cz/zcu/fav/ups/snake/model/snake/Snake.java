package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.*;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;
import cz.zcu.fav.ups.snake.model.snake.tail.TailGraphicsComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cz.zcu.fav.ups.snake.model.World.SCALE;

/**
 *
 */
public class Snake extends BaseObject {

    public static final int SIZE = 15;

    final List<Tail> tailList = new ArrayList<>();

    final GraphicsComponent tailGraphicsComponent = new TailGraphicsComponent();

    // Počet kusů ocasu
    private int total = 10;
    //public final PhysicsComponent tailPhysicsComponent = new TailPhysicsComponent();

    /**
     * @param inputComponent
     * @param physicsComponent
     * @param graphicsComponent
     */
    public Snake(InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent) {
        super(inputComponent, physicsComponent, graphicsComponent);
    }

    @Override
    public void init(World world) {
        super.init(world);

        for (int i = 0; i <= total; i++) {
            Vector2D tailPos = Vector2D.sub(pos, dir.x * (i + 1) * (SIZE), dir.y * (i + 1) * (SIZE));
            tailList.add(new Tail(tailPos, tailGraphicsComponent));
        }

    }

    public int getTotal() {
        return total + 1;
    }

    @Override
    public String toString() {
        return String.format("Snake{X: %s, Y: %s}", pos.x, pos.y);
    }
}
