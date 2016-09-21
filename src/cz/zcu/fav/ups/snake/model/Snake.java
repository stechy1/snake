package cz.zcu.fav.ups.snake.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Třída představující hada
 */
public class Snake extends BaseObject {

    public static final int SIZE = 10;

    private int total = 6;

    private Direction direction = Direction.RIGHT;

    private final List<Tail> tails;

    public final IntegerProperty widthProperty = new SimpleIntegerProperty();
    public final IntegerProperty heightProperty = new SimpleIntegerProperty();
    public final DoubleProperty mouseX = new SimpleDoubleProperty(0);
    public final DoubleProperty mouseY = new SimpleDoubleProperty(0);

    public Snake() {
        tails = new ArrayList<>();
        pos.set(50, 50);

        tails.addAll(Arrays.asList(
                new Tail(40, 50),
                new Tail(30, 50),
                new Tail(20, 50),
                new Tail(10, 50),
                new Tail(0, 50),
                new Tail(-10, 50)
        ));

        mouseX.addListener((observable, oldValue, newValue) -> dir.x = newValue.doubleValue() - widthProperty.get() / 2);
        mouseY.addListener((observable, oldValue, newValue) -> dir.y = newValue.doubleValue() - heightProperty.get() / 2);
    }

    private boolean isDirectionInverted(Direction direction) {
        switch (direction) {
            case UP:
                if (this.direction == Direction.DOWN)
                    return true;
                dir = Vector2D.UP;
                break;
            case DOWN:
                if (this.direction == Direction.UP)
                    return true;
                dir = Vector2D.DOWN;
                break;
            case LEFT:
                if (this.direction == Direction.RIGHT)
                    return true;
                dir = Vector2D.LEFT;
                break;
            case RIGHT:
                if (this.direction == Direction.LEFT)
                    return true;
                dir = Vector2D.RIGHT;
                break;
        }
        return false;
    }

    public void updateDirection(Direction direction) {
        if (isDirectionInverted(direction))
            return;

        if (this.direction != direction)
            this.direction = direction;

        switch (direction) {
            case UP:
                dir = Vector2D.UP;
                break;
            case DOWN:
                dir = Vector2D.DOWN;
                break;
            case LEFT:
                dir = Vector2D.LEFT;
                break;
            case RIGHT:
                dir = Vector2D.RIGHT;
                break;
        }
    }

    @Override
    public void update(long timestamp) {

        dir.normalize();

        if (total == tails.size()) {
            for (int i = 0; i < tails.size() - 1; i++) {
                tails.set(i, tails.get(i + 1));
            }
        }

        tails.set(total - 1, new Tail(pos));

        Vector2D newPos = dir.copy().mul(vel.copy()).mul(SIZE);
        pos.add(newPos);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(pos.x, pos.y, SIZE, SIZE);

        tails.forEach(tail -> tail.draw(graphicsContext));
    }

    public int getSize() {
        return total + 1;
    }

    private class Tail extends BaseObject {

        public Tail(Vector2D pos) {
            this(pos.x, pos.y);
        }

        public Tail(double x, double y) {
            pos.set(x, y);
        }

        @Override
        public void update(long timestamp) {

        }

        @Override
        public void draw(GraphicsContext graphicsContext) {
            graphicsContext.setFill(Color.CYAN);
            graphicsContext.fillRect(pos.x, pos.y, SIZE, SIZE);
        }
    }
}
