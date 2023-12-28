import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
     List<Character> lista = new ArrayList<>();
     for(int i = 0;i<5;i++){
         lista.add(new Enemy());
     }
     for(int i = 0;i<5;i++){
         lista.add(new Friend());
     }
        Collections.shuffle(lista);
     for(Character c : lista){
         if(c.isEnemy()){
             System.out.println("Character " + lista.indexOf(c)+" is an enemy. Kill it!");
             ((Enemy)c).kill();
         }else{
             System.out.println("Character " + lista.indexOf(c)+" is a friend. :-)");
         }
     }

    }
}