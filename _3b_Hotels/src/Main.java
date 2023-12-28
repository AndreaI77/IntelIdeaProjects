import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel("Meliá", "Alicante", 4.3f));
        hotels.add(new Hotel("Costa Narejos", "Los Alcázares", 3.8f));
        hotels.add(new Hotel("Estoril", "Estoril", 2.4f));
        hotels.add(new Hotel("Imperial", "Santander", 4.1f));
        hotels.add(new Hotel("Magic Natura", "Benidorm", 5f));
        hotels.add(new Hotel("Gran Sol", "Alicante", 3.6f));

        hotels.stream().sorted((h1,h2)-> Float.compare(h2.getRating(),h1.getRating())).forEach(System.out::println);
        List<Hotel>lista = new ArrayList<>();
        lista = hotels.stream().filter(h -> h.getRating() >3.0F).collect(Collectors.toList());
        for(Hotel h:lista){
            System.out.println(h);
        }
        String s= hotels.stream().filter(h -> h.getLocation() == "Alicante").map(Hotel::getName).collect(Collectors.joining(",", "Hotels en Alicante: ","."));
        System.out.println(s);

        Long i= hotels.stream().filter(h -> h.getRating() == 5.0F).count();
        System.out.println("Hoteles con rating 5: "+i);
    }
}