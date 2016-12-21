package cz.zcu.fav.ups.snake.controller.main;

import cz.zcu.fav.ups.snake.controller.OnCloseHanler;
import cz.zcu.fav.ups.snake.model.LoginModel;
import cz.zcu.fav.ups.snake.model.World;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Hlavní kontroler
 */
public class MainController implements Initializable, OnCloseHanler {

    // region Variables
    // Příznak který určuje, zda-li jsem ve stavu připojování do hry, či nikoliv
    private final BooleanProperty connecting = new SimpleBooleanProperty(false);


    // region FXML
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
    private Button stopBtn;
    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField hostTxtField;
    @FXML
    private NumericField portNumField;
    // endregion

    // Svět, ve kterém se hraje
    private World world;
    // Přihlašovací model
    private LoginModel loginModel;
    // endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        loginModel = new LoginModel();
        Bindings.bindBidirectional(nameTxtField.textProperty(), loginModel.usernameProperty());
        Bindings.bindBidirectional(hostTxtField.textProperty(), loginModel.hostProperty());
        Bindings.bindBidirectional(portNumField.numberProperty(), loginModel.portProperty());

        connecting.addListener((observable, oldValue, newValue) -> canvas.setCursor(newValue ? Cursor.WAIT : Cursor.DEFAULT));

        world = new World(canvas);
        world.setLostConnectionListener(lostConnectionListener);
        startBtn.disableProperty().bind(Bindings.or(loginModel.validProperty().not(), Bindings.or(world.playing, connecting)));
        stopBtn.disableProperty().bind(world.playing.not());
    }

    // region Button handlers
    /**
     * Akce, která se spustí při stisknutí tlačítka "startBtn"
     *
     * @param actionEvent {@link ActionEvent}
     */
    public void handleStartBtn(ActionEvent actionEvent) {
        if (connecting.get()) {
            return;
        }

        connecting.setValue(true);
        world.connect(loginModel, connectedListener);
    }

    /**
     * Akce, která se spustí při stisknutí tlačítka "stopBtn"
     *
     * @param actionEvent {@link ActionEvent}
     */
    public void handleStopBtn(ActionEvent actionEvent) {
        world.stop();
    }

    // endregion

    // Listener reagující na výsledek připojení klienta do hry
    private final World.ConnectedListener connectedListener = new World.ConnectedListener() {

        @Override
        public void onConnected() {
            world.start();
            canvas.requestFocus();
            connecting.setValue(false);
        }

        @Override
        public void onConnectionFailed() {
            connecting.setValue(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba.");
            alert.setHeaderText("Chyba připojení.");
            alert.setContentText("Nepodařilo se navázat spojení se serverem.");
            alert.showAndWait();
        }
    };

    private final World.LostConnectionListener lostConnectionListener = new World.LostConnectionListener() {
        @Override
        public void onConnectionLost() {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chyba.");
                alert.setHeaderText("Chyba připojení.");
                alert.setContentText("Spojení bylo ztraceno. Zkuste to později");
                alert.showAndWait();
            });
        }
    };

    @Override
    public void onClose() {
        world.stop();
    }

    public void handleSingleplayerBtn(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba.");
        alert.setHeaderText("Oznámení.");
        alert.setContentText("Tato funkce ještě není implementována");
        alert.showAndWait();
    }
}
