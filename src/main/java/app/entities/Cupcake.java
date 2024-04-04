package app.entities;

public class Cupcake {
    private int cupcakeID;
    private Topping topping;
    private Bottom bottom;
    private int amount;
    private int priceEach;

    public Cupcake() {}

    public Cupcake(int cupcakeID, Topping topping, Bottom bottom, int amount, int priceEach) {
        this.cupcakeID = cupcakeID;
        this.topping = topping;
        this.bottom = bottom;
        this.amount = amount;
        this.priceEach = priceEach;
    }

    public int getCupcakeID() {
        return cupcakeID;
    }

    public void setCupcakeID(int cupcakeID) {
        this.cupcakeID = cupcakeID;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public Bottom getBottom() {
        return bottom;
    }

    public void setBottom(Bottom bottom) {
        this.bottom = bottom;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPriceEach() {
        return priceEach;
    }

    public void setPriceEach(int priceEach) {
        this.priceEach = priceEach;
    }

    @Override
    public String toString() {
        return "Cupcake{" +
                "cupcakeID=" + cupcakeID +
                ", topping=" + topping +
                ", bottom=" + bottom +
                ", amount=" + amount +
                ", priceEach=" + priceEach +
                '}';
    }
}
