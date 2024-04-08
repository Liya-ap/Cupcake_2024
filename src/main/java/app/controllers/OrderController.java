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
        app.get("customerorder", ctx -> displayCustomerOrders(ctx, connectionPool));
        app.get("adminorder", ctx -> displayAdminOrders(ctx, connectionPool));
        app.post("deleteorder", ctx -> deleteOrder(ctx, connectionPool));
    }

    private static void displayCustomerOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        ctx.attribute("currentUserEmail", currentUser.getEmail());
        CupcakeController.getAmountOfCupcakesInBasket(ctx, connectionPool);
        List<Order> listOfOrders = OrderMapper.getAllUserOrders(currentUser.getUserId(), connectionPool);
        ctx.attribute("listOfOrders", listOfOrders);
        ctx.render("customerorder.html");
    }

    private static void displayAdminOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        ctx.attribute("currentUserEmail", currentUser.getEmail());
        List<Order> cupcakes = OrderMapper.getAllOrders(connectionPool);
        ctx.attribute("listOfAllOrders", cupcakes);
        ctx.render("adminorder.html");
    }

    private static void deleteOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        int orderId = Integer.parseInt(ctx.formParam("orderId"));

        OrderMapper.deleteOrderLine(orderId, connectionPool);

        List<Order> listOfAllOrders = OrderMapper.getAllOrders(connectionPool);
        ctx.attribute("listOfAllOrders", listOfAllOrders);
        ctx.render("adminorder.html");
    }
}


