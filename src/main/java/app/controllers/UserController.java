package app.controllers;

import app.validators.EmailValidator;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import app.validators.PasswordValidator;
import app.validators.PayUserValidator;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx -> ctx.render("login/login-page.html"));
        app.post("/login", ctx -> login(ctx, connectionPool));

        app.get("/createuser", ctx -> ctx.render("login/createuser-page.html"));
        app.post("/createuser", ctx -> createUser(ctx, connectionPool));

        app.get("/payuser", ctx -> displayAllUserBalances(ctx, connectionPool));
        app.post("/payuser", ctx -> {
            changeBalance(ctx, connectionPool);
            displayAllUserBalances(ctx, connectionPool);
        });

        app.get("/logout", ctx -> logout(ctx));

    }

    private static void displayAllUserBalances(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        ctx.attribute("currentUserEmail", currentUser.getEmail());
        List<User> userList = UserMapper.getAllUsers(connectionPool);
        ctx.attribute("userList", userList);

        ctx.render("admin/userbalances-page.html");
    }

    private static void changeBalance(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        if (PayUserValidator.isValidInput(ctx.formParam("amount"))) {
            int amount = Integer.parseInt(ctx.formParam("amount"));
            int userId = Integer.parseInt(ctx.formParam("userId"));
            int balance = Integer.parseInt(ctx.formParam("balance"));

            if (PayUserValidator.isValidPayment(amount, balance)) {
                UserMapper.setUserBalance(amount, userId, connectionPool);
            } else {
                ctx.attribute("message", "Insufficient funds. Try again.");
            }
        } else {
            ctx.attribute("message", "Invalid input. Try again.");
        }
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            ctx.redirect("/homepage");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("login/login-page.html");
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
                        ctx.render("login/login-page.html");
                    } catch (DatabaseException e) {
                        ctx.attribute("message", "Email already exists. Try again.");
                    }
                } else {
                    ctx.attribute("message", "The entered passwords don't match. Try again.");
                }
            } else {
                ctx.attribute("message", "The entered password is not valid. It must contain minimum 8 letters, an uppercase, a lowercase and a number. Try again.");
            }
        } else {
            ctx.attribute("message", "The entered email is not valid. Try again.");
        }

        ctx.render("login/createuser-page.html");
    }

    /**
     * Returns to the login page
     * Logs out/invalidates the current session user
     *
     * @param ctx Context parameter used to get session user and invalidates it
     */
    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }
}

