package cz.zcu.fav.ups.snake.model;

import cz.zcu.fav.ups.snake.model.food.Food;
import cz.zcu.fav.ups.snake.model.food.FoodGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.*;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Třída představující herní svět
 */
public final class World {

    // region Constants
    // Globální proměnná představující měřítko, podle kterého se všem objektům upravují velikosti
    public static final float SCALE = 0.2F;
    // endregion

    // region Variables
    // Herní smyčka
    private final MainLoop loop;
    // Plátno, na které se kreslí
    public final Canvas canvas;
    // Příznak určující, zda-li se bude herní smyčka opakovat do nekonečna, nebo pouze jednou
    private boolean noLoop = false;

    // Kolekce všech objektů, které se můžou hýbat
    private final List<BaseObject> objects = new ArrayList<>();
    // Kolekce všech jídel - statické objekty
    private final List<BaseObject> foodList = new ArrayList<>();

    // Grafická komponenta starající se o vykreslení jídla
    private GraphicsComponent foodGraphicsComponent = new FoodGraphicsComponent();
    // endregion

    // region Constructors
    /**
     * Vytvoří novou instanci světa
     *
     * @param canvas Plátno, na které se kreslí
     */
    public World(Canvas canvas) {
        this.canvas = canvas;
        loop = new MainLoop();

        Snake snake = new Snake(
                new SnakeMouseInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeGraphicsComponent());

        snake.init(this);
        objects.add(snake);

        loadFood();
    }
    // endregion

    // region Private methods
    /**
     * Pomocná proměnná pro vložení jídla do mapy
     */
    private void loadFood() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        for (int i = 0; i < 100; i++) {
            foodList.add(new Food(
                    Vector2D.RANDOM(
                            0, 0,
                            w / SCALE, h / SCALE),
                    foodGraphicsComponent));
        }
    }
    // endregion

    // region Public methods
    /**
     * Spustí herní smyčku
     */
    public void start() {
        loop.start();
    }

    /**
     * Zastaví herní smysku
     */
    public void stop() {
        loop.stop();
    }

    /**
     * Zavolá jeden krok v herní smyčce
     *
     * @param time Rozdíl mezi předchozím a aktuálním snímkem
     */
    public void step(long time) {
        loop.handle(time);
    }

    /**
     * Nastaví příznak aby se herní smyčka spustila pouze jednou
     */
    public void noLoop() {
        noLoop = true;
    }
    // endregion

    /**
     * Třída představující herní smyčku světa
     */
    private class MainLoop extends AnimationTimer {

        // region Constants
        private static final double MS_PER_SECOND = 10000000.0;
        // endregion

        // region Variables
        // lag
        private long lag = 0;
        //předchozí čas
        private long lastTime = 0;
        // endregion

        @Override
        public void handle(long now) {
            int inLoopTime = 0;
            final long elapsed = now - lastTime;
            lastTime = now;
            lag += elapsed;
            objects.forEach(object -> object.inputComponent.handleInput(object));

            while (lag >= MS_PER_SECOND) {
                objects.forEach(object -> object.physicsComponent.handlePhysics(object, lag));
                lag -= MS_PER_SECOND;
                inLoopTime++;
            }

            System.out.printf("In loop time: %d%n", inLoopTime);

            GraphicsContext graphic = canvas.getGraphicsContext2D();
            graphic.save();
            graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphic.setStroke(Color.RED);
            graphic.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

            graphic.scale(SCALE, SCALE);

            graphic.translate((canvas.getWidth() / 2) / SCALE, (canvas.getHeight() / 2) / SCALE);

            final double divide = lag / MS_PER_SECOND;
            objects.forEach(object -> object.graphicsComponent.handleDraw(object, graphic, divide));
            foodList.forEach(object -> object.graphicsComponent.handleDraw(object, graphic, divide));

            graphic.restore();

            if (noLoop) {
                loop.stop();
            }
        }

        @Override
        public void start() {
            lastTime = System.nanoTime();
            super.start();
        }
    }
}
