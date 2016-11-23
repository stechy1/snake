package cz.zcu.fav.ups.snake.model.event.input;

import cz.zcu.fav.ups.snake.model.*;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.food.Food;
import cz.zcu.fav.ups.snake.model.food.FoodGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.*;
import cz.zcu.fav.ups.snake.model.snake.tail.TailCircleGraphicsComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cz.zcu.fav.ups.snake.model.Protocol.*;

/**
 * Třída představující inicializační event, který inicializuje celou mapu
 */
public class InitInputEvent implements InputEvent {

    private static final int INDEX_SNAKE_FOOD_ID = 0;
    private static final int INDEX_SNAKE_FOOD_POS_X = 1;
    private static final int INDEX_SNAKE_FOOD_POS_Y = 2;

    private static final GraphicsComponent tailGraphicComponent = new TailCircleGraphicsComponent();

    private final Snake mySnake;
    private final List<Snake> snakes = new ArrayList<>();
    private final List<Food> foodList = new ArrayList<>();
    private final double worldWidth;
    private final double worldHeight;

    public InitInputEvent(String[] mySnakeInfo, String[] worldSizeInfo, List<String[]> othersSnakeInfo, List<String[]> foodInfo) {
        System.out.println("Init event");
        this.mySnake = new Snake(
                mySnakeInfo[INDEX_SNAKE_ID],
                mySnakeInfo[INDEX_SNAKE_USERNAME],
                Integer.valueOf(mySnakeInfo[INDEX_SNAKE_SCORE]),
                new SnakeMouseInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeGraphicsComponent(),
                new Vector2D(Double.valueOf((mySnakeInfo[INDEX_SNAKE_POS_X])), Double.valueOf(mySnakeInfo[INDEX_SNAKE_POS_Y])),
                new Vector2D(Double.valueOf((mySnakeInfo[INDEX_SNAKE_DIR_X])), Double.valueOf(mySnakeInfo[INDEX_SNAKE_DIR_Y])),
                tailGraphicComponent
        );
        snakes.add(mySnake);
        worldWidth = Double.valueOf(worldSizeInfo[0]);
        worldHeight = Double.valueOf(worldSizeInfo[1]);
        othersSnakeInfo
                .stream()
                .filter(values -> !Objects.equals(values[INDEX_SNAKE_ID], mySnakeInfo[INDEX_SNAKE_ID]))
                .forEach(snakeInfo -> snakes.add(new Snake(
                        (String)snakeInfo[INDEX_SNAKE_ID],
                        (String)snakeInfo[INDEX_SNAKE_USERNAME],
                        Integer.parseInt(snakeInfo[INDEX_SNAKE_SCORE]),
                        new SnakeNetworkInputComponent(),
                        new SnakePhysicsComponent(),
                        new SnakeNetworkGraphicsComponent(),
                        new Vector2D(Double.parseDouble(snakeInfo[INDEX_SNAKE_POS_X]), Double.parseDouble(snakeInfo[INDEX_SNAKE_POS_Y])),
                        new Vector2D(Double.parseDouble(snakeInfo[INDEX_SNAKE_DIR_X]), Double.parseDouble(snakeInfo[INDEX_SNAKE_DIR_Y])),
                        tailGraphicComponent
                )));
        foodInfo.forEach(oneFoodInfo -> foodList.add(new Food(
                Integer.parseInt(oneFoodInfo[INDEX_SNAKE_FOOD_ID]),
                new Vector2D(Double.parseDouble(oneFoodInfo[INDEX_SNAKE_FOOD_POS_X]), Double.parseDouble(oneFoodInfo[INDEX_SNAKE_FOOD_POS_Y])),
                new FoodGraphicsComponent()
        )));
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;
        world.setWidth((int) worldWidth);
        world.setHeight((int) worldHeight);

        world.getSnakesOnMap().clear();
        world.setMySnakeID(mySnake.getID());
        snakes.forEach(world::addSnake);
        foodList.forEach(food -> world.addFood(food.id, food));
    }

    @Override
    public String getUserID() {
        return mySnake.getID();
    }

    @Override
    public String getDescription() {
        return "InitInputEvent";
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
