package app.entities;

import java.util.List;

public class Order {

    private int orderId;
    private int userId;
    private List<Cupcake> cupcakes;

    public Order(int orderId, int userId, List<Cupcake> cupcakes) {
        this.orderId = orderId;
        this.userId = userId;
        this.cupcakes = cupcakes;
    }



    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Cupcake> getCupcakes() {
        return cupcakes;
    }

    public void setCupcakes(List<OrderDetails> orderDetails) {
        this.cupcakes = cupcakes;
    }

}


