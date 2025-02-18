package hundirlaflotasocket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {
    public static boolean login(String username, String password) {
        String query = "SELECT * FROM usuarios WHERE username = ? AND password = ? AND estado = 'desconectado'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si el usuario existe y está desconectado, lo conectamos
                updateUserStatus(username, "conectado");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void updateUserStatus(String username, String status) {
        String query = "UPDATE usuarios SET estado = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logout(String username) {
        updateUserStatus(username, "desconectado");
    }
}