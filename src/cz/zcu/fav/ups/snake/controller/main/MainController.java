package cz.zcu.fav.ups.snake.controller.main;

import cz.zcu.fav.ups.snake.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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

    private Game game;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        game = new Game(canvas);
        fpsLabel.textProperty().bind(game.fps.asString("%d"));

        //canvas.setOnKeyReleased(System.out::println);
    }

    public void handleStartBtn(ActionEvent actionEvent) {
        game.start();
        canvas.requestFocus();
    }

    public void handleStopBtn(ActionEvent actionEvent) {
        game.stop();
    }
}
