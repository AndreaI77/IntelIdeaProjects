import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static final String MOVIEFILE = "movies.dat";
    public static List<Movie> loadMovies(){
        List<Movie> lista = new ArrayList();
        try(ObjectInputStream os = new ObjectInputStream( new FileInputStream( MOVIEFILE))){
            lista = (List<Movie>) os.readObject();
        }catch ( FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            e.printStackTrace();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return lista;
    }
    public static void printMovies(List<Movie>lista){
        if( lista.size() != 0){
            System.out.println("Your movie list:");
            for(Movie m:lista){
                System.out.println(m);
            }
        }else{
            System.out.println("Your movie list is empty");
        }
    }
    public static List<Movie> addMovies(List<Movie> lista){
        Scanner sc = new Scanner(System.in);
        String title ="", genre = "";
        do{
            System.out.println("Insert movie title:");
            title = sc.nextLine();
            if(!title.isEmpty()){
                do {
                    System.out.println("Insert movie genre:");
                    genre = sc.nextLine();
                }while(genre.isEmpty());
                lista.add(new Movie(title, genre));
            }
        }while(! title.isEmpty());
        return lista;
    }
    public static void saveMovies(List<Movie> lista){
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(MOVIEFILE))){
            os.writeObject(lista);
            System.out.println("Your movie list is saved");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
    public static void main(String[] args) {
        if( ! Files.exists(Paths.get(MOVIEFILE)) ){
           File file= new File(MOVIEFILE);
           try{
              if(file.createNewFile()){
                  System.out.println("Se ha creado el fichero");
              }
           }catch(IOException e){
               System.out.println(e.getMessage());
           }
        }
        List<Movie> movies=  loadMovies();
        printMovies(movies);
        saveMovies(addMovies(movies));





    }
}