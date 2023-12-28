public class AnimalConversation <T extends Animal, E extends Animal> {
    private T a1;
    private E a2;

    public AnimalConversation(T a1, E a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public void chat(){
        System.out.println( this.a1.getClass().getName()+" "+this.a1.name +": "+this.a1.talk()+" talks to " +this.a2.getClass().getName() +" "+this.a2.name +": "+this.a2.talk());

    }
}
