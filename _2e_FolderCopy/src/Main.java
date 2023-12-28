import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try{
            if( Files.exists(Paths.get("folder2"))) {
               File[] src = Paths.get("folder2").toFile().listFiles();
                for(File f : src){
                    Files.delete(f.toPath());
                }
                Files.delete(Paths.get("folder2"));
            }
            Files.createDirectory(Paths.get("folder2"));
            if(Files.exists(Paths.get("folder1"))){
                File[] files = Paths.get("folder1").toFile().listFiles();

                for(File f : files){
                    Files.copy(f.toPath(), Paths.get("folder2"+ File.separatorChar + f.getName()), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}