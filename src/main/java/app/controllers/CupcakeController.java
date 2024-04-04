package app.controllers;

import app.entities.Bottom;
import app.entities.Cupcake;
import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

public class CupcakeController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool){

        app.get("/", ctx -> displayFrontPage(ctx, connectionPool));

        app.post("/addCupcake", ctx -> {
            addCupcakeToBasket(ctx, connectionPool);
            displayFrontPage(ctx, connectionPool);
        });

        app.get("/basket", ctx -> ctx.redirect("/displayBasket"));

        app.get("/displayBasket", ctx -> showAllCupcakesInBasket(ctx, connectionPool));
    }

    private static void displayFrontPage(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        Cupcake cupcake = new Cupcake();
        ctx.attribute("cupcakeobj", cupcake);
        getAllToppings(ctx, connectionPool);
        getAllBottoms(ctx, connectionPool);
        displayAmountOfCupcakesInBasket(ctx, connectionPool);
        ctx.render("frontpage.html");
    }

    public static void addCupcakeToBasket(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String nullFields = "Could not add cupcake to basket";

        try {
            String toppingFlavor = ctx.formParam("toppingFlavor");
            String bottomFlavor = ctx.formParam("bottomFlavor");
            int amount = Integer.parseInt(ctx.formParam("amount"));

            if (toppingFlavor == null || bottomFlavor == null) {
                ctx.attribute("nullFields", nullFields);
            } else {
                Topping topping = CupcakeMapper.getTopping(toppingFlavor, connectionPool);
                System.out.println(topping.getFlavor() + topping.getPrice());
                Bottom bottom = CupcakeMapper.getBottom(bottomFlavor, connectionPool);
                System.out.println(bottom.getFlavor() + bottom.getPrice());
                int priceEach = topping.getPrice() + bottom.getPrice();
                int cupcakeDetailID = CupcakeMapper.createCupcake(topping.getToppingID(), bottom.getBottomID(), amount, priceEach, connectionPool);
                //int userID = User.getUserID();
                CupcakeMapper.addToBasket(cupcakeDetailID, 2, connectionPool);
            }
        } catch (NumberFormatException e) {
            ctx.attribute("nullFields", nullFields);
        }
    }

    public static void getAllToppings(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        ctx.attribute("allToppingFlavors", CupcakeMapper.getAllToppings(connectionPool));
    }

    public static void getAllBottoms(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        ctx.attribute("allBottomFlavors", CupcakeMapper.getAllBottoms(connectionPool));
    }

    public static void showAllCupcakesInBasket(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        //int userID = User.getUserID();
        List<Cupcake> allCupcakesInBasket = CupcakeMapper.getAllCupcakesInBasket(2,connectionPool);
        ctx.attribute("all-cupcakes-in-basket", allCupcakesInBasket);
        ctx.render("basket.html");
    }

    public static void displayAmountOfCupcakesInBasket(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        List<Cupcake> allCupcakesInBasket = CupcakeMapper.getAllCupcakesInBasket(2,connectionPool);
        ctx.attribute("amountInBasket", allCupcakesInBasket.size());
    }

}
