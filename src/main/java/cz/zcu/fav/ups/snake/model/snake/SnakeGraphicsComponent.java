package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.GameObject;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

import static cz.zcu.fav.ups.snake.model.snake.Snake.SCALED_SIZE;

/**
 * Třída představující standartní grafickou komponentu hada
 */
public class SnakeGraphicsComponent implements GraphicsComponent {

    private int world_width;

    @Override
    public void init(World world) {
        world_width = world.getWidth();
    }

    @Override
    public void handleDraw(GameObject object, GraphicsContext graphicsContext, double divide) {
        Snake snake = (Snake) object;

        graphicsContext.setFill(Color.BLACK);
        String position = String.format("Snake pos: X:%3d,Y:%3d", (int)snake.pos.x, (int)snake.pos.y);
        graphicsContext.fillText(position, -world_width / 2 + 50, -50);
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
