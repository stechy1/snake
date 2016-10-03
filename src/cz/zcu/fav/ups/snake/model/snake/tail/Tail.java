package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.InputComponent;
import cz.zcu.fav.ups.snake.model.PhysicsComponent;

/**
 * Třída představující jeden kousek těla hada
 */
public class Tail extends BaseObject {

    // region Constructors
    /**
     * Vytvoří nový kus těla hada
     *
     * @param graphicsComponent {@link GraphicsComponent} Komponenta starající se o vykreslení objektu na plátno
     */
    public Tail(Vector2D pos, GraphicsComponent graphicsComponent) {
        super(null, null, graphicsComponent);

        this.pos.set(pos);
    }
    // endregion

    @Override
    public String toString() {
        return String.format("Tail{X: %s, Y: %s}", pos.x, pos.y);
    }
}
