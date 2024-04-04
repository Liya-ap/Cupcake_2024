package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("createuser", ctx -> ctx.render("createuser.html"));
        app.post("createuser", ctx -> createUser(ctx, connectionPool));
    }

    public static void login(Context ctx, ConnectionPool connectionPool) {

        // Hent formparametre
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        // Check om bruger findes i DB med de angivne username + password
        try {
            User user = UserMapper.login(username, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            // Hvis ja, send videre til task siden

            // Gem brugerens ordrehistorik i en liste og som attribute
            // List<Order> orderList = OrderMapper.getAllOrdersPerUser(user.getUserId(), connectionPool);
            // ctx.attribute("orderList", orderList);

            ctx.render("frontpage.html");
        } catch (DatabaseException e) {
            // Hvis nej, send tilbage til login side med fejl besked
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {

        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2)) {
            try {
                UserMapper.createuser(username, password1, connectionPool);
                ctx.attribute("message", "User with username " + username + " succesfully created. You can now login.");
                ctx.render("login.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", "Username already exists. Try again.");
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "The entered passwords don't match. Try again.");
            ctx.render("createuser.html");
        }
    }
}
