package cz.zcu.fav.ups.snake;

import cz.zcu.fav.ups.snake.controller.main.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Vstupní bod aplikace
 */
public class App extends Application {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("cs"));
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setOnCloseRequest(event -> {
            controller.onClose();
            Platform.exit();
            System.exit(0);
        });
        stage.setScene(scene);
        stage.setTitle("Multiplayer snake");
        stage.show();
    }
}
