package cz.zcu.fav.ups.snake.model.events;

/**
 * Debugovací event, který se posílá při stisku debugovacího tlačítka
 */
public class DebugEvent extends GameEvent {

    private final String content;

    public DebugEvent(String content) {
        this.content = content;
    }

    @Override
    public byte[] getBytes() {
        return content.getBytes();
    }
}
