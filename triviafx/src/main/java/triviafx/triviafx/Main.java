package triviafx.triviafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class that launches the application
 */
public class Main extends Application {
    /**
     * starts the application
     * @param stage Stage object  where the  container will be displayed
     * @throws IOException Exception in case the fxml file doesn't exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("trivia-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Trivia Game");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main maethod that launches the application
     * @param args String array with argumens to be passed to the main
     */
    public static void main(String[] args) {
        launch();
    }
}