package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketMapper {

    /**
     * Inserts the user's id into the order table and returns the created order_id
     * @param user the user that made the order
     * @param connectionPool ConnectionPool used to execute sql for inserting user id and getting an order id
     * @return the created order id as int
     * @throws DatabaseException Displays "Failed at creating an order" + system msg
     */
    public static int createOrder(User user, ConnectionPool connectionPool) throws DatabaseException {
        int orderID = -1;
        String sql = "INSERT INTO public.order (user_id) VALUES (?) RETURNING order_id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, user.getUserId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orderID = rs.getInt("order_id");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed at creating an order", e.getMessage());
        }

        return orderID;
    }

    /**
     * Inserts the order id and cupcakedetail id into the orderline table
     * @param orderID the order that should contain the orderline
     * @param cupcakeDetailID the cupcake id representing a cupcake
     * @param connectionPool ConnectionPool used to execute sql for inserting order id and cupcakedetail id
     * @throws DatabaseException Displays "Failed at adding cupcake to basket" + system msg
     */
    public static void createOrderline(int orderID, int cupcakeDetailID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO orderline (order_id, cupcakedetail_id) VALUES (?, ?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderID);
            ps.setInt(2, cupcakeDetailID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed at adding cupcake to basket", e.getMessage());
        }

    }

    /**
     * Selects all the cupcake ids from a given user's basket
     * @param user the user whose basket is viewed
     * @param connectionPool ConnectionPool used to execute sql for selecting cupcakedetail ids
     * @return list of integers containing the cupcakedetail ids from database
     * @throws DatabaseException Displays "Failed at getting cupcakes all cupcakes from basket" + system msg
     */
    public static List<Integer> getCupcakeDetailIDs(User user, ConnectionPool connectionPool) throws DatabaseException {
        List<Integer> cupcakedetailIDs = new ArrayList<>();
        String sql = "SELECT cupcakedetail_id FROM basket WHERE user_id = ?;";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, user.getUserId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cupcakedetailID = rs.getInt("cupcakedetail_id");

                cupcakedetailIDs.add(cupcakedetailID);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed at getting cupcakes all cupcakes from basket", e.getMessage());
        }
        return cupcakedetailIDs;
    }

    /**
     * Removes all cupcakes from the given user's basket
     * @param user the user whose basket is being deleted
     * @param connectionPool ConnectionPool used to execute sql for deleting cupcakes from basket based on user id
     * @throws DatabaseException Displays "Failed at deleting cupcakes from basket" + system msg
     */
    public static void removeAllFromBasket(User user, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM basket WHERE user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed at deleting cupcakes from basket", e.getMessage());
        }
    }
}
