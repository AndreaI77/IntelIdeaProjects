package imagescalerfx.model;

import java.nio.file.Path;

/**
 * Class that defines the model of ImageData
 */
public class ImageData {
    private String fileName;
    private Path path;
    private long duration;

    /**
     * sets the duration of resizind of the image
     * @param duration
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Method tu get the duration of resizing
     * @return the duration of resizing of the image
     */
    public long getDuration() {
        return duration;
    }

    /**
     * method to get the filename
     * @return String filename
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * method to get the path to the image
     * @return Path object
     */
    public Path getPath() {
        return path;
    }

    /**
     * constructor to create new object ImageData
     * @param fileName Name of the file
     * @param path path to the file
     */
    public ImageData(String fileName, Path path) {
        this.fileName = fileName;
        this.path = path;
    }

    /**
     * method to print the object name
     * @return filename
     */
    @Override
    public String toString() {
        return fileName;
    }
}
