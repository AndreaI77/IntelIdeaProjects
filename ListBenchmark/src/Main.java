import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        List<Double> ar = new ArrayList<Double>();
        List<Double> ln = new LinkedList<Double>();

        Instant start = Instant.now();
// Some operation with ArrayList or LinkedList
        for (int i = 0; i < 100000; i++) {
            ar.add(0, Math.random());
        }
        Instant end = Instant.now();
        Duration dur = Duration.between(start, end);
        System.out.printf("ArrayList: The operation add 100 000 takes: %dms\n", dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < 100000; i++) {
            ln.add(0, Math.random());
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("LinkedList: The operation add 100 000 takes: %dms\n", dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < 50000; i++) {
            ar.remove(0);
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("ArrayList: The operation remove 50 000 takes: %dms\n", dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < 50000; i++) {
            ln.remove(0);
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("LinkedList: The operation remove 50 000 takes: %dms\n", dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < 50000; i++) {
            int pos = (int) (Math.random() * (50000 + i));
            ar.add(pos, Math.random());
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("ArrayList: The operation add random 50 000 takes: %dms\n", dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < 50000; i++) {
            int pos = (int) (Math.random() * (50000 + i));
            ln.add(pos, Math.random());
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("LinkedList: The operation add random 50 000 takes: %dms\n", dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < 50000; i++) {
            int pos = (int) (Math.random() * (100000 - i));
            ar.remove(pos);
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("ArrayList: The operation remove random 50 000 takes: %dms\n", dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < 50000; i++) {
            int pos = (int) (Math.random() * (100000 - i));
            ln.remove(pos);
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("LinkedList: The operation remove random 50 000 takes: %dms\n", dur.toMillis());


    }


}