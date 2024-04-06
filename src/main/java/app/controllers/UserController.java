package app.controllers;

import app.validators.EmailValidator;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import app.validators.PasswordValidator;
import app.validators.PayUserValidate;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx -> ctx.render("login.html"));
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("createuser", ctx -> ctx.render("createuser.html"));
        app.post("createuser", ctx -> createUser(ctx, connectionPool));

        app.get("payuser", ctx -> displayPayUser(ctx, connectionPool));
        app.post("payuser", ctx -> payUser(ctx, connectionPool));

    }

    private static void displayPayUser(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        List<User> userList = UserMapper.getAllUsers(connectionPool);
        ctx.attribute("userList", userList);
        ctx.render("payuser.html");
    }

    private static void payUser(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        //Check om amount er et talværdi
        if (PayUserValidate.isValidInput(ctx.formParam("amount"))) {

            // Hent formparametre
            int amount = Integer.parseInt(ctx.formParam("amount"));
            int userId = Integer.parseInt(ctx.formParam("userId"));
            int balance = Integer.parseInt(ctx.formParam("balance"));

            //Check om der er nok på kontoen, hvis der trækkes fra
            if (PayUserValidate.isValidPayment(amount, balance)) {

                try {
                    UserMapper.setUserBalance(amount, userId, connectionPool);
                    List<User> userList = UserMapper.getAllUsers(connectionPool);
                    ctx.attribute("userList", userList);
                    ctx.render("payuser.html");
                } catch (DatabaseException e) {
                    ctx.attribute("message", "Something went wrong. Try again.");
                    ctx.render("payuser.html");
                }
            } else {
                List<User> userList = UserMapper.getAllUsers(connectionPool);
                ctx.attribute("userList", userList);
                ctx.attribute("message", "Insufficient funds. Try again.");
                ctx.render("payuser.html");
            }
        } else {
            List<User> userList = UserMapper.getAllUsers(connectionPool);
            ctx.attribute("userList", userList);
            ctx.attribute("message", "Invalid input. Try again.");
            ctx.render("payuser.html");
        }
    }

    public static void login(Context ctx, ConnectionPool connectionPool) {

        // Hent formparametre
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // Check om bruger findes i DB med de angivne email + password
        try {
            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            // Hvis ja, send videre til task siden

            // Gem brugerens ordrehistorik i en liste og som attribute
            // List<Order> orderList = OrderMapper.getAllOrdersPerUser(user.getUserId(), connectionPool);
            // ctx.attribute("orderList", orderList);
            ctx.redirect("/frontpage");
        } catch (DatabaseException e) {
            // Hvis nej, send tilbage til login side med fejl besked
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {

        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (EmailValidator.isValidEmail(email)) {
            if (PasswordValidator.isValidPassword(password1)) {
                if (password1.equals(password2)) {
                    try {
                        UserMapper.createuser(email, password1, connectionPool);
                        ctx.attribute("message", "User with email " + email + " succesfully created. You can now login.");
                        ctx.render("login.html");
                    } catch (DatabaseException e) {
                        ctx.attribute("message", "Email already exists. Try again.");
                        ctx.render("createuser.html");
                    }
                } else {
                    ctx.attribute("message", "The entered passwords don't match. Try again.");
                    ctx.render("createuser.html");
                }
            } else {
                ctx.attribute("message", "The entered password is not valid. It must contain minimum 8 letters, an uppercase, a lowercase and a number. Try again.");
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "The entered email is not valid. Try again.");
            ctx.render("createuser.html");
        }
    }
}

