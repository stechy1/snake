package cz.zcu.fav.ups.snake.model.event.output;

import cz.zcu.fav.ups.snake.model.event.OutputEvent;

/**
 * Debugovací event, který se posílá při stisku debugovacího tlačítka
 */
public class DebugOutputEvent implements OutputEvent {

    private final String content;

    public DebugOutputEvent(String content) {
        this.content = content;
    }

    @Override
    public byte[] getData() {
        return content.getBytes();
    }

    @Override
    public String getDescription() {
        return "DebugOutputEvent";
    }
}
