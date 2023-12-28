import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RegexReader rd = null;
        try {
           rd = new RegexReader(new FileReader("datos.txt"), ".*\\d{2}/\\d{2}/\\d{4}|\\d{2}.*");
            String line = null;
            while((line = rd.readLine()) != null){
                System.out.println(line);
            };
        }catch ( FileNotFoundException e){
           e.printStackTrace();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }finally{
            if( rd != null){
                try{
                    rd.close();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}