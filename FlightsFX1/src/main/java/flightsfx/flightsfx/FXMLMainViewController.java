package flightsfx.flightsfx;

import flightsfx.model.Flight;
import flightsfx.utils.FileUtils;
import flightsfx.utils.MessageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalDouble;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

/**
 * class that controls the main scene of the application
 */
public class FXMLMainViewController implements Initializable {

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnChart;
    @FXML
    private TableView <Flight>tabla;
    @FXML
    private TableColumn<Flight, String> colNumber;
    @FXML
    private TableColumn <Flight, String> colDestination;
    @FXML
    private TableColumn <Flight, String> colDeparture;
    @FXML
    private TableColumn<Flight, String>colDuration;
    @FXML
    private TextField txtflight;
    @FXML
    private TextField txtDestination;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtDeparture;
    @FXML
    private TextField txtDuration;
    @FXML
    private Button btnDelete;
    @FXML
    private ComboBox<String> cbFilter;
    @FXML
    private Button btnFilter;
    @FXML
    private TextField txtSearch;

    private static ObservableList<Flight> flights;
    private static ObservableList<Flight> elements;
    private static final DateTimeFormatter tmFormat = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Method that set the starting values of the scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbFilter.getItems().addAll("Show all flights", "Show flights to currently selected city","Show long flights", "Show next 5 flights", "Show flights duration average");

        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        flights = FXCollections.observableArrayList(FileUtils.loadFlights());
        elements = flights;
        tabla.setItems(elements);
        tabla.setPlaceholder(new Label("No flights to show"));

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    /**
     * method that checks the fields of the form and add new flights to the lists
     * @param actionEvent click event of the button
     */
    public void addFlight(ActionEvent actionEvent) {
        String msg= checkFields();
        if(msg.isEmpty()){

                Flight fl= new Flight(txtflight.getText().trim().toUpperCase(), txtDestination.getText().trim(), LocalDateTime.parse(txtDeparture.getText().trim(), format), LocalTime.parse(txtDuration.getText().trim(), tmFormat));
                flights.add(fl);
                if( flights.size() != elements.size() ){
                   elements.add(fl);
                }
                tabla.refresh();
                //tabla.setItems(elements);

                MessageUtils.showMessage("The flight was added correctly");
                clearForm();
        }else{
                MessageUtils.showError("Error adding flight",msg);
        }
    }

    /**
     * method that clears all the fields of the form
     */
    public void clearForm(){
        txtflight.setText("");
        txtDestination.setText("");
        txtDeparture.setText("");
        txtDuration.setText("");
    }

    /**
     * method that checks if the fields have the expected values
     * @return String message with errors or empty String in case all the fields are correct
     */
    public String checkFields() {
        String message = "";
        if (txtflight.getText().isEmpty() ||
                txtDestination.getText().isEmpty() ||
                txtDeparture.getText().isEmpty() ||
                txtDuration.getText().isEmpty()) {
            message += "Missing fields! Fill up all the fields";
        } else {
            try {
                LocalDateTime dt = LocalDateTime.parse(txtDeparture.getText().trim(), format);
                if (now().isAfter(dt)) {
                    message += "The departure time can't be in the past.\n ";
                }
            } catch (Exception e) {
                message += "The format of the departure is not correct\n";
            }
            try {
                LocalTime tm = LocalTime.parse(txtDuration.getText().trim(), tmFormat);
            } catch (Exception e) {
                message += "The format of the duration is not correct";
            }
        }
        return message;
    }

    /**
     * activate buttons deactivated in the click on the row
     */
    public void activateButtons(){
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnAdd.setDisable(false);
    }

    /**
     * delete selected flight
     * @param actionEvent get the click event of the button and clear te fields of the form
     */
    public void deleteFlight(ActionEvent actionEvent) {
        if(MessageUtils.askConfirmation("Delete flight","Are you sure, you want to delete the flight?")) {
            Flight fl = elements.get(tabla.getSelectionModel().getSelectedIndex());
            flights.remove(fl);
            elements.remove(fl);
            tabla.setItems(elements);
            activateButtons();
            clearForm();
        }
        activateButtons();
        clearForm();
    }

