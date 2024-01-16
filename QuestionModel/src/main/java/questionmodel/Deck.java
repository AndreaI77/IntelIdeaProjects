package questionmodel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Deck implements Serializable {
    private List <Card> cardList;
    Lock lock;

    public Card getCard(){
        lock.lock();
        Card crd = cardList.remove(0);
        cardList.add(crd);
        lock.unlock();
        return crd;
    }

    public Deck(Path file)  {
        cardList = new ArrayList<Card>();
        this.lock = new ReentrantLock();
        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                String[] st = line.split(";");
                Card card = new Card(st[0], st[5], st[6], new String[]{st[1], st[2], st[3], st[4]});
                cardList.add(card);
            }
            Collections.shuffle(cardList);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}