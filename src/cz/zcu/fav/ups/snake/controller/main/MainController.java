package cz.zcu.fav.ups.snake.controller.main;

import cz.zcu.fav.ups.snake.model.World;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Hlavní kontroler
 */
public class MainController implements Initializable {

    // Obalovací prvek pro canvas
    @FXML
    private AnchorPane anchorPane;

    // Canvas, na který se kreslí
    @FXML
    private Canvas canvas;

    // Toolbar
    @FXML
    private ToolBar toolbar;
    // TextField zobrazující FPS
    @FXML
    private Label fpsLabel;

//    private Game game;
    private World world;
    private int counter = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        world = new World(canvas);
//        fpsLabel.textProperty().bind(game.fps.asString("%d"));
    }

    public void handleStartBtn(ActionEvent actionEvent) {
        world.start();
        canvas.requestFocus();
    }

    public void handleStopBtn(ActionEvent actionEvent) {
        world.stop();
    }

    public void handleStepBtn(ActionEvent actionEvent) {
        world.step(counter++);
    }
}
