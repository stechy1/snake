package cz.zcu.fav.ups.snake.model;

import javafx.scene.canvas.GraphicsContext;

/**
 * Komponenta starající se o vykreslení objektu na plátno
 */
public interface GraphicsComponent extends IComponent {

    /**
     * Zavolá se, když je potřeba objekt vykreslit
     *
     * @param object {@link BaseObject} Objekt, který se má vykreslit
     * @param graphicsContext {@link GraphicsContext}
     * @param divide Interpolace mezi aktuálním a následujícím snímkem
     */
    void handleDraw(BaseObject object, GraphicsContext graphicsContext, double divide);


}
