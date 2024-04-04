package app.persistence;

import app.entities.Bottom;
import app.entities.Cupcake;
import app.entities.Topping;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CupcakeMapper {

    public static Topping getTopping(String toppingFlavor, ConnectionPool connectionPool) throws DatabaseException {
        Topping chosenTopping = null;
        String sql = "SELECT * FROM topping WHERE flavor = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, toppingFlavor);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int toppingID = rs.getInt("topping_id");
                String flavor = rs.getString("flavor");
                int price = rs.getInt("price");

                 chosenTopping = new Topping(toppingID, flavor, price);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed at getting a topping", e.getMessage());
        }
        return chosenTopping;
    }

    public static List<Topping> getAllToppings(ConnectionPool connectionPool) throws DatabaseException {
        List<Topping> allToppings = new ArrayList<>();
        String sql = "SELECT * FROM public.topping";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int topping_id = rs.getInt("topping_id");
                String flavor = rs.getString("flavor");
                int price = rs.getInt("price");

                allToppings.add(new Topping(topping_id, flavor, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed at getting all toppings", e.getMessage());
        }
        return allToppings;
    }

    public static Bottom getBottom(String bottomFlavor, ConnectionPool connectionPool) throws DatabaseException {
        Bottom chosenBottom = null;
        String sql = "SELECT * FROM bottom WHERE flavor = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, bottomFlavor);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int toppingID = rs.getInt("bottom_id");
                String flavor = rs.getString("flavor");
                int price = rs.getInt("price");

                chosenBottom = new Bottom(toppingID, flavor, price);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed at getting a bottom", e.getMessage());
        }
        return chosenBottom;
    }

    public static List<Bottom> getAllBottoms(ConnectionPool connectionPool) throws DatabaseException {
        List<Bottom> allBottoms = new ArrayList<>();
        String sql = "SELECT * FROM public.bottom";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int topping_id = rs.getInt("bottom_id");
                String flavor = rs.getString("flavor");
                int price = rs.getInt("price");

                allBottoms.add(new Bottom(topping_id, flavor, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed at getting all bottoms", e.getMessage());
        }
        return allBottoms;
    }

    public static int createCupcake(int toppingID, int bottomID, int amount, int priceEach, ConnectionPool connectionPool) throws DatabaseException {
        int cupcakeDetailID = -1;
        String sql = "INSERT INTO cupcakedetail (topping_id, bottom_id, amount, price_each) VALUES (?, ?, ?, ?) RETURNING cupcakedetail_id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, toppingID);
            ps.setInt(2, bottomID);
            ps.setInt(3, amount);
            ps.setInt(4, priceEach);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cupcakeDetailID = rs.getInt("cupcakedetail_id");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed at creating a cupcake", e.getMessage());
        }
        return cupcakeDetailID;
    }

    public static void addToBasket(int cupcakeDetailID, int userID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO basket (cupcakedetail_id, user_id) VALUES (?, ?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, cupcakeDetailID);
            ps.setInt(2, userID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed at adding cupcake to basket", e.getMessage());
        }
    }

    public static List<Cupcake> getAllCupcakesInBasket(int userID, ConnectionPool connectionPool) throws DatabaseException {
        List<Cupcake> allCupcakesInBasket = new ArrayList<>();
        String sql = "SELECT  user_id, basket_id, c.cupcakedetail_id, amount, price_each," +
                "t.topping_id, t.flavor AS topping_flavor, t.price AS topping_price, " +
                "b.bottom_id, b.flavor AS bottom_flavor, b.price AS bottom_price  " +
                "FROM cupcakedetail c " +
                "INNER JOIN basket ba ON c.cupcakedetail_id = ba.cupcakedetail_id AND ba.user_id = ?\n" +
                "INNER JOIN topping t ON c.topping_id = t.topping_id\n" +
                "INNER JOIN bottom b ON c.bottom_id = b.bottom_id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cupcakeID = rs.getInt("cupcakedetail_id");

                int toppingID = rs.getInt("topping_id");
                String toppingFlavor = rs.getString("topping_flavor");
                int toppingPrice = rs.getInt("topping_price");
                Topping topping = new Topping(toppingID, toppingFlavor, toppingPrice);

                int bottomID = rs.getInt("bottom_id");
                String bottomFlavor = rs.getString("bottom_flavor");
                int bottomPrice = rs.getInt("bottom_price");
                Bottom bottom = new Bottom(bottomID, bottomFlavor, bottomPrice);

                int amount = rs.getInt("amount");
                int priceEach = rs.getInt("price_each");

                allCupcakesInBasket.add(new Cupcake(cupcakeID, topping, bottom, amount, priceEach));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed at getting all cupcakes in basket", e.getMessage());
        }
        return allCupcakesInBasket;
    }
}
