package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.*;
import cz.zcu.fav.ups.snake.model.events.GameEvent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;
import cz.zcu.fav.ups.snake.model.snake.tail.TailCircleGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.TailGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.tail.TailRainbowGraphicsComponent;

import java.util.*;

import static cz.zcu.fav.ups.snake.model.World.SCALE;

/**
 * Třída představující hada na mapě
 */
public class Snake implements GameObject, IUpdatable {

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
    public final Vector2D pos = new Vector2D(); // Pozice
    public final Vector2D vel = Vector2D.ONES; // Rychlost
    public final Vector2D dir = Vector2D.ZERO; // Směr
    // Komponenta starající se o aktualizaci směru objektu
    public final InputComponent inputComponent;
    // Komponenta starající se o logiku objektu
    public final PhysicsComponent physicsComponent;
    // Komponenta starající se o vykreslení objektu na plátno
    public final GraphicsComponent graphicsComponent;
    // Kolekce kousků těla hada
    final LinkedList<Tail> tailList = new LinkedList<>();
    final LinkedList<GameEvent> events = new LinkedList<>();

    // Grafická komponenta starající se o vykreslení těla hada
    final GraphicsComponent tailGraphicsComponent;

    // Jednoznačný identifikátor hada
    private int snakeID;
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
    public Snake(int id, int total, InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent, Vector2D pos, Vector2D dir, GraphicsComponent tailGraphicsComponent) {
        this.snakeID = id;
        this.inputComponent = inputComponent;
        this.physicsComponent = physicsComponent;
        this.graphicsComponent = graphicsComponent;
        this.tailGraphicsComponent = tailGraphicsComponent;
        this.pos.set(pos);
        this.dir.set(dir);
        vel.mul(DEFAULT_VELOCITY_MULTIPLIER);
        this.total = total;
    }
    // endregion

    public void init(World world) {
        inputComponent.init(world);
        physicsComponent.init(world);
        graphicsComponent.init(world);

        for (int i = total; i >= 1; i--) {
            tailList.add(new Tail(pos, tailGraphicsComponent));
        }
    }

    public void addEvent(GameEvent event) {
        events.addLast(event);
    }

    @Override
    public String toString() {
        return String.format("Snake{X: %s, Y: %s}", pos.x, pos.y);
    }

    public int getSnakeID() {
        return snakeID;
    }
}
