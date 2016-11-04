package cz.zcu.fav.ups.snake.model.events;

import cz.zcu.fav.ups.snake.model.IUpdatable;

/**
 * Rozhraní definující metody pro herní eventy
 */
public interface GameEvent {

    /**
     * @return Vrátí byty, které představují jeden herní event
     */
     byte[] getBytes();

    /**
     * Aplikuje event na zadaný objekt
     *
     * @param updatable {@link IUpdatable}
     */
    void applyEvent(IUpdatable updatable);

    /**
     * @return Vrátí ID uživatele, na kterého se event vztahuje
     */
    int getUserId();

    EventType getType();

    enum EventType {
        WORLD, GAME_OBJECT
    }

}
