package cz.zcu.fav.ups.snake.model;

/**
 * Základní kreslitelný objekt
 */
public abstract class BaseObject {

    // region Variables
    public final Vector2D pos = new Vector2D(); // Pozice
    public final Vector2D vel = Vector2D.ONES; // Rychlost
    public final Vector2D dir = Vector2D.ZERO; // Směr

    // Komponenta starající se o aktualizaci směru objektu
    public final InputComponent inputComponent;
    // Komponenta starající se o logiku objektu
    public final PhysicsComponent physicsComponent;
    // Komponenta starající se o vykreslení objektu na plátno
    public final GraphicsComponent graphicsComponent;
    // endregion

    // region Constructors
    /**
     * Vytvoří herní objekt
     *
     * @param inputComponent {@link InputComponent} Komponenta starající se o aktualizaci směru objektu
     * @param physicsComponent {@link PhysicsComponent} Komponenta starající se o logiku objektu
     * @param graphicsComponent {@link GraphicsComponent} Komponenta starající se o vykreslení objektu na plátno
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


}
