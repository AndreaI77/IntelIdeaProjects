// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
      Store store = new LiquorStore(8.95,20);

      store.welcome();
      store.payDrinks(10);
      System.out.printf("Total cash: %.2f€\n", store.getCash());

      Store st = new Store(8.95){

          @Override
          public void welcome() {
              System.out.printf("Welcome to anonymous store!, Our drink price is %.2f€\n", getDrinkPrice());
          }
      };
      st.welcome();
      st.payDrinks(10);
      System.out.printf("Total cash: %.2f€\n", st.getCash());
    }
}