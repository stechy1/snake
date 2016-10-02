package cz.zcu.fav.ups.snake.model;

/**
 *
 */
public interface PhysicsComponent extends IComponent {

    /**
     *
     *
     * @param object
     * @param lag
     */
    void handlePhysics(BaseObject object, long lag);

}
