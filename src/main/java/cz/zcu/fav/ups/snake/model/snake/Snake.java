package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.*;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.snake.tail.Tail;

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
    private static final float DEFAULT_VELOCITY_MULTIPLIER = 0.05F;

    // Výchozí velikost jednoho kusu těla hada
    public static final int SIZE = 10;
    // Pomocná proměnná
    public static final float SCALED_SIZE = SIZE / SCALE;
    // endregion

    // region Variables
    public final Vector2D pos = new Vector2D(); // Pozice
    public final Vector2D vel = Vector2D.ONES(); // Rychlost
    public final Vector2D dir = Vector2D.ZERO(); // Směr
    // Komponenta starající se o aktualizaci směru objektu
    public final InputComponent inputComponent;
    // Komponenta starající se o logiku objektu
    public final PhysicsComponent physicsComponent;
    // Komponenta starající se o vykreslení objektu na plátno
    public final GraphicsComponent graphicsComponent;
    // Kolekce kousků těla hada
    final LinkedList<Tail> tailList = new LinkedList<>();
    final LinkedList<InputEvent> events = new LinkedList<>();

    // Grafická komponenta starající se o vykreslení těla hada
    final GraphicsComponent tailGraphicsComponent;

    // Jednoznačný identifikátor hada
    private final String id;
    // Jméno hada
    private final String name;
    // Počet kusů ocasu
    private int score;
    // endregion

    // region Constructors

    /**
     * Vytvoří nového hada
     *
     * @param id ID hada
     * @param name Jméno hada
     * @param score Skóre hada
     * @param inputComponent    {@link InputComponent} Komponenta starající se o aktualizaci směru objektu
     * @param physicsComponent  {@link PhysicsComponent} Komponenta starající se o logiku objektu
     * @param graphicsComponent {@link GraphicsComponent} Komponenta starající se o vykreslení objektu na plátno
     * @param pos Vektor pozice hada
     * @param dir Vektor směru hada
     * @param tailGraphicsComponent Grafická komponenta starající se o vykreslení ocasu hada
     */
    public Snake(String id, String name, int score, InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent, Vector2D pos, Vector2D dir, GraphicsComponent tailGraphicsComponent) {
        this.id = id;
        this.name = name;
        this.inputComponent = inputComponent;
        this.physicsComponent = physicsComponent;
        this.graphicsComponent = graphicsComponent;
        this.tailGraphicsComponent = tailGraphicsComponent;
        this.pos.set(pos);
        this.dir.set(dir);
        vel.mul(DEFAULT_VELOCITY_MULTIPLIER);
        this.score = score;
    }
    // endregion

    public void init(World world) {
        inputComponent.init(world);
        physicsComponent.init(world);
        graphicsComponent.init(world);

        for (int i = score; i >= 1; i--) {
            tailList.add(new Tail(pos, tailGraphicsComponent));
        }
    }

    public void addEvent(InputEvent event) {
        events.addLast(event);
    }

    public void incrementScore(int count) {
        score += count;
        for (int i = 0; i < count; i++) {
            tailList.add(new Tail(pos, tailGraphicsComponent));
        }
    }

    @Override
    public String toString() {
        return String.format("Snake{X: %s, Y: %s}", pos.x, pos.y);
    }

    public String getID() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }
}
