package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.*;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;
import cz.zcu.fav.ups.snake.model.snake.tail.TailCircleGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.TailGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.TailRainbowGraphicsComponent;

import java.util.*;

import static cz.zcu.fav.ups.snake.model.World.SCALE;

/**
 *
 */
public class Snake extends BaseObject {

    // region Constants
    // Výchozí délka ocasu hada
    private static final int DEFAULT_SIZE = 20;
    // Výchozí multiplikátor rychlosti hada
    private static final float DEFAULT_VELOCITY_MULTIPLIER = 0.7F;

    // Výchozí velikost jednoho kusu těla hada
    public static final int SIZE = 15;
    // Pomocná proměnná
    public static final float SCALED_SIZE = SIZE / SCALE;
    // endregion

    // region Variables
    // Kolekce kousků těla hada
    final LinkedList<Tail> tailList = new LinkedList<>();

    // Grafická komponenta starající se o vykreslení těla hada
    final GraphicsComponent tailGraphicsComponent = new TailCircleGraphicsComponent();

    // Počet kusů ocasu
    private int total;
    // endregion

    // region Constructors
    /**
     * Vytvoří nového hada
     *
     * @param inputComponent    {@link InputComponent} Komponenta starající se o aktualizaci směru objektu
     * @param physicsComponent  {@link PhysicsComponent} Komponenta starající se o logiku objektu
     * @param graphicsComponent {@link GraphicsComponent} Komponenta starající se o vykreslení objektu na plátno
     */
    public Snake(InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent) {
        super(inputComponent, physicsComponent, graphicsComponent);

        dir.set(Vector2D.RIGHT);
        vel.mul(DEFAULT_VELOCITY_MULTIPLIER);

        total = DEFAULT_SIZE;
    }
    // endregion

    @Override
    public void init(World world) {
        super.init(world);

        for (int i = total; i >= 1; i--) {
//            Vector2D tailPos = Vector2D.sub(pos, dir.x * (i + 1) * SCALED_SIZE, dir.y * (i + 1) * SCALED_SIZE);
            tailList.add(new Tail(pos, tailGraphicsComponent));
        }
    }

    @Override
    public String toString() {
        return String.format("Snake{X: %s, Y: %s}", pos.x, pos.y);
    }
}
