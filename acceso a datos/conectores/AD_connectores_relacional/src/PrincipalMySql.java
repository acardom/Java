import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrincipalMySql {

	// jardineria is database
	// user is user of database
	// pass is password of database
	private String nameDatabase = "jardineria";
	private String user = "root";
	private String pass = "root";

	public PrincipalMySql() {
	}

	public void consultaPorNombreColumna() {
		Connection connection = null;
		try {

			// jardineria is database
			// user is user of database
			// pass is password of database

			// Class.forName("com.mysql.cj.jdbc.Driver"); //De versiones antiguas

			// Notese ip, puerto, base de datos, usuario, password
			// connection =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/jardineria",
			// "userA", "passwordA");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT codigo_cliente, nombre_cliente, nombre_contacto, apellido_contacto FROM cliente;");
			int code;
			String apellidoContacto;
			while (resultSet.next()) {
				code = resultSet.getInt("codigo_cliente");
				apellidoContacto = resultSet.getString("apellido_contacto").trim();
				System.out.println("Codigo Cliente : " + code + " Apellido contacto : " + apellidoContacto);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	public void consultaPorIndexColumna() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT codigo_cliente, nombre_cliente, nombre_contacto, apellido_contacto FROM cliente;");

			int code;
			String apellidoContacto;
			while (resultSet.next()) {
				code = resultSet.getInt(1); // Sí, empieza en 1 la primera columna
				apellidoContacto = resultSet.getString(4).trim();
				System.out.println("Codigo Cliente : " + code + " Apellido contacto : " + apellidoContacto);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	public void consultaParamPorIdCliente(int idCliente) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT codigo_cliente, nombre_cliente, nombre_contacto, apellido_contacto FROM cliente  WHERE codigo_cliente = "
							+ idCliente + ";");

			int code;
			String apellidoContacto;
			while (resultSet.next()) {
				code = resultSet.getInt("codigo_cliente");
				apellidoContacto = resultSet.getString("apellido_contacto").trim();
				System.out.println("Codigo Cliente : " + code + " Apellido contacto : " + apellidoContacto);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	public void consultaParamPorNombreCliente(String nombreCliente) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT codigo_cliente, nombre_cliente, nombre_contacto, apellido_contacto FROM cliente  WHERE nombre_cliente = '"
							+ nombreCliente + "';");

			int code;
			String apellidoContacto;
			while (resultSet.next()) {
				code = resultSet.getInt("codigo_cliente");
				apellidoContacto = resultSet.getString("apellido_contacto").trim();
				System.out.println("Codigo Cliente : " + code + " Apellido contacto : " + apellidoContacto);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	public void consultaParamPorNombreClientePreparedStatment(String nombreCliente) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT codigo_cliente, nombre_cliente, nombre_contacto, apellido_contacto FROM cliente  WHERE nombre_cliente = ?");
			
			pstmt.setString(1, nombreCliente); // Sí, empieza en 1 el primer parametro, no 0
			ResultSet resultSet = pstmt.executeQuery();
			int code;
			String apellidoContacto;
			while (resultSet.next()) {
				code = resultSet.getInt("codigo_cliente");
				apellidoContacto = resultSet.getString("apellido_contacto").trim();
				System.out.println("Codigo Cliente : " + code + " Apellido contacto : " + apellidoContacto);
			}
			resultSet.close();
			pstmt.close();
			connection.close();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	public void actualizaLimiteCreditoPreparedStatment(String nombreCliente) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			PreparedStatement pstmt = connection.prepareStatement(
					"UPDATE cliente SET limite_credito = limite_credito + 1 WHERE nombre_cliente = ?");
			pstmt.setString(1, nombreCliente); // Sí, empieza en 1 el primer parametro, no 0

			// ResultSet resultSet = pstmt.executeQuery(); //da error
			int nFilas = pstmt.executeUpdate();
			System.out.println("Afectadas un total de " + nFilas + " filas por el update");

			pstmt.close();
			connection.close();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	public void actualizaLimiteCredito2vecesPreparedStatment(String nombreCliente) {
		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			PreparedStatement pstmt = connection.prepareStatement(
					"UPDATE cliente SET limite_credito = limite_credito + 1 WHERE nombre_cliente = ?");
			pstmt.setString(1, nombreCliente); // Sí, empieza en 1 el primer parametro, no 0
			int nFilas = pstmt.executeUpdate();
			if (1 == 1) {
				throw new Exception("aa");
			}
			nFilas = pstmt.executeUpdate();
			System.out.println("Afectadas un total de " + nFilas + " filas por el update");

			pstmt.close();
			connection.close();
		} catch (Exception exception) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(exception);
		}
	}

	public void actualizaLimiteCredito2vecesPreparedStatmentTransaction(String nombreCliente) {
		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(
					"UPDATE cliente SET limite_credito = limite_credito + 1 WHERE nombre_cliente = ?");
			pstmt.setString(1, nombreCliente); // Sí, empieza en 1 el primer parametro, no 0
			int nFilas = pstmt.executeUpdate();
			if (1 == 1) {
				throw new Exception("aa");
			}
			nFilas = pstmt.executeUpdate();
			System.out.println("Afectadas un total de " + nFilas + " filas por el update");
			connection.commit();
			pstmt.close();
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception exception) {
			try {
				connection.rollback();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(exception);
		}
	}
}
