package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.*;
import cz.zcu.fav.ups.snake.model.events.GameEvent;
import cz.zcu.fav.ups.snake.model.events.SnakeChangeDirectionEvent;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseEvent;

import java.util.Queue;

/**
 * Třída představující ovládání hada pomocí myši
 */
public class SnakeMouseInputComponent implements InputComponent {

    // region Variables
    // Šířka plátna
    private final IntegerProperty widthProperty = new SimpleIntegerProperty();
    // Výška plátna
    private final IntegerProperty heightProperty = new SimpleIntegerProperty();
    // X-ová souřadnice myši
    private final DoubleProperty mouseX = new SimpleDoubleProperty(0);
    // Y-ová souřadnice myši
    private final DoubleProperty mouseY = new SimpleDoubleProperty(0);
    private Queue<GameEvent> eventQueue;
    // endregion

    @Override
    public void init(World world) {
        widthProperty.bind(world.canvas.widthProperty());
        heightProperty.bind(world.canvas.heightProperty());

        eventQueue = world.outputEventQeue;

        world.canvas.setOnMouseMoved(this::handleMove);
    }

    /**
     * Zavolá se pokažde, když se hýbe s myší
     *
     * @param mouseEvent {@link MouseEvent}
     */
    private void handleMove(MouseEvent mouseEvent) {
        mouseX.setValue(mouseEvent.getX() - widthProperty.get() / 2);
        mouseY.setValue(mouseEvent.getY() - heightProperty.get() / 2);
    }

    @Override
    public void handleInput(GameObject object) {
        Snake snake = (Snake) object;

//        Vector2D mouseVec = new Vector2D(
//                mouseX.get() - widthProperty.get() / 2,
//                mouseY.get() - heightProperty.get() / 2
//        ).normalize();
//
//        double alpha = Math.sin(snake.dir.y);
//        mouseVec.rotateRad(alpha);
//
//        double oldPolar = Math.atan2(snake.dir.y, snake.dir.x);
//        double mouPolar = Math.atan2(mouseVec.y, mouseVec.x);
//
//        double delta = mouPolar - oldPolar;
//
//        int degree = 1;
//        if (delta < 0)
//            degree = -1;
//
//        snake.dir.rotate(degree);
//
//        System.out.printf("Degree: %s%n", degree);

        Vector2D oldDir = snake.dir.copy();

        snake.dir.x = mouseX.get();
        snake.dir.y = mouseY.get();

        snake.dir.normalize();

        if (!oldDir.equals(snake.dir))
            eventQueue.add(new SnakeChangeDirectionEvent(snake.getSnakeID(), snake.dir.copy()));
    }

}
