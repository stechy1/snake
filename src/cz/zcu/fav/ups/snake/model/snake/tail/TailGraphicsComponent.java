package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 * Třída představující grafickou komponentu hada, která vykreslí tělo ve stylu malých jednobarevných kostiček
 */
public class TailGraphicsComponent implements GraphicsComponent {

    @Override
    public void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide) {
        Tail tail = (Tail) object;

        graphicsContext.setFill(Color.CYAN);
        graphicsContext.fillRect(
                tail.pos.x - (SIZE / SCALE), tail.pos.y - (SIZE / SCALE),
                SIZE  / SCALE, SIZE  / SCALE
        );
    }
}
