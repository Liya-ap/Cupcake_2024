package app.entities;

public class Bottom {

    private int bottomID;
    private String flavor;
    private int price;

    public Bottom(String flavor) {
        this.flavor = flavor;
    }

    public Bottom(int bottomID, String flavor, int price) {
        this.bottomID = bottomID;
        this.flavor = flavor;
        this.price = price;


    }

    public int getBottomID() {
        return bottomID;
    }

    public void setBottomID(int bottomID) {
        this.bottomID = bottomID;
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
        return "Bottom{" +
                "flavor='" + flavor + '\'' +
                ", price=" + price +
                '}';
    }
}
