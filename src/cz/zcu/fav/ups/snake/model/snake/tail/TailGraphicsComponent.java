package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.snake.Snake.SIZE;

/**
 *
 */
public class TailGraphicsComponent implements GraphicsComponent {


    @Override
    public void init(World world) {

    }

    @Override
    public void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide) {
        Tail tail = (Tail) object;

        graphicsContext.setFill(Color.CYAN);
        graphicsContext.fillRect(
                tail.pos.x - (SIZE / SCALE), tail.pos.y - (SIZE / SCALE),
                SIZE  / SCALE, SIZE  / SCALE
        );
//        graphicsContext.fillRect(
//                tail.pos.x / SCALE  - (SIZE / SCALE) + ((tail.dir.x / SCALE  - (SIZE / SCALE)) * divide) , tail.pos.y / SCALE  - (SIZE / SCALE) + ((tail.dir.y / SCALE - (SIZE / SCALE)) * divide),
//                SIZE  / SCALE, SIZE  / SCALE);
    }
}
