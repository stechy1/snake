package cz.zcu.fav.ups.snake.model;

/**
 * Komponenta starající se o logiku objektu
 */
public interface PhysicsComponent extends IComponent {

    /**
     * Zavolá se, když je potřeba aktualizovat logiku objektu
     *
     * @param object {@link GameObject} Objekt, nad kterým se má aktualizovat logika
     * @param lag Rozdíl času mezi předchozím a aktuálním snímkem v nanosekundách
     */
    void handlePhysics(GameObject object, long lag);
}
