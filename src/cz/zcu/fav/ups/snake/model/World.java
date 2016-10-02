package cz.zcu.fav.ups.snake.model;

import cz.zcu.fav.ups.snake.model.snake.Snake;
import cz.zcu.fav.ups.snake.model.snake.SnakeGraphicsComponent;
import cz.zcu.fav.ups.snake.model.snake.SnakePhysicsComponent;
import cz.zcu.fav.ups.snake.model.snake.SnakeMouseInputComponent;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class World {

    private final MainLoop loop;
    public final Canvas canvas;
    
    private final List<BaseObject> objects = new ArrayList<>();

    public World(Canvas canvas) {
        this.canvas = canvas;
        loop = new MainLoop();

        Snake snake = new Snake(
                new SnakeMouseInputComponent(),
                new SnakePhysicsComponent(),
                new SnakeGraphicsComponent());

        snake.init(this);
        objects.add(snake);
    }

    public void start() {
        loop.start();
    }

    public void stop() {
        loop.stop();
    }

    public void step(int time) {
        loop.handle(time);
    }

    private class MainLoop extends AnimationTimer {

        private static final double MS_PER_SECOND = 1000000.0;

        // lag
        private long lag = 0;
        //předchozí čas
        private long lastTime = 0;

        @Override
        public void handle(long now) {

            int inLoopTime = 0;
            long elapsed = now - lastTime;
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

            graphic.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);

            double divide = lag / MS_PER_SECOND;
            objects.forEach(object -> object.graphicsComponent.handleDraw(object, graphic, divide));

            graphic.restore();
        }

        @Override
        public void start() {
            lastTime = System.nanoTime();
            super.start();
        }
    }
}
