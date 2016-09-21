package cz.zcu.fav.ups.snake.model;

import cz.zcu.fav.ups.snake.utils.SnakeUtils;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Třída představující samotnou hru
 */
public class Game {

    // Hlavní herní smyčka
    private final AnimationTimer mainLoop;
    // Kolekce, obsahující všechny herní objekty
    private final List<BaseObject> gameObjects;
    // Platno, na které se kreslí celá hra
    private final Canvas canvas;

    private double zoom = 1;

    // Můj had
    private Snake snake;
    private final Vector2D oldPos = Vector2D.ZERO;

    //počítadlo fps
    public final IntegerProperty fps = new SimpleIntegerProperty(0);

    /**
     * Vytvoří nový objekt hry
     */
    public Game(Canvas canvas) {
        this.canvas = canvas;
        //this.canvas.setOnKeyPressed(this::handleKey);
        this.canvas.setOnMouseMoved(this::handleMove);

        mainLoop = new MainLoop();
        gameObjects = new ArrayList<>();

        init();
    }

    private void handleMove(MouseEvent mouseEvent) {
        snake.mouseX.setValue(mouseEvent.getX());
        snake.mouseY.setValue(mouseEvent.getY());
    }

    private void init() {
        snake = new Snake();

        snake.widthProperty.bind(canvas.widthProperty());
        snake.heightProperty.bind(canvas.heightProperty());
        //snake.mouseX.b
    }

    private void handleKey(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case W:
                snake.updateDirection(Direction.UP);
                break;
            case S:
                snake.updateDirection(Direction.DOWN);
                break;
            case A:
                snake.updateDirection(Direction.LEFT);
                break;
            case D:
                snake.updateDirection(Direction.RIGHT);
                break;
        }
    }

    /**
     * Aktualizační smyčka
     *
     * @param now Aktuální čas
     */
    private void update(long now) {
        GraphicsContext graphic = canvas.getGraphicsContext2D();
        graphic.save();
        graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphic.setStroke(Color.RED);
        graphic.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphic.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);

//        double newZoom = 64 / snake.getSize();
//        zoom = SnakeUtils.lerp(zoom, newZoom, 0.1);
//        graphic.scale(zoom, zoom);
//        double deltaX = snake.pos.x - oldPos.x;
//        double deltaY = snake.pos.y - oldPos.y;
//        System.out.println("DeltaX: " + deltaX + ", deltaY: " + deltaY);

        graphic.translate(-snake.pos.x, -snake.pos.y);

        graphic.setFill(Color.BLACK);


        snake.update(now);
        snake.draw(graphic);

//        gameObjects.forEach(baseObject -> {
//            baseObject.update(now);
//            baseObject.draw(graphic);
//        });

        oldPos.set(snake.pos);

        graphic.restore();
    }

    /**
     * Spustí herní smyčku
     */
    public void start() {
        mainLoop.start();
    }

    /**
     * Zastaví herní smyčku
     */
    public void stop() {
        mainLoop.stop();
    }

    /**
     * Třída představující hlavní herní smyčku
     */
    private class MainLoop extends AnimationTimer {

        //1 vteřina v nanosekundách
        private static final long ONE_SECOND = 1000000000;

        //aktuální čas
        private long currentTime = 0;
        //předchozí čas
        private long lastTime = 0;
        //časový rozdíl mezi posledním a aktuálním snímkem
        private double delta = 0;

        private int counter = 0;

        @Override
        public void handle(long now) {
            currentTime = now;
            int f = fps.get();
            fps.setValue(f + 1);
            delta += currentTime - lastTime;

            counter++;

            if (counter == 5) {
                update(currentTime);
                counter = 0;
            }


            if (delta > ONE_SECOND) {
                //tFPS.setText("FPS : "+fps);
                delta -= ONE_SECOND;
                fps.setValue(0);
            }

            lastTime = currentTime;
        }
    }
}