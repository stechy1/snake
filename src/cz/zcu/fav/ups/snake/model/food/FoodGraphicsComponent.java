package cz.zcu.fav.ups.snake.model.food;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static cz.zcu.fav.ups.snake.model.World.SCALE;
import static cz.zcu.fav.ups.snake.model.food.Food.SIZE;

/**
 *
 */
public class FoodGraphicsComponent implements GraphicsComponent {

    @Override
    public void init(World world) {

    }

    @Override
    public void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide) {
        Food food = (Food) object;

        graphicsContext.setFill(Color.ORANGE);
        graphicsContext.fillRect(food.pos.x - (SIZE / SCALE), food.pos.y - (SIZE / SCALE),
                SIZE  / SCALE, SIZE  / SCALE);
    }
}
