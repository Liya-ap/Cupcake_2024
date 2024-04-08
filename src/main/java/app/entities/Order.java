package app.entities;

import java.util.List;

public class Order {

    private int orderId;
    private int userId;
    private String email;
    private List<Cupcake> cupcakes;

    public Order(int orderId, int userId, List<Cupcake> cupcakes) {
        this.orderId = orderId;
        this.userId = userId;
        this.cupcakes = cupcakes;

    }

    public Order(int orderId,  String email, List<Cupcake> cupcakes) {
        this.orderId = orderId;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cupcake> getCupcakes() {
        return cupcakes;
    }

    public void setCupcakes(List<OrderDetails> orderDetails) {
        this.cupcakes = cupcakes;
    }

}


