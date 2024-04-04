package app.controllers;

import app.entities.Bottom;
import app.entities.Cupcake;
import app.entities.Topping;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

public class CupcakeController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("/frontpage", ctx -> displayFrontPage(ctx, connectionPool));

        app.post("/addCupcake", ctx -> {
            addCupcakeToBasket(ctx, connectionPool);
            displayFrontPage(ctx, connectionPool);
        });

        app.get("/basket", ctx -> ctx.redirect("/displayBasket"));

        app.get("/displayBasket", ctx -> showAllCupcakesInBasket(ctx, connectionPool));

        app.get("/logout", ctx -> logout(ctx));
    }

    /**
     * Renders the frontpage after login
     * Gets all necessary data from database to be displayed on page
     * @param ctx Context parameter used to obtain information related to the given page
     * @param connectionPool ConnectionPool used to retrieve data from database
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    private static void displayFrontPage(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User user = ctx.sessionAttribute("currentUser");
        ctx.attribute("currentUserEmail", user.getEmail());
        getAllToppings(ctx, connectionPool);
        getAllBottoms(ctx, connectionPool);
        displayAmountOfCupcakesInBasket(ctx, connectionPool);
        ctx.render("frontpage.html");
    }

    /**
     * Adds current user's chosen cupcake to the shopping basket
     * Saves the cupcake to the database for future use so that the user can log in/out and still keep the items in the basket
     * If the user chooses invalid options the above will be ignored and a message will be displayed to the user
     * @param ctx Context parameter used to getting user's cupcake information, getting the current user and making message retrievable
     * @param connectionPool ConnectionPool used to retrieve cupcake information and to save in database
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    public static void addCupcakeToBasket(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String fail = "Could not add cupcake to basket";
        String success = "Added cupcake(s) to basket";

        try {
            String toppingFlavor = ctx.formParam("toppingFlavor");
            String bottomFlavor = ctx.formParam("bottomFlavor");
            int amount = Integer.parseInt(ctx.formParam("amount"));

            if (toppingFlavor == null || bottomFlavor == null) {
                ctx.attribute("fail", fail);
            } else {
                ctx.attribute("success", success);
                Topping topping = CupcakeMapper.getTopping(toppingFlavor, connectionPool);
                Bottom bottom = CupcakeMapper.getBottom(bottomFlavor, connectionPool);
                int priceEach = topping.getPrice() + bottom.getPrice();
                int cupcakeDetailID = CupcakeMapper.createCupcake(topping.getToppingID(), bottom.getBottomID(), amount, priceEach, connectionPool);

                User currentUser = ctx.sessionAttribute("currentUser");
                CupcakeMapper.addToBasket(cupcakeDetailID, currentUser, connectionPool);
            }
        } catch (NumberFormatException e) {
            ctx.attribute("fail", fail);
        }
    }

    /**
     * Retrieves all the toppings from the database and makes it retrievable through Context attribute
     * @param ctx Context parameter used to make list of all toppings retrievable
     * @param connectionPool ConnectionPool used to retrieve all toppings from database
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    public static void getAllToppings(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        ctx.attribute("allToppingFlavors", CupcakeMapper.getAllToppings(connectionPool));
    }

    /**
     * Retrieves all the bottoms from the database and makes it retrievable through Context attribute
     * @param ctx Context parameter used to make list of all bottoms retrievable
     * @param connectionPool ConnectionPool used to retrieve all bottoms from database
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    public static void getAllBottoms(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        ctx.attribute("allBottomFlavors", CupcakeMapper.getAllBottoms(connectionPool));
    }

    /**
     * Renders the basket page and shows the current user's basket with all the added cupcakes
     * @param ctx Context parameter used to get current user and to make current user's list of items in basket retrievable
     * @param connectionPool ConnectionPool used to retrieve all cupcakes in current users basket from database
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    public static void showAllCupcakesInBasket(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        List<Cupcake> allCupcakesInBasket = CupcakeMapper.getAllCupcakesInBasket(currentUser, connectionPool);
        ctx.attribute("allCupcakesInBasket", allCupcakesInBasket);
        ctx.render("basket.html");
    }

    /**
     * Used to show the number of items in current user's basket from the frontpage
     * @param ctx Context parameter used to get the current
     * @param connectionPool ConnectionPool used to get all cupcakes in current user's basket from database
     * @throws DatabaseException Throws exception if database connection/sql fails
     */
    public static void displayAmountOfCupcakesInBasket(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        List<Cupcake> allCupcakesInBasket = CupcakeMapper.getAllCupcakesInBasket(currentUser, connectionPool);
        ctx.attribute("amountInBasket", allCupcakesInBasket.size());
    }

    /**
     * Returns to the login page
     * Logs out/invalidates the current session user
     * @param ctx Context parameter used to get session user and invalidates it
     */
    private static void logout(Context ctx)
    {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

}
