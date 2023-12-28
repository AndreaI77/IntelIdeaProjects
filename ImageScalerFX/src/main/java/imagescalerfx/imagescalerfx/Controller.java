package imagescalerfx.imagescalerfx;

import imagescalerfx.model.ImageData;
import imagescalerfx.model.ImageThread;
import imagescalerfx.utils.IOUtils;
import imagescalerfx.utils.MessageUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * class that controls the fxml file
 */
public class Controller implements Initializable {
    @FXML
    private Button btnChart;
    @FXML
    private Label lblStatus;
    @FXML
    private ListView <ImageData>lvScaled;
    @FXML
    private ImageView  ivImage;
    @FXML
    private Button btnStart;
    @FXML
    private ListView <ImageData> lvImages;
    private static ObservableList<ImageData> imageList= FXCollections.observableArrayList();
    private static ObservableList<ImageData> scaledList  = FXCollections.observableArrayList();
    private static  Map<String, Long> threadDuration = new TreeMap<>();
    private ScheduledService<Boolean> schedServ;
    private ThreadPoolExecutor executor;

    /**
     * method to get the map to use in the chart
     * @return map
     */
    public static Map<String, Long> getThreadDuration() {
        return threadDuration;
    }

    /**
     * method to get the imageList
     * @return imageList
     */
    public static ObservableList<ImageData> getImageList() {
        return imageList;
    }

    /**
     * nethod that initialize the view
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //imageList = FXCollections.observableArrayList();
        //scaledList = FXCollections.observableArrayList();
        lvImages.setItems(imageList);
        lvScaled.setItems(scaledList);
    }

    /**
     * method executed by the click on the start button
     * @param actionEvent click on the start button
     */
    public void scale(ActionEvent actionEvent) {
        imageList.clear();
        scaledList.clear();
        ivImage.setImage(null);
        btnStart.setDisable(true);
        btnChart.setDisable(true);

        // opens a dialog with the explorer to choose a directory
        DirectoryChooser chooser = new DirectoryChooser();
        Stage stage = new Stage();
        chooser.setTitle("Open Resource directory");
        File file = chooser.showDialog(stage);
        // if pressed on cancel button, shows message and enable the buttons
        if(file == null){
            MessageUtils.showError("No directory", "Choose a source directory");
            btnStart.setDisable(false);
            btnChart.setDisable(false);
        }else {
            String path = file.getPath();
            System.out.println(path);
            schedServ = new ScheduledService<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {

                            Platform.runLater(() -> {
                                lblStatus.setText(executor.getCompletedTaskCount() + " of " + executor.getTaskCount() + " tasks finished.");
                            });
                            return executor.isTerminated();
                        }
                    };
                }
            };
            //set period of the service
            schedServ.setPeriod(Duration.seconds(1));
            schedServ.setOnSucceeded(e -> {
                if (schedServ.getValue()) {
                    // Executor finished
                    schedServ.cancel(); // Cancel service (stop it).
                    //enables buttons and shows info message
                    btnStart.setDisable(false);
                    btnChart.setDisable(false);
                    MessageUtils.showMessage("Tasks finished", " All the images have been scaled.");
                }
            });

            executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

            File[] contents = IOUtils.getFiles(path);
            for (File f : contents) {

                if (!f.isDirectory()) {
                    String [] names = f.getName().split("\\.");
                    //checks the file type  !! change to f.getName().endsWith("jpg")
                    if( names[1].equals("jpg") || names[1].equals("jpeg") || names[1].equals("png")) {
                        ImageData img = new ImageData(f.getName(), Path.of(f.getPath()));
                        ImageThread it = new ImageThread(img);
                        executor.execute(it);
                    }
                }
            }
            executor.shutdown();
            schedServ.restart();
        }
    }

    /**
     * method executed by the click on the listView
     * @param mouseEvent mouseClick
     */
    public void lvImageClick(MouseEvent mouseEvent) {
        //if there are is data in the listview, get selected item
        if(!imageList.isEmpty()) {
            scaledList.clear();
            ivImage.setImage(null);
            ImageData image = lvImages.getSelectionModel().getSelectedItem();
            //change to replace() ?
            String pth = image.getPath().toString().split("\\.")[0];;
            // get the files form the subdirectory and add them to the scaled list
            File[] contents = IOUtils.getFiles(pth);

            for (File f : contents) {
                //System.out.println(f.getName()+":  "+ f.getPath());
                ImageData img = new ImageData(f.getName(), Path.of(f.getPath()));
                scaledList.add(img);
            }
        }else{
            // shows message if the list is empty
            MessageUtils.showError("Error","No images to show. Press Start button to load images");
        }
    }

    /**
     * method executed by the click on the listView with scaled images
     * @param mouseEvent mouseClick
     */
    public void lvScaledClick(MouseEvent mouseEvent) {
        // if list is empty, showes a message, otherwise ahows the image in the imageView
        if(scaledList.isEmpty()){
            MessageUtils.showError("No images! ","To see scaled images, choose an image in the left listview.");
        }else{
            ImageData image = lvScaled.getSelectionModel().getSelectedItem();
            Image img = new Image("file:"+image.getPath().toString());
            ivImage.setImage(img);
        }
    }

    /**
     * opens a modal window with the chart
     * @param actionEvent click on the chart button
     * @throws IOException exception thrown when fxml file is not found
     */
    public void openChart(ActionEvent actionEvent) throws IOException {
        // if the list is empty, showes message, otherwise opens chart window
        if(imageList.isEmpty()){
            MessageUtils.showMessage("No data","No data to show. Press Start button to load images");
        }else {

            Parent view = FXMLLoader.load(Objects.requireNonNull(ImageScalerFX.class.getResource("Chart.fxml")));
            Scene viewScene = new Scene(view);
            Stage stage =
                    (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("ImageScalerFX");
            secondaryStage.setScene(viewScene);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(stage);
            //secondaryStage.setOnCloseRequest(e -> // Do whatever);
            secondaryStage.show();
        }
    }
}