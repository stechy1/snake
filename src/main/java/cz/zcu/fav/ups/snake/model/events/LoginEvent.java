package cz.zcu.fav.ups.snake.model.events;

import cz.zcu.fav.ups.snake.model.IUpdatable;

/**
 * Třída představující přihlašovací event
 */
public class LoginEvent implements GameEvent {

    private final String username;

    public LoginEvent(String username) {
        this.username = username;
    }

    @Override
    public byte[] getBytes() {
        return (String.format("login:%s;", username)).getBytes();
    }

    @Override
    public void applyEvent(IUpdatable updatable) {}

    @Override
    public int getUserId() {
        return -1;
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
