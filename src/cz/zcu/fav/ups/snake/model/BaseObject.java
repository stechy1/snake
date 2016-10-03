package cz.zcu.fav.ups.snake.model;

/**
 * Základní kreslitelný objekt
 */
public abstract class BaseObject {

    // region Variables
    public final Vector2D pos = new Vector2D(); // Pozice
    public final Vector2D vel = Vector2D.ONES; // Rychlost
    public final Vector2D dir = Vector2D.ZERO; // Směr

    public final InputComponent inputComponent;
    public final PhysicsComponent physicsComponent;
    public final GraphicsComponent graphicsComponent;
    // endregion

    // region Constructors

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


}
