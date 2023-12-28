package flightsfx.flightsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the application
 */
public class FlightsFX extends Application {
    /**
     * method that load the stage with the main scene
     * @param stage Stage object used  as the main stage
     * @throws IOException an exception thrown if the file doesn't exists
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlightsFX.class.getResource("FXMLMainView.fxml"));
        Parent root = fxmlLoader. load();
        FXMLMainViewController controller = (FXMLMainViewController) fxmlLoader.getController();
        controller.setOnCloseListener(stage);
        Scene scene = new Scene(root, 600, 500);
        stage.getIcons().add(new Image("Icon2.jpg"));
        stage.setTitle("Flight control");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method that launch the application
     * @param args String array  with arguments
     */
    public static void main(String[] args) {
        launch();
    }
}