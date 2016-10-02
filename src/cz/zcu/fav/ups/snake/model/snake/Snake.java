package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.InputComponent;
import cz.zcu.fav.ups.snake.model.PhysicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;
import cz.zcu.fav.ups.snake.model.snake.tail.TailGraphicsComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Snake extends BaseObject {

    public static final int SIZE = 10;

    final List<Tail> tailList = new ArrayList<>();

    final GraphicsComponent tailGraphicsComponent = new TailGraphicsComponent();
    //public final PhysicsComponent tailPhysicsComponent = new TailPhysicsComponent();

    /**
     * @param inputComponent
     * @param physicsComponent
     * @param graphicsComponent
     */
    public Snake(InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent) {
        super(inputComponent, physicsComponent, graphicsComponent);

        init();
    }

    private void init() {
        tailList.addAll(Arrays.asList(
                new Tail(new Vector2D(pos.x - 1 * SIZE, pos.y), tailGraphicsComponent),
                new Tail(new Vector2D(pos.x - 2 * SIZE, pos.y), tailGraphicsComponent),
                new Tail(new Vector2D(pos.x - 3 * SIZE, pos.y), tailGraphicsComponent),
                new Tail(new Vector2D(pos.x - 3 * SIZE, pos.y), tailGraphicsComponent)
        ));
    }

}
