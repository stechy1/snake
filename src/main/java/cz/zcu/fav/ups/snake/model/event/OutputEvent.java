package cz.zcu.fav.ups.snake.model.event;

/**
 * Rozhraní definující metody pro výstupní event
 */
public interface OutputEvent {

    /**
     * @return Vrátí pole bytů, které se pošloe ven
     */
    byte[] getData();

    /**
     * @return Vrátí popis eventu
     */
    String getDescription();

}
