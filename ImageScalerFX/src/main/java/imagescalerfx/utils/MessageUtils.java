package imagescalerfx.utils;

import javafx.scene.control.Alert;

/**
 * Class that handles the message dialogs shown to the user
 */
public class MessageUtils {
    /**
     * Shows information message dialog
     * @param header The title of the message
     * @param message The message to be shown in the dialog
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
}
