package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from users where email=? and password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                String role = rs.getString("role");
                int balance = rs.getInt("balance_dkk");
                return new User(id, email, password, role, balance);
            } else {
                throw new DatabaseException("Error, failed to login. Try again.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error", e.getMessage());
        }
    }

    public static void createuser(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into users (email, password) values (?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Error, failed to create a user");
            }
        } catch (SQLException e) {
            String msg = "Error, something went wrong. Try again.";
            if (e.getMessage().startsWith("Error, duplicate key value.")) {
                msg = "Email already exists. Choose another one.";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}
