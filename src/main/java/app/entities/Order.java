package app.entities;

import java.util.List;

public class Order {

    private int orderId;
    private int userId;
    private List<OrderDetails> orderDetails;

    public Order(int orderId, int userId, List<OrderDetails> orderDetails) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDetails = orderDetails;
    }

    public Order(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
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

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    /*public double calculateTotalPrice (){
        double totalPrice = 0;

        for (OrderDetails orderDetails : orderDetails){
            totalPrice += orderDetails.getPriceEach() * orderDetails.getAmount();
        }
        return totalPrice;
    }*/

}


