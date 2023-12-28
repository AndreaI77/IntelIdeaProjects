package flightsfx.flightsfx;

import flightsfx.model.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * class that controlls the chart view
 */
public class ChartController implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private PieChart chart;

    /**
     * gets the Flights list from the main view controller and charge the chart data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(FlightsFX.class.getResource("FXMLMainView.fxml"));
        try {
            Parent root = (Parent) loader.load();
        }catch (Exception e){}
        FXMLMainViewController controller = (FXMLMainViewController) loader.getController();
        List<Flight> flights = controller.getFlights();
        chart.getData().clear();
        Map<String,Long> result = flights.stream().collect(Collectors.groupingBy(Flight::getDestination, Collectors.counting()));

        result.forEach((dest,sum)->{
            chart.getData().add( new PieChart.Data(dest,sum));
        });
    }

    /**
     * change the scene in the stage and opesn the main view
     * @param actionEvent event button click
     * @throws IOException exception in case the file doesn't exist
     */
    public void goToMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(FlightsFX.class.getResource("FXMLMainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
