package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.GameObject;
import cz.zcu.fav.ups.snake.model.InputComponent;
import cz.zcu.fav.ups.snake.model.event.InputEvent;

/**
 * Třída předstaující vstupní komponentu ovládající ostatní hady
 */
public class SnakeNetworkInputComponent implements InputComponent {

    @Override
    public void handleInput(GameObject object) {
        Snake snake = (Snake) object;
        InputEvent event = snake.events.poll();
        if (event == null) {
            return;
        }

        event.applyEvent(snake);
    }
}
