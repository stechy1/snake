package cz.zcu.fav.ups.snake.model.snake;

import cz.zcu.fav.ups.snake.model.BaseObject;
import cz.zcu.fav.ups.snake.model.World;
import cz.zcu.fav.ups.snake.model.InputComponent;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseEvent;

/**
 *
 */
public class SnakeMouseInputComponent implements InputComponent {

    private final IntegerProperty widthProperty = new SimpleIntegerProperty();
    private final IntegerProperty heightProperty = new SimpleIntegerProperty();
    private final DoubleProperty mouseX = new SimpleDoubleProperty(0);
    private final DoubleProperty mouseY = new SimpleDoubleProperty(0);

    @Override
    public void init(World world) {

        widthProperty.bind(world.canvas.widthProperty());
        heightProperty.bind(world.canvas.heightProperty());

        world.canvas.setOnMouseMoved(this::handleMove);
    }

    private void handleMove(MouseEvent mouseEvent) {
        mouseX.setValue(mouseEvent.getX());
        mouseY.setValue(mouseEvent.getY());
    }

    @Override
    public void handleInput(BaseObject object) {
        Snake snake = (Snake) object;

        snake.dir.x = mouseX.get() - widthProperty.get() / 2;
        snake.dir.y = mouseY.get() - heightProperty.get() / 2;
    }

}
