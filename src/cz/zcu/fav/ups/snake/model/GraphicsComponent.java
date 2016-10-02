package cz.zcu.fav.ups.snake.model;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public interface GraphicsComponent extends IComponent {

    /**
     *   @param object
     * @param graphicsContext
     * @param divide
     */
    void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide);


}
