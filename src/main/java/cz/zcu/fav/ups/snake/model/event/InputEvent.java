package cz.zcu.fav.ups.snake.model.event;

import cz.zcu.fav.ups.snake.model.IUpdatable;

/**
 * Rozhraní definující metody pro vstupní event
 */
public interface InputEvent {

    /**
     * Aplikuje event na zadaný objekt
     * @param updatable {@link IUpdatable}
     */
    void applyEvent(IUpdatable updatable);

    /**
     * @return Vrátí userID uživatele, kterého se evet týká
     */
    int getUserID();

    /**
     * @return Vrátí popis eventu
     */
    String getDescription();

    /**
     * @return Vrátí typ eventu
     */
    EventType getType();

}
