package cz.zcu.fav.ups.snake.model.event.output;

import cz.zcu.fav.ups.snake.model.event.OutputEvent;

/**
 * Třída představující přihlašovací event
 */
public class LoginOutputEvent implements OutputEvent {

    private final String username;

    public LoginOutputEvent(String username) {
        this.username = username;
    }

    @Override
    public byte[] getData() {
        return (String.format("login:%s;", username)).getBytes();
    }

    @Override
    public String getDescription() {
        return "LoginOutputEvent";
    }
}
