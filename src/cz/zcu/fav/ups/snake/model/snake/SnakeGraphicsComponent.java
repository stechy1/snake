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
 *
 */
public class SnakeGraphicsComponent implements GraphicsComponent {

    private static final int LINE_LENGTH = 10;

    @Override
    public void init(World world) {

    }

    @Override
    public void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide) {
        Snake snake = (Snake) object;

        graphicsContext.translate(-snake.pos.x, -snake.pos.y);

        List<Tail> tails = snake.tailList;
        tails.forEach(tail -> tail.graphicsComponent.handleDraw(tail, graphicsContext, divide));

        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(
                snake.pos.x + (snake.dir.x * divide) - SCALED_SIZE, snake.pos.y + (snake.dir.x * divide) - (SIZE / SCALE),
                SCALED_SIZE, SCALED_SIZE
        );

        graphicsContext.setStroke(Color.RED);
        graphicsContext.strokeLine(
                snake.pos.x + (snake.dir.x * divide) - SCALED_SIZE / 2, snake.pos.y + (snake.dir.y * divide) - SCALED_SIZE / 2,
                snake.pos.x + (snake.dir.x * divide) + snake.dir.x * LINE_LENGTH / SCALE, snake.pos.y + (snake.dir.y * divide) + snake.dir.y * LINE_LENGTH / SCALE);


    }
}
