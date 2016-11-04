package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.GameObject;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 * Třída představující grafickou komponentu hada, která vykreslí tělo ve stylu kostičkované duhy
 */
public class TailRainbowGraphicsComponent implements GraphicsComponent {

    // region Constants
    private static final Color[] tailColors = new Color[] {
            Color.CYAN,
            Color.RED
    };
    // endregion

    // region Variables
    private static int index = 0;
    // endregion

    @Override
    public void handleDraw(GameObject object, GraphicsContext graphicsContext, double divide) {
        Tail tail = (Tail) object;

        graphicsContext.setFill(tailColors[index % 2]);
        graphicsContext.fillRect(
                tail.pos.x - (SIZE / SCALE), tail.pos.y - (SIZE / SCALE),
                SIZE  / SCALE, SIZE  / SCALE
        );

        index++;
    }
}
