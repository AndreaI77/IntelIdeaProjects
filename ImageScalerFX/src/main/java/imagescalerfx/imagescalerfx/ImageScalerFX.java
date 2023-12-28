package imagescalerfx.imagescalerfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class thats launch the application
 */
public class ImageScalerFX extends Application {
    /**
     * define the start method from the aplication
     * @param stage stage to be opened in
     * @throws IOException exception to be thrown in case the file doesn't exists
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ImageScalerFX.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("ImageScalerFX");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main method to launch the application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}