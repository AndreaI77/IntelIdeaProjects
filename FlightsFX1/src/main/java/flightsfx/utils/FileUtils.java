package flightsfx.utils;

import flightsfx.model.Flight;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class that manage the operations with the file.
 */
public class FileUtils {
    /**
     * method that that reads a file and create different objects of the class Flight
     * using its constructor with all atributes
     * @return returns a list of Flight objects
     */
    public static List<Flight> loadFlights(){
        DateTimeFormatter tm = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<Flight> lista = new ArrayList<>();
        try{
           lista = Files.lines(Paths.get("flights.txt")).map(ln ->
                   new Flight(ln.split(";")[0],ln.split(";")[1], LocalDateTime.parse(ln.split(";")[2],dt),
                           LocalTime.parse(ln.split(";")[3],tm))).collect(Collectors.toList());
       }catch(Exception e){
           e.printStackTrace();

       }
        return lista;
    }

    /**
     * a methos that writes the object into a file.
     * If the file doesn't exist, it creates it.
     * It replaces the content each time
     * @param lista list of Flight objects
     */
    public static void saveFlights(List<Flight>lista){
        try(PrintWriter writer = new PrintWriter("flights.txt")){
            lista.forEach(f->writer.println(f.toString()));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
