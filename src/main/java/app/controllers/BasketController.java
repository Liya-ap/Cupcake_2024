package app.controllers;

import app.entities.Cupcake;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.BasketMapper;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import app.persistence.UserMapper;
import app.validators.PayUserValidator;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class BasketController {
    private static List<Cupcake> allCupcakesInBasket = new ArrayList<>();

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/yourBasket", ctx -> displayBasketPage(ctx, connectionPool));

        app.post("/cupcakesPurchased", ctx -> {
            purchaseCupcakes(ctx, connectionPool);
            displayAfterPurchase(ctx, connectionPool);
        });
    }

    /**
     * Displays the basket page with all the cupcakes that the user has added to the basket
     * Shows relevant information regarding the cupcakes picked
     *
     * @param ctx            Context parameter used to get the current user, set user email and renders basket page
     * @param connectionPool ConnectionPool used to get amount/list of cupcakes in basket
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    private static void displayBasketPage(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        ctx.attribute("currentUserEmail", currentUser.getEmail());
        CupcakeController.getAmountOfCupcakesInBasket(ctx, connectionPool);
        listOfCupcakesInBasket(ctx, connectionPool, currentUser);
        getTotalPriceForBasket(ctx);

        ctx.render("customer/basket-page.html");
    }

    /**
     * Displays the basket page after the current user has made a purchase
     *
     * @param ctx            Context parameter used to get the current user, set user email and renders basket page
     * @param connectionPool ConnectionPool used to get amount of cupcakes in basket and to delete all from basket
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    private static void displayAfterPurchase(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        ctx.attribute("currentUserEmail", currentUser.getEmail());
        BasketMapper.removeAllFromBasket(currentUser, connectionPool);
        CupcakeController.getAmountOfCupcakesInBasket(ctx, connectionPool);
        
        ctx.render("customer/basket-page.html");
    }

    /**
     * Creates an order and orderlines if the user has chosen to purchase the cupcakes in the basket
     *
     * @param ctx            Context parameter used to get the current user, total price for items, created order number and renders basket page
     * @param connectionPool ConnectionPool used to create order and orderline
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    private static void purchaseCupcakes(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        int priceToBeDeducted = -1 * getTotalPriceForBasket(ctx);

        if (PayUserValidator.isValidPayment(priceToBeDeducted, currentUser.getBalance())) {
            int orderID = createOrder(currentUser, connectionPool);
            createOrderlines(orderID, currentUser, connectionPool);
            UserMapper.setUserBalance(priceToBeDeducted, currentUser.getUserID(), connectionPool);
            ctx.attribute("orderNumber", orderID);
            ctx.attribute("purchased", true);
        } else {
            ctx.attribute("purchased", false);
            ctx.attribute("currentUserBalance", currentUser.getBalance());
        }
    }

    /**
     * Gets the total price for all cupcakes in the user's basket
     *
     * @param ctx Context parameter used to make total price retrievable
     * @return the total price as int
     */
    private static int getTotalPriceForBasket(Context ctx) {
        int totalPrice = 0;
        for (Cupcake cupcake : allCupcakesInBasket) {
            totalPrice += cupcake.getPriceEach() * cupcake.getAmount();
        }

        ctx.attribute("totalPrice", totalPrice);
        return totalPrice;
    }

    /**
     * Calls to BasketMapper class to create the order
     *
     * @param connectionPool Database to be used to create an order
     * @param currentUser    The current user that has made an order
     * @return The order number as int
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    private static int createOrder(User currentUser, ConnectionPool connectionPool) throws DatabaseException {
        return BasketMapper.createOrder(currentUser, connectionPool);
    }

    /**
     * Calls to BasketMapper class to create the orderline(s)
     *
     * @param connectionPool Database to be used to create an order
     * @param orderID        The order id that contains the orderlines
     * @param currentUser    The current user that has made an order
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    private static void createOrderlines(int orderID, User currentUser, ConnectionPool connectionPool) throws DatabaseException {
        List<Integer> cupcakeDetailIDs = BasketMapper.getCupcakeDetailIDs(currentUser, connectionPool);
        for (int cupcakeDetailId : cupcakeDetailIDs) {
            BasketMapper.createOrderline(orderID, cupcakeDetailId, connectionPool);
        }
    }

    /**
     * Gets all the cupcakes in the current user's basket
     *
     * @param ctx            Context parameter used to make current user's list of items in basket retrievable
     * @param connectionPool ConnectionPool used to retrieve all cupcakes in current users basket from database
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    private static void listOfCupcakesInBasket(Context ctx, ConnectionPool connectionPool, User currentUser) throws DatabaseException {
        allCupcakesInBasket = CupcakeMapper.getAllCupcakesInBasket(currentUser, connectionPool);
        ctx.attribute("allCupcakesInBasket", allCupcakesInBasket);
    }
}
