package app;

import app.config.ThymeleafConfig;
import app.controllers.UserController;
import app.persistence.ConnectionPool;
import app.controllers.CupcakeController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing here:
        app.get("/", ctx -> ctx.render("login.html"));
        UserController.addRoutes(app, ConnectionPool.getInstance());
        CupcakeController.addRoutes(app, ConnectionPool.getInstance());
    }
}