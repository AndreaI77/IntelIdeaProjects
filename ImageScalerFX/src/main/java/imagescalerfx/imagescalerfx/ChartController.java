package imagescalerfx.imagescalerfx;

import imagescalerfx.model.ImageData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * class that controlls the chart view
 */

public class ChartController implements Initializable {
    @FXML
    private BarChart <String, Number> chart;
    @FXML
    private Button btnReturn;

    /**
     * method that close the stage
     * @param actionEvent click on the button Return
     * @throws IOException exception thrown in case the stage is not accesible
     */
    public void goBack(ActionEvent actionEvent) throws IOException {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * method called when the window opens.
     * Gets data from the main controller and load it in to the chart
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(ImageScalerFX.class.getResource("main.fxml"));
        Controller controller = (Controller) loader.getController();

        chart.setTitle("Thread performance");
        XYChart.Series <String, Number> data = new XYChart.Series<>();
        data.setName("Miliseconds");

        // working with map ************************************
        /*Map<String, Long>  lista= Controller.getThreadDuration();
        for(String key: lista.keySet()){
            data.getData().add(new XYChart.Data<>(key, lista.get(key)));
        }*/

        // working with duration property in the imageData object *************************
        List<ImageData> lista= Controller.getImageList();
        String[] img = new String[lista.size()];
        Number[] marks = new Number [lista.size()];

        for(int i = 0; i < lista.size(); i++){
            data.getData().add(new XYChart.Data<>(lista.get(i).getFileName(), lista.get(i).getDuration()));
        }
        //************************************
        chart.getData().add(data);
    }
}
