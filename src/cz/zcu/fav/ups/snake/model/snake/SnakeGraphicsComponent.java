package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 *
 */
public class SnakeGraphicsComponent implements GraphicsComponent {

//    private static final int LINE_LENGTH = 10;

    @Override
    public void init(World world) {

    }

    @Override
    public void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide) {
        Snake snake = (Snake) object;

        graphicsContext.translate(-snake.pos.x, -snake.pos.y);

        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(snake.pos.x, snake.pos.y, SIZE, SIZE);

//        graphicsContext.setStroke(Color.RED);
//        graphicsContext.strokeLine(
//                snake.pos.x + (snake.dir.x * divide), snake.pos.y + (snake.dir.y * divide),
//                snake.pos.x + (snake.dir.x * divide) + snake.dir.x * LINE_LENGTH, snake.pos.y + (snake.dir.y * divide) + snake.dir.y * LINE_LENGTH);

        List<Tail> tails = snake.tailList;
        tails.forEach(tail -> tail.graphicsComponent.handleDraw(tail, graphicsContext, divide));
    }
}
