package triviafx.triviafx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Class with different messages
 */
public class MessageUtils {
    /**
     * Information message
     * @param header title of the message
     * @param message text of the message
     */
    public static void showMessage(String header, String message){
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Information");
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * Shows error message dialog
     * @param header Title of the message
     * @param message Message to be shown in the dialog
     */
    public static void showError(String header, String message){
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Error");
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * confirmation message
     * @param header String wuith the title of the message box
     * @param message String with the text of the message
     * @return Boolean wit the response
     */
    public static boolean askConfirmation(String header, String message){
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Confirmation");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        //stage.getIcons().add(new Image("Icon2.jpg"));
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
