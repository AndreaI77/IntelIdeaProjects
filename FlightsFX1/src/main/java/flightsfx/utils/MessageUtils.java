package flightsfx.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * class thata manage the different messages shown to user
 */
public class MessageUtils {
    /**
     * show an error alert
     * @param header String that will be used as a header
     * @param message String that will be used al message
     */
    public static void showError(String header,String message){
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Error");
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        dialog.showAndWait();

    }

    /**
     * show an alert with information
     * @param message String that will be used as a message
     */
    public static void showMessage(String message){
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Information");
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * an Alert that ask for a confimation
     * @param header String that is used as a title
     * @param message String thata will be shown as a message
     * @return boolean as representation of accept or cancel button
     */
    public static boolean askConfirmation(String header, String message){
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Confirmation");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("Icon2.jpg"));
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.get() == ButtonType.CANCEL) {
            return false;
        }else{
            return true;
        }
    }

}
