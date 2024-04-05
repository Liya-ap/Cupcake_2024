package app.entities;

public class Topping {

    private int toppingID;
    private String flavor;
    private int price;

    public Topping(String flavor) {
        this.flavor = flavor;
    }

    public Topping(int toppingID, String flavor, int price) {
        this.toppingID = toppingID;
        this.flavor = flavor;
        this.price = price;

    }

    public int getToppingID() {
        return toppingID;
    }

    public void setToppingID(int toppingID) {
        this.toppingID = toppingID;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Topping{" +
                "flavor='" + flavor + '\'' +
                ", price=" + price +
                '}';
    }
}
