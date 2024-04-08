package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class OrderMapper {
    public static List<Order> getAllUserOrders(int userId, ConnectionPool connectionPool) throws DatabaseException {

        List<Order> listOfAllOrders = new ArrayList<>();

        String sql = "SELECT public.order.order_id, bottom.flavor AS bottomflavor, topping.flavor AS toppingflavor," +
                " cupcakedetail.price_each AS priceeach, cupcakedetail.amount AS amount, (price_each * amount) AS totalprice \n" +
                "FROM public.order\n" +
                "INNER JOIN public.orderline ON public.order.order_id = orderline.order_id\n" +
                "INNER JOIN users ON public.order.user_id = users.user_id\n" +
                "INNER JOIN cupcakedetail ON orderline.cupcakedetail_id = cupcakedetail.cupcakedetail_id\n" +
                "INNER JOIN bottom ON cupcakedetail.bottom_id = bottom.bottom_id\n" +
                "INNER JOIN topping ON cupcakedetail.topping_id = topping.topping_id\n" +
                "WHERE public.order.user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String bottomFlavor = rs.getString("bottomflavor");
                String toppingFlavor = rs.getString("toppingflavor");
                int priceEach = rs.getInt("priceeach");
                int amount = rs.getInt("amount");
                int totalPrice = rs.getInt("totalprice");

                List<Cupcake> listofCupcakes = new ArrayList<>();

                listofCupcakes.add(new Cupcake(new Topping(toppingFlavor), new Bottom(bottomFlavor), priceEach, amount, totalPrice));

                listOfAllOrders.add(new Order(orderId, userId, listofCupcakes));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i SQL!!", e.getMessage());
        }
        return listOfAllOrders;
    }

    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {

        List<Order> listOfAllOrders = new ArrayList<>();

        String sql = "SELECT public.order.order_id, users.email, bottom.flavor AS bottomflavor, topping.flavor AS toppingflavor, cupcakedetail.price_each AS priceeach, cupcakedetail.amount AS amount, (price_each * amount) AS totalprice\n" +
                "FROM public.order\n" +
                "INNER JOIN public.orderline ON public.order.order_id = orderline.order_id\n" +
                "INNER JOIN users ON public.order.user_id = users.user_id\n" +
                "INNER JOIN cupcakedetail ON orderline.cupcakedetail_id = cupcakedetail.cupcakedetail_id\n" +
                "INNER JOIN bottom ON cupcakedetail.bottom_id = bottom.bottom_id\n" +
                "INNER JOIN topping ON cupcakedetail.topping_id = topping.topping_id";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String email = rs.getString("email");
                String bottomFlavor = rs.getString("bottomflavor");
                String toppingFlavor = rs.getString("toppingflavor");
                int priceEach = rs.getInt("priceeach");
                int amount = rs.getInt("amount");
                int totalPrice = rs.getInt("totalprice");



                List<Cupcake> listofCupcakes = new ArrayList<>();

                listofCupcakes.add(new Cupcake(new Topping(toppingFlavor), new Bottom(bottomFlavor), priceEach, amount, totalPrice));

                listOfAllOrders.add(new Order(orderId, email, listofCupcakes));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i SQL!!", e.getMessage());
        }
        return listOfAllOrders;
    }

    public static void deleteOrderLine(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sqlOrderLine = "DELETE FROM orderline WHERE order_id = ?";
        String sqlOrder = "DELETE FROM public.order WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement psDeleteOrderLines = connection.prepareStatement(sqlOrderLine);
             PreparedStatement psDeleteOrder = connection.prepareStatement(sqlOrder)) {

            // Delete order lines first
            psDeleteOrderLines.setInt(1, orderId);
            psDeleteOrderLines.executeUpdate();

            // Then delete the order
            psDeleteOrder.setInt(1, orderId);
            int rowsAffected = psDeleteOrder.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en ordre");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning af en Ordre line  i databasen", e.getMessage());
        }
    }
}
