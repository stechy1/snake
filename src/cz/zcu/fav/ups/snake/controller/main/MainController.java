package cz.zcu.fav.ups.snake.controller.main;

import cz.zcu.fav.ups.snake.model.LoginModel;
import cz.zcu.fav.ups.snake.model.World;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;

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
    @FXML
    private Button startBtn;
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField hostTxtField;
    @FXML
    private NumericField portNumField;
    @FXML
    private TextField debugger;
    @FXML
    private Button debugBtn;

//    private Game game;
    private World world;
    private LoginModel loginModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        loginModel = new LoginModel();
        Bindings.bindBidirectional(nameTxtField.textProperty(), loginModel.usernameProperty());
        Bindings.bindBidirectional(hostTxtField.textProperty(), loginModel.hostProperty());
        Bindings.bindBidirectional(portNumField.textProperty(), loginModel.portProperty(), new NumberStringConverter());

        startBtn.disableProperty().bind(loginModel.validProperty().not());

        BooleanProperty disabled = new SimpleBooleanProperty();
        debugger.textProperty().addListener((observable, oldValue, newValue) -> disabled.setValue(newValue.isEmpty()));
        debugBtn.disableProperty().bindBidirectional(disabled);

        world = new World(canvas);
    }

    /**
     * Akce, která se spustí při stisknutí tlačítka "startBtn"
     *
     * @param actionEvent {@link ActionEvent}
     */
    public void handleStartBtn(ActionEvent actionEvent) {
        world.connect(loginModel);
        canvas.requestFocus();
    }

    /**
     * Akce, která se spustí při stisknutí tlačítka "stopBtn"
     *
     * @param actionEvent {@link ActionEvent}
     */
    public void handleStopBtn(ActionEvent actionEvent) {
        world.stop();
    }

    /**
     * Akce, která se spustí při stisknutí tlačítka "stepBtn"
     *
     * @param actionEvent {@link ActionEvent}
     */
    public void handleStepBtn(ActionEvent actionEvent) {
        world.step(System.nanoTime());
    }

    /**
     * Akce, která se spustí při stisknutí tlačítka "noLoop"
     *
     * @param actionEvent {@link ActionEvent}
     */
    public void handleNoLoopBtn(ActionEvent actionEvent) {
        world.noLoop();
    }

    public void handleDebugBtn(ActionEvent actionEvent) {
        world.debug(debugger.getText());
    }
}
