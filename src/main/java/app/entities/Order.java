package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private int userID;
    private String email;
    private List<Cupcake> cupcakes;

    public Order(int orderID, int userID, List<Cupcake> cupcakes) {
        this.orderID = orderID;
        this.userID = userID;
        this.cupcakes = cupcakes;
    }

    public Order(int orderID, String email, List<Cupcake> cupcakes) {
        this.orderID = orderID;
        this.email = email;
        this.cupcakes = cupcakes;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

}


