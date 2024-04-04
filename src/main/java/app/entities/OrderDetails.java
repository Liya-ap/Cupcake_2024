package app.entities;

public class OrderDetails {
    private int orderDetailID;
    private int toppingId;
    private int bottomId;
    private int amount;
    private int priceEach;

    public OrderDetails(int orderDetailID, int toppingId, int bottomId, int amount, int priceEach) {
        this.orderDetailID = orderDetailID;
        this.toppingId = toppingId;
        this.bottomId = bottomId;
        this.amount = amount;
        this.priceEach = priceEach;
    }

    public OrderDetails(int toppingId, int bottomId, int priceEach) {
        this.toppingId = toppingId;
        this.bottomId = bottomId;
        this.priceEach = priceEach;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getToppingId() {
        return toppingId;
    }

    public void setToppingId(int toppingId) {
        this.toppingId = toppingId;
    }

    public int getBottomId() {
        return bottomId;
    }

    public void setBottomId(int bottomId) {
        this.bottomId = bottomId;
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
}
