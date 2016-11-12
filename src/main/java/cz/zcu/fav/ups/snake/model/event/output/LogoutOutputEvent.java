package cz.zcu.fav.ups.snake.model.event.output;

import cz.zcu.fav.ups.snake.model.event.OutputEvent;

/**
 * Odhlašovací event
 */
public class LogoutOutputEvent implements OutputEvent {

    @Override
    public byte[] getData() {
        return "logout;".getBytes();
    }

    @Override
    public String getDescription() {
        return "LogoutEvent";
    }
}
