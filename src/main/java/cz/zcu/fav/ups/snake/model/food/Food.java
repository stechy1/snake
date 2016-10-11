package cz.zcu.fav.ups.snake.model.food;

import cz.zcu.fav.ups.snake.model.*;

/**
 *
 */
public class Food extends BaseObject {

    static final int SIZE = 10;

    /**
     *
     *
     * @param graphicsComponent
     */
    public Food(Vector2D pos, GraphicsComponent graphicsComponent) {
        super(null, null, graphicsComponent);

        this.pos.set(pos);
    }
}
