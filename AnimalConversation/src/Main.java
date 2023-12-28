// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        AnimalConversation<Dog,Cat>ac= new AnimalConversation<>(new Dog("Perro"), new Cat("Gato"));
        ac.chat();

        AnimalConversation<Dog, Sheep> an = new AnimalConversation<>(new Dog("Perro"), new Sheep("Dolly"));
        an.chat();

        AnimalConversation<Cat, Sheep> av = new AnimalConversation<> (new Cat("Gato"), new Sheep("Dolly"));
        av.chat();
    }
}