package imagescalerfx.utils;

import imagescalerfx.model.ImageData;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * Class that handles file operations
 */
public class IOUtils
{
    /**
     * Resize an image to the indicated measures
     * @param inputImagePath String with the path of the image to scale
     * @param outputImagePath String width path to save scaled image
     * @param scaledWidth width of the nes image
     * @param scaledHeight height of the scaled image
     * @throws IOException exception thrown in case some of the indicated paths doesn't exist
     */
    private static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    /**
     * Calculate the new width and height of an image
     * @param inputImagePath path of the original image
     * @param outputImagePath path of the new image
     * @param percent percentage to scale the image
     * @throws IOException exception to be thrown in case some of the paths is not correct
     */
    public static void resize(String inputImagePath,
                              String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    /**
     * Deletes all the subdirectories in the  specific directory and the directory itself
     * @param path path of the directory
     * @throws IOException exception to be thrown if the path is not correct
     */
    public static void deleteDirectory(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        Files.delete(path);
    }

    /**
     * return an array of files located in the specified directory
     * @param dir path to the directory
     * @return array of files
     */
     public static File[] getFiles(String dir){
         File location = new File(dir);
         return location.listFiles();
     }
}
