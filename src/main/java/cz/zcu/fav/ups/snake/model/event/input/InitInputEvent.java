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

/**
 * Třída představující inicializační event, který inicializuje celou mapu
 */
public class InitInputEvent implements InputEvent {

    private static final GraphicsComponent tailGraphicComponent = new TailCircleGraphicsComponent();

    private final Snake mySnake;
    private final List<Snake> snakes = new ArrayList<>();
    private final List<Food> foodList = new ArrayList<>();
    private final double worldWidth;
    private final double worldHeight;

    public InitInputEvent(SnakeInfo mySnakeInfo, double[] worldSizeInfo, List<SnakeInfo> othersSnakeInfo, List<FoodInfo> foodInfo) {
        System.out.println("Init event");
        this.mySnake = new Snake(
                mySnakeInfo.id,
                mySnakeInfo.score,
                new SnakeMouseInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeGraphicsComponent(),
                new Vector2D(mySnakeInfo.pos),
                new Vector2D(mySnakeInfo.dir),
                tailGraphicComponent
        );
        snakes.add(mySnake);
        worldWidth = worldSizeInfo[0];
        worldHeight = worldSizeInfo[1];
        othersSnakeInfo
                .stream()
                .filter(values -> !Objects.equals(values.id, mySnakeInfo.id))
                .forEach(snakeInfo -> snakes.add(new Snake(
                snakeInfo.id,
                snakeInfo.score,
                new SnakeNetworkInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeNetworkGraphicsComponent(),
                new Vector2D(snakeInfo.pos),
                new Vector2D(snakeInfo.dir),
                tailGraphicComponent
        )));
        foodInfo.forEach(oneFoodInfo -> foodList.add(new Food(
                oneFoodInfo.id,
                new Vector2D(oneFoodInfo.pos),
                new FoodGraphicsComponent()
        )));
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;
        world.setWidth((int)worldWidth);
        world.setHeight((int)worldHeight);

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
