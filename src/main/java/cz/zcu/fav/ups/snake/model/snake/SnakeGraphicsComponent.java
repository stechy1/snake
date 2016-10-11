package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Stack;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SCALED_SIZE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 * Třída představující standartní grafickou komponentu hada
 */
public class SnakeGraphicsComponent implements GraphicsComponent {

    @Override
    public void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide) {
        Snake snake = (Snake) object;

        graphicsContext.translate(-snake.pos.x, -snake.pos.y);

        List<Tail> tails = snake.tailList;
        tails.forEach(tail -> tail.graphicsComponent.handleDraw(tail, graphicsContext, divide));

        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillOval(
                snake.pos.x - SCALED_SIZE / 2,
                snake.pos.y - SCALED_SIZE / 2,
                SCALED_SIZE,
                SCALED_SIZE
        );
    }
}
