package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.GameObject;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

import static cz.zcu.fav.ups.snake.model.snake.Snake.SCALED_SIZE;

/**
 * Grafická komponenta starající se o vykreslení ostatních hadů
 */
public class SnakeNetworkGraphicsComponent implements GraphicsComponent {

    @Override
    public void handleDraw(GameObject object, GraphicsContext graphicsContext, double divide) {
        Snake snake = (Snake) object;

        String name = snake.getName();
        float width = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().computeStringWidth(name, graphicsContext.getFont());

        graphicsContext.setFill(Color.BLACK);

        List<Tail> tails = snake.tailList;
        tails.forEach(tail -> tail.graphicsComponent.handleDraw(tail, graphicsContext, divide));

        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillOval(
                snake.pos.x - SCALED_SIZE / 2,
                snake.pos.y - SCALED_SIZE / 2,
                SCALED_SIZE,
                SCALED_SIZE
        );
        graphicsContext.fillText(name, snake.pos.x - width / 2,snake.pos.y + 20);
    }
}
