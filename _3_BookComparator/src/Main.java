import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        List<Book> lista = new ArrayList<>();
        lista.add(new Book("libro1", 12.5F));
        lista.add(new Book("libro2", 25.6F));
        lista.add(new Book("libro3", 16.5F));
        lista.add(new Book("libro4", 22.5F));
        lista.add(new Book("libro5", 26.35F));
        lista.add(new Book("libro6", 18.5F));
        lista.add(new Book("libro7", 18.5F));

        lista.sort((l1,l2)-> Float.compare(l2.getPrice(), l1.getPrice()));
        lista.forEach(System.out::println);


        //forma de hacerlo según ejemplo:
        Collections.sort(lista, (b1, b2) -> b1.getName().compareTo(b2.getName()));
        System.out.println("List sorted by title (ascending):");
        for (Book b: lista)
            System.out.println(b);

        Collections.sort(lista, (b1, b2) -> Float.compare(b2.getPrice(), b1.getPrice()));
        System.out.println("List sorted by price (descending):");
        for (Book b: lista)
            System.out.println(b);
    }
    }
}