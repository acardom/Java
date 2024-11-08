package bbdd;

import java.sql.*;
public class BBDD {
    public static void main(String[] args) throws SQLException {
        
        String urlCon = "jdbc:oracle:thin:@localhost:1521/XE";
        String user = "ACD";
        String password = "MANAGER";
        Connection conexion = DriverManager.getConnection (urlCon,user,password);
        
        Statement sentencia=conexion.createStatement();
        
        }
    }

