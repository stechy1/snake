package cz.zcu.fav.ups.snake.model;

/**
 * Základní rozhraní všech komponent
 */
interface IComponent {

    /**
     * Zaválá se, když je potřeba inicializovat komponentu
     *
     * @param world {@link World} Svět, který komponenta ovlivňuje
     */
    default void init(World world) {}

}
