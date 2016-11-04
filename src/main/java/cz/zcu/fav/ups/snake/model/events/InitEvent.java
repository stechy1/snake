package cz.zcu.fav.ups.snake.model.events;

import cz.zcu.fav.ups.snake.model.GraphicsComponent;
import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.Vector2D;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.food.Food;
import cz.zcu.fav.ups.snake.model.food.FoodGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.*;
import cz.zcu.fav.ups.snake.model.snake.tail.TailCircleGraphicsComponent;

import java.util.LinkedList;
import java.util.List;

/**
 * Třída představující inicializační event, který inicializuje celou mapu
 */
public class InitEvent implements GameEvent {

    private static final GraphicsComponent tailGraphicComponent = new TailCircleGraphicsComponent();

    private final List<Snake> snakes = new LinkedList<>();
    private final List<Food> foodList = new LinkedList<>();
    private final double worldWidth;
    private final double worldHeight;

    public InitEvent(double[] mySnakeInfo, double[] worldSizeInfo, List<double[]> othersSnakeInfo, List<double[]> foodInfo) {
        System.out.println("Init event");
        Snake mySnake = new Snake(
                (int) mySnakeInfo[0],
                (int) mySnakeInfo[5],
                new SnakeMouseInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeGraphicsComponent(),
                new Vector2D(mySnakeInfo[1], mySnakeInfo[2]),
                new Vector2D(mySnakeInfo[3], mySnakeInfo[4]),
                tailGraphicComponent
        );
        snakes.add(mySnake);
        worldWidth = worldSizeInfo[0];
        worldHeight = worldSizeInfo[1];
        othersSnakeInfo
                .stream()
                .filter(doubles -> (int)doubles[0] != mySnakeInfo[0])
                .forEach(snakeInfo -> snakes.add(new Snake(
                (int)snakeInfo[0],
                (int)snakeInfo[5],
                new SnakeNetworkInputComponent(),
                new SnakePhysicsComponent(), 
                new SnakeGraphicsComponent(),
                new Vector2D(snakeInfo[1], snakeInfo[2]),
                new Vector2D(snakeInfo[3], snakeInfo[4]),
                tailGraphicComponent
        )));
        foodInfo.forEach(oneFoodInfo -> foodList.add(new Food(
                (int)oneFoodInfo[0],
                new Vector2D(oneFoodInfo[1], oneFoodInfo[2]),
                new FoodGraphicsComponent()
        )));
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;
        world.setWidth((int)worldWidth);
        world.setHeight((int)worldHeight);

        snakes.forEach(snake -> world.addSnake(snake.getSnakeID(), snake));
        foodList.forEach(food -> world.addFood(food.id, food));
    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
