public abstract class Store {
    private double cash, drinkPrice;

    public Store(double drinkPrice){
        this.drinkPrice = drinkPrice;
        this.cash = 0.0;
    }

    public double getCash() {
        return cash;
    }

    public void addCash(double cash) {
        this.cash += cash;
    }

    public double getDrinkPrice() {
        return drinkPrice;
    }

    public abstract void welcome();

    public void payDrinks(int numOfDrinks){
        this.cash += numOfDrinks * this.drinkPrice;
    }
}
