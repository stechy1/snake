package cz.zcu.fav.ups.snake.model.snake.tail;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
        graphicsContext.fillRect(tail.pos.x + (tail.dir.x * divide) , tail.pos.y + (tail.dir.y * divide), SIZE, SIZE);
    }
}