    /**
     * filter the list of flights and show the results in the table
     * @param actionEvent get the click event of the button and clear te fields of the form
     */
    public void filter(ActionEvent actionEvent) {
        activateButtons();
        txtSearch.setText("");
        clearForm();
        int num = cbFilter.getSelectionModel().getSelectedIndex();
        if(num >= 0){
            switch(num) {
                case 0:
                    elements = flights;
                    tabla.setItems(elements);
                    break;
                case 1:
                    int nmr = tabla.getSelectionModel().getSelectedIndex();
                    if (nmr >= 0) {
                        String dest = elements.get(nmr).getDestination().toUpperCase();

                        elements = FXCollections.observableArrayList(flights.stream().filter(fl -> fl.getDestination().toUpperCase().equals(dest)).collect(Collectors.toList()));
                        tabla.setItems(elements);

                    } else {
                        MessageUtils.showError("Filter error", "No rows have been selected in the table.");
                    }
                    break;
                case 2:
                    if (!flights.isEmpty()) {
                        elements = FXCollections.observableArrayList(flights.stream().filter(fl -> LocalTime.parse(fl.getDuration(), DateTimeFormatter.ofPattern("H:mm")).isAfter(LocalTime.parse("3:00", DateTimeFormatter.ofPattern("H:mm")))).collect(Collectors.toList()));
                        tabla.setItems(elements);
                    }
                    break;
                case 3:
                    if (!flights.isEmpty()) {
                        elements = FXCollections.observableArrayList(flights.stream().filter(fl -> LocalDateTime.parse(fl.getDeparture(), format).isAfter(now())).sorted((f1, f2) -> LocalDateTime.parse(f1.getDeparture(), format).compareTo(LocalDateTime.parse(f2.getDeparture(), format))).limit(5).collect(Collectors.toList()));
                        tabla.setItems(elements);
                    }
                    break;
                case 4:
                    if (!flights.isEmpty()) {
                        //LocalTime ltime=LocalTime.ofSecondOfDay((long) flights.stream().mapToLong(fl -> LocalTime.parse(fl.getDuration(),DateTimeFormatter.ofPattern("H:mm"))
                        //  .toSecondOfDay()).average().getAsDouble());

                        OptionalDouble od = flights.stream().mapToLong(fl -> LocalTime.parse(fl.getDuration(), DateTimeFormatter.ofPattern("H:mm")).toNanoOfDay()).average();
                        if (od.isPresent()) {
                            LocalTime loc = LocalTime.ofNanoOfDay((long) od.getAsDouble());
                            elements = FXCollections.observableArrayList((flights.stream().filter(f -> f.getDuration2().isAfter(loc))).collect(Collectors.toList()));
                            tabla.setItems(elements);
                            //MessageUtils.showMessage("Average flight duration: " + loc.format(DateTimeFormatter.ofPattern("HH:mm")));
                        } else {

                            MessageUtils.showMessage("Average flight duration is not available");
                        }
                    }
            }
        }else{
            MessageUtils.showError("Filter error", "Select a filter.");
        }
    }

    /**
     * filter the list of flights looking for te  value int the attributes
     * @param keyEvent get the change event of the text field
     */
    public void search(KeyEvent keyEvent) {
       activateButtons();
        clearForm();
        cbFilter.getSelectionModel().select(-1);

        String text = txtSearch.getText().toUpperCase();
        elements = FXCollections.observableArrayList(flights.stream().filter(f-> (f.getNumber().toUpperCase().contains(text)
                || f.getDestination().toUpperCase().contains(text) || f.getDeparture().contains(text)
                || f.getDuration().contains(text))).collect(Collectors.toList()));
        tabla.setItems(elements);
    }

    /**
     * get the selected flight, fill the form with its attributes and activate the needed buttons
     * @param mouseEvent click event of the mouse
     */
    public void tableClick(MouseEvent mouseEvent) {
        if(tabla.getSelectionModel().getSelectedIndex() >= 0){
            Flight fl= elements.get(tabla.getSelectionModel().getSelectedIndex());
            txtflight.setText(fl.getNumber());
            txtDestination.setText(fl.getDestination());
            txtDeparture.setText(fl.getDeparture());
            txtDuration.setText(fl.getDurationFormat());
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnAdd.setDisable(true);
        }
    }

    /**
     * catches the close event of the stage, ask for confirmation and in case of positive answer, save the list into the file
     * @param stage a stage object that is going to get closed
     */
    public void setOnCloseListener(Stage stage) {
        stage.setOnCloseRequest(e ->
        {
            if(MessageUtils.askConfirmation("Confirm exit","Are you sure, you want to exit the application?")){
                FileUtils.saveFlights(flights);
            }else {
                e.consume();
            }
        });
    }

    /**
     * getter of the Flights list
     * @return list of flights
     */
    public List<Flight> getFlights(){
        return flights;
    }

    /**
     * opens the chart scene
     * @param actionEvent click of the button
     * @throws IOException in case that the file doesn't exist, throws an IOexception
     */
    public void openChart(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(FlightsFX.class.getResource("Chart.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Update the selected  flight in the form
     * @param actionEvent click on the update button
     */
    public void update(ActionEvent actionEvent) {
        String msg = checkFields();
        if(msg.isEmpty()) {
            if (MessageUtils.askConfirmation("Update flight", "Are you sure, you want to update the flight?")) {
                Flight fl = elements.get(tabla.getSelectionModel().getSelectedIndex());

                fl.setNumber(txtflight.getText().trim().toUpperCase());
                fl.setDestination(txtDestination.getText().trim());
                fl.setDeparture(LocalDateTime.parse(txtDeparture.getText().trim(), format));
                fl.setDuration(LocalTime.parse(txtDuration.getText().trim(), tmFormat));
                tabla.refresh();

            }
            activateButtons();
            clearForm();

        } else {
            MessageUtils.showError("Error adding flight",msg);

        }
    }
}