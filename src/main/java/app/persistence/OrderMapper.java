package app.persistence;

import app.entities.Order;
import app.entities.OrderDetails;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;


public class OrderMapper {


    public static OrderDetails addOrderDetails(int bottomId, int toppingID, ConnectionPool connectionPool) throws DatabaseException {
        OrderDetails newOrderDetails = null;

        String sql = "INSERT INTO public.orderdetail( orderdetail_id, topping_id, bottom_id, amount, price_each) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, (toppingID));
            ps.setInt(3, (bottomId));
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int newId = rs.getInt(1);
                newOrderDetails = new OrderDetails(newId, toppingID, bottomId, newOrderDetails.getAmount(), newOrderDetails.getPriceEach());
            } else {
                throw new DatabaseException("Fejl under indsætning af Ordredetaljer: ");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl i SQL!!", e.getMessage());
        }

        return newOrderDetails;
    }

    public static List<OrderDetails> seeOrderLine(User user, int bottomId, int toppingID, ConnectionPool connectionPool) throws DatabaseException {
        List<OrderDetails> orderLineList = new ArrayList<>();

        String sql = "SELECT SUM(price_each) AS total_price FROM public.orderdetail WHERE bottom_id = ? AND topping_id = ? AND user_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, bottomId);
            ps.setInt(2, toppingID);
            ps.setInt(3, user.getUserId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int topping = rs.getInt("topping_id");
                int bottom = rs.getInt("bottom_id");
                int price = rs.getInt("price_each");

                orderLineList.add(new OrderDetails(topping, bottom, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i SQL!!", e.getMessage());
        }
        return orderLineList;
    }


    public static List<Order> showAllOrders(int orderID, ConnectionPool connectionPool) throws DatabaseException {
        List<Order>listOfOrders = new ArrayList<>();
        String sql = "SELECT order_id, user_id FROM public.order WHERE order_id = ? AND user_id =? ORDER BY user_id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");

                listOfOrders.add(new Order(orderId,userId));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i SQL!!", e.getMessage());
        }
        return listOfOrders;
    }

    public static void deleteOrder(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM public.order where order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en ordre");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning af en Ordre i databasen", e.getMessage());
        }
    }
    public static void deleteOrderLine(int orderLine, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM public.orderline where order_id = ? AND orderdetail_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderLine);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en ordre");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning af en Ordre line  i databasen", e.getMessage());
        }
    }

}
