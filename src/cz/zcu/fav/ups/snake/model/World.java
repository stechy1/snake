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
 *
 */
public final class World {

    public static final float SCALE = 0.2F;

    private final MainLoop loop;
    public final Canvas canvas;
    private boolean noLoop = false;
    
    private final List<BaseObject> objects = new ArrayList<>();
    private final List<BaseObject> foodList = new ArrayList<>();

    private GraphicsComponent foodGraphicsComponent = new FoodGraphicsComponent();

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

    public void start() {
        loop.start();
    }

    public void stop() {
        loop.stop();
    }

    public void step(long time) {
        loop.handle(time);
    }

    public void noLoop() {
        noLoop = true;
    }

    private class MainLoop extends AnimationTimer {

        private static final double MS_PER_SECOND = 10000000.0;

        // lag
        private long lag = 0;
        //předchozí čas
        private long lastTime = 0;

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
//            final double divide = 1;
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
