package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.GameObject;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static cz.zcu.fav.ups.snake.model.snake.Snake.SCALED_SIZE;

/**
 * Třída představující grafickou komponentu hada, která vykreslí tělo ve stylu malých kruhů
 */
public class TailCircleGraphicsComponent implements GraphicsComponent {

    @Override
    public void handleDraw(GameObject object, GraphicsContext graphicsContext, double divide) {
        Tail tail = (Tail) object;

        graphicsContext.setFill(Color.CYAN);
        graphicsContext.fillOval(
                tail.pos.x - SCALED_SIZE / 2,
                tail.pos.y - SCALED_SIZE / 2,
                SCALED_SIZE, SCALED_SIZE);
    }
}
