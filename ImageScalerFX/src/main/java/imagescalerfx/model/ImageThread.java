package imagescalerfx.model;
import imagescalerfx.imagescalerfx.Controller;
import imagescalerfx.utils.IOUtils;
import imagescalerfx.utils.MessageUtils;
import javafx.application.Platform;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static imagescalerfx.imagescalerfx.Controller.getImageList;

/**
 * Class that define the ImageThread object
 */
public class ImageThread implements Runnable{
    private ImageData img;
    private long start, end, duration;

    /**
     * constructor of the class
     * @param img ImageData object
     */
    public ImageThread(ImageData img) {
        this.img = img;
    }

    /**
     * implemented method, delete directory if exists and creates new one,
     * resize image, save it in the created directory and add the data in the list in the controller
     */
    @Override
    public void run() {
        // get the starting time
        start = System.currentTimeMillis();
        //get the directory path
        Path dirPath = Path.of(img.getPath().toString().split("\\.")[0]);
        //delete directory if exists
        if(Files.exists(dirPath)){
            try{
                IOUtils.deleteDirectory(dirPath);
            }catch(IOException e){
                MessageUtils.showError("File error", "Error deleting the directory!");
            }
        }
        //creates directory and call resize
        try {
            Files.createDirectory(dirPath);
           // System.out.println(dirPath);
            for( int i = 1; i < 10; i++){
                IOUtils.resize(img.getPath().toString(),
                        dirPath.toString()+File.separatorChar+ i * 10 +"_"+img.getFileName(),
                        ((double) i /10));
            }
            //add data to teh list in main controller
            Platform.runLater(() ->getImageList().add(img));
            end = System.currentTimeMillis();
            //calculate duration
            duration = end - start;
            //add data to map to can use it in the cahrt
            Controller.getThreadDuration().put(img.getFileName(),duration);
            //set duration in the object
            img.setDuration(duration);
            //System.out.println(duration);

        } catch (IOException e) {
            MessageUtils.showError("File error", "Error creating  directory. ");
        }

    }
}
