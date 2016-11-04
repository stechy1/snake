package cz.zcu.fav.ups.snake.model;

/**
 * Komponenta starající se o aktualizaci směru objektu
 */
public interface InputComponent extends IComponent {

    /**
     * Zavolá se, když příjde čas aktualizovat směr objektu
     *
     * @param object {@link GameObject} Objekt, nad kterým se provádí změny
     */
    void handleInput(GameObject object);
}
