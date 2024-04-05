package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("customerorder", ctx -> displayCustomerOrders(ctx, connectionPool));
        app.get("adminorder", ctx -> displayAdminOrders(ctx, connectionPool));
        app.post("adminorder", ctx -> deleteOrder(ctx, connectionPool));
    }


    private static void displayCustomerOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User user = ctx.sessionAttribute("currentUser");
        List<Order> listOfOrders = OrderMapper.getAllUserOrders(user.getUserId(), connectionPool);
        ctx.attribute("listOfOrders", listOfOrders);
        ctx.render("customerorder.html");
    }

    private static void displayAdminOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        List<Order> cupcakes = OrderMapper.getAllOrders(connectionPool);
        ctx.attribute("listOfAllOrders", cupcakes);
        ctx.render("adminorder.html");
    }
   private static void deleteOrder(@NotNull Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        Order order = ctx.attribute("deleteorder");

        OrderMapper.deleteOrderLine(order.getOrderId(),connectionPool);

        List<Order> listOfAllOrders = OrderMapper.getAllOrders(connectionPool);
        ctx.attribute("listOfAllOrders", listOfAllOrders);
        ctx.render("adminorder.html");
    }


   /* private static void deleteOrder(@NotNull Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        Order order = ctx.sessionAttribute("deleteorder");
        if (order == null) {
            // Handle the case when the order is not found in the session
            ctx.redirect("/error");
            return;
        }

        OrderMapper.deleteOrderLine(order.getOrderId(), connectionPool);
        List<Order> listOfAllOrders = OrderMapper.getAllOrders(connectionPool);
        ctx.attribute("listOfAllOrders", listOfAllOrders);
        ctx.render("adminorder.html");
    }*/
}


