package cz.zcu.fav.ups.snake.model.food;

import cz.zcu.fav.ups.snake.model.*;

/**
 * Třída představující jídlo, které had pojída
 */
public class Food implements GameObject {

    // region Constants
    static final int SIZE = 10;
    // endragion

    // region Variables
    public final Vector2D pos = new Vector2D(); // Pozice

    // Komponenta starající se o vykreslení objektu na plátno
    public final GraphicsComponent graphicsComponent;

    // ID jídla
    public final int id;
    // endregion

    /**
     * Vytvoří nové jídlo
     *
     * @param graphicsComponent {@link GraphicsComponent}
     */
    public Food(int id, Vector2D pos, GraphicsComponent graphicsComponent) {
        this.id = id;
        this.pos.set(pos);
        this.graphicsComponent = graphicsComponent;
    }
}
