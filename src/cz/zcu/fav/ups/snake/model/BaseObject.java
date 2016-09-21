package cz.zcu.fav.ups.snake.model;

import javafx.scene.canvas.GraphicsContext;

/**
 * Základní kreslitelný objekt
 */
public abstract class BaseObject {

    // region Constants
    // Počítadlo vytvořených herních objektů
    private static int COUNTER = 0;
    // endregion

    // region Variables
    protected Vector2D pos = new Vector2D(); // Pozice
    protected Vector2D vel = Vector2D.ONES; // Rychlost
    protected Vector2D dir = Vector2D.RIGHT; // Směr
    // endregion

    // region Constructors
    /**
     * Zjednodušený konstruktor herního objektu, který má výchozí souřadnice v bodě [0,0]
     */
    public BaseObject() {
        COUNTER++;
    }
    // endregion

    /**
     * Metoda, která se zavolá pro aktualizaci stavu objektu
     *
     * @param timestamp Doba od posledního updatu
     */
    public abstract void update(long timestamp);

    /**
     * Vykreslí kreslitelný předmět
     *
     * @param graphicsContext {@link GraphicsContext}
     */
    public abstract void draw(GraphicsContext graphicsContext);

    // region Getters & Setters
    // endregion
}
