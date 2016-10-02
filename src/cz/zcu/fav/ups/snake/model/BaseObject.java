package cz.zcu.fav.ups.snake.model;

/**
 * Základní kreslitelný objekt
 */
public abstract class BaseObject {

    // region Constants
    // Počítadlo vytvořených herních objektů
    private static int COUNTER = 0;
    // endregion

    // region Variables
    public final Vector2D pos = new Vector2D(); // Pozice
    public final Vector2D vel = Vector2D.ONES; // Rychlost
    public final Vector2D dir = Vector2D.RIGHT; // Směr

    public final InputComponent inputComponent;
    public final PhysicsComponent physicsComponent;
    public final GraphicsComponent graphicsComponent;
    // endregion

    // region Constructors
//    /**
//     * Zjednodušený konstruktor herního objektu, který má výchozí souřadnice v bodě [0,0]
//     */
//    public BaseObject() {
//        COUNTER++;
//    }

    /**
     *
     *
     * @param inputComponent
     * @param physicsComponent
     * @param graphicsComponent
     */
    public BaseObject(InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent) {
        this.inputComponent = inputComponent;
        this.physicsComponent = physicsComponent;
        this.graphicsComponent = graphicsComponent;
    }

    // endregion

    public void init(World world) {
        inputComponent.init(world);
        physicsComponent.init(world);
        graphicsComponent.init(world);
    }

//    /**
//     * Metoda, která se zavolá pro aktualizaci stavu objektu
//     *
//     * @param timestamp Doba od posledního updatu
//     */
//    public abstract void update(long timestamp);
//
//    /**
//     * Vykreslí kreslitelný předmět
//     *
//     * @param graphicsContext {@link GraphicsContext}
//     */
//    public abstract void draw(GraphicsContext graphicsContext);

    // region Getters & Setters
    // endregion
}
