package cz.zcu.fav.ups.snake.model.events;

import cz.zcu.fav.ups.snake.model.IUpdatable;

/**
 * Debugovací event, který se posílá při stisku debugovacího tlačítka
 */
public class DebugEvent implements GameEvent {

    private final String content;

    public DebugEvent(String content) {
        this.content = content;
    }

    @Override
    public byte[] getBytes() {
        return content.getBytes();
    }

    @Override
    public void applyEvent(IUpdatable updatable) {

    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public EventType getType() {
        return null;
    }
}
