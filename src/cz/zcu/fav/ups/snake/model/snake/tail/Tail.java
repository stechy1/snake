package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.InputComponent;
import cz.zcu.fav.ups.snake.model.PhysicsComponent;

/**
 *
 */
public class Tail extends BaseObject {

    public Tail(Vector2D pos, GraphicsComponent graphicsComponent) {
        this(pos, null, null, graphicsComponent);
    }

    /**
     * @param inputComponent
     * @param physicsComponent
     * @param graphicsComponent
     */
    public Tail(Vector2D pos, InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent) {
        super(inputComponent, physicsComponent, graphicsComponent);

        this.pos.set(pos);
    }
}
