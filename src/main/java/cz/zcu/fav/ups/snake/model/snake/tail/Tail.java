package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.*;
import cz.zcu.fav.ups.snake.model.events.GameEvent;

/**
 * Třída představující jeden kousek těla hada
 */
public class Tail implements GameObject {

    // region Variables
    public final Vector2D pos = new Vector2D(); // Pozice

    // Komponenta starající se o vykreslení objektu na plátno
    public final GraphicsComponent graphicsComponent;
    // endregion

    // region Constructors
    /**
     * Vytvoří nový kus těla hada
     *
     * @param graphicsComponent {@link GraphicsComponent} Komponenta starající se o vykreslení objektu na plátno
     */
    public Tail(Vector2D pos, GraphicsComponent graphicsComponent) {
        this.pos.set(pos);
        this.graphicsComponent = graphicsComponent;
    }
    // endregion

    @Override
    public String toString() {
        return String.format("Tail{X: %s, Y: %s}", pos.x, pos.y);
    }

}
