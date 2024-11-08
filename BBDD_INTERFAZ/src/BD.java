
import java.sql.*;
import java.util.Scanner;
        
public class BD {


    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        
        String urlCon = "jdbc:oracle:thin:@localhost:xe";
        String user = "root";
        String password = "";
        
        Connection conexion = DriverManager.getConnection (urlCon,user,password);
        Statement sentencia=conexion.createStatement();
        ResultSet resultado=sentencia.executeQuery("");
    }
    
}
