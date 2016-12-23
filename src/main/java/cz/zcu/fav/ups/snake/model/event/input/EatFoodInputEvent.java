package cz.zcu.fav.ups.snake.model.event.input;


import cz.zcu.fav.ups.snake.model.IUpdatable;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.snake.Snake;

public class EatFoodInputEvent implements InputEvent {

    public static final int EAT_FOOD_SCORE = 3;

    private final String clientId;
    private final int foodId;

    public EatFoodInputEvent(String[] eatInfo) {
        foodId = Integer.parseInt(eatInfo[0]);
        clientId = eatInfo[1];
    }

    @Override
    public void applyEvent(IUpdatable updatable) {
        World world = (World) updatable;

        world.removeFood(foodId);
        Snake snake = world.getSnakesOnMap().get(clientId);
        if (snake == null) {
            return;
        }

        snake.incrementScore(EAT_FOOD_SCORE);
    }

    @Override
    public String getUserID() {
        return clientId;
    }

    @Override
    public String getDescription() {
        return "EatFoodEvent";
    }

    @Override
    public EventType getType() {
        return EventType.WORLD;
    }
}
