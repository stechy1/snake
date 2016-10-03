package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 *
 */
public class TailRainbowGraphicsComponent implements GraphicsComponent {

    private static final Color[] tailColors = new Color[] {
            Color.CYAN,
            Color.RED
    };

    private static int index = 0;

    @Override
    public void init(World world) {

    }

    @Override
    public void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide) {
        Tail tail = (Tail) object;

        graphicsContext.setFill(tailColors[index % 2]);
        graphicsContext.fillRect(
                tail.pos.x - (SIZE / SCALE), tail.pos.y - (SIZE / SCALE),
                SIZE  / SCALE, SIZE  / SCALE
        );

        index++;
    }
}
