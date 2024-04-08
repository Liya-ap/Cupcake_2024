package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class OrderController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/customerorder", ctx -> displayCurrentCustomersOrderHistory(ctx, connectionPool));
        app.get("/adminorder", ctx -> displayAllCustomerOrderHistories(ctx, connectionPool));

        app.post("/deleteorder", ctx -> {
            deleteOrder(ctx, connectionPool);
            displayAllCustomerOrderHistories(ctx, connectionPool);
        });
    }

    private static void displayCurrentCustomersOrderHistory(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        ctx.attribute("currentUserEmail", currentUser.getEmail());
        CupcakeController.getAmountOfCupcakesInBasket(ctx, connectionPool);
        List<Order> listOfOrders = OrderMapper.getACustomersOrders(currentUser.getUserID(), connectionPool);
        ctx.attribute("listOfOrders", listOfOrders);

        ctx.render("customer/myOrders-page.html");
    }

    private static void displayAllCustomerOrderHistories(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        ctx.attribute("currentUserEmail", currentUser.getEmail());
        List<Order> listOfAllOrders = OrderMapper.getAllOrders(connectionPool);
        ctx.attribute("listOfAllOrders", listOfAllOrders);

        ctx.render("admin/allOrders-page.html");
    }

    private static void deleteOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        OrderMapper.deleteOrder(orderId, connectionPool);
    }
}


