package ad_t3;

import java.sql.*;
import java.util.Scanner;

public class act1_solicitando {
    public static void main(String[] args) {
        // Configuración de la conexión a MySQL (PHPMyAdmin)
        String url = "jdbc:mysql://localhost:3306/jardineria"; // Cambiar por el nombre de tu base de datos
        String user = "root"; // Cambiar por tu usuario
        String password = ""; // Cambiar por tu contraseña

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa a la base de datos.");

            // Menú de selección
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione la consulta a ejecutar (11-16):");
            System.out.println("11. Pedidos rechazados entre dos fechas");
            System.out.println("12. Pedidos entregados en un mes específico de cualquier año");
            System.out.println("13. Pagos realizados en un año específico mediante una forma de pago");
            System.out.println("14. Formas de pago únicas (sin parámetros)");
            System.out.println("15. Productos de una gama específica con más de X unidades en stock");
            System.out.println("16. Clientes de una ciudad específica con representantes específicos");
            int opcion = scanner.nextInt();

            // Variables para parámetros de entrada
            String query = null;
            PreparedStatement stmt = null;

            switch (opcion) {
                case 11 -> {
                    query = """
                        SELECT *
                        FROM pedido
                        WHERE estado = 'Rechazado' AND fecha_pedido BETWEEN ? AND ?;
                    """;
                    System.out.println("Ingrese la fecha de inicio (YYYY-MM-DD):");
                    String fechaInicio = scanner.next();
                    System.out.println("Ingrese la fecha de fin (YYYY-MM-DD):");
                    String fechaFin = scanner.next();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, fechaInicio);
                    stmt.setString(2, fechaFin);
                }
                case 12 -> {
                    query = """
                        SELECT *
                        FROM pedido
                        WHERE MONTH(fecha_entrega) = ? AND YEAR(fecha_entrega) = ?;
                    """;
                    System.out.println("Ingrese el número del mes (1-12):");
                    int mes = scanner.nextInt();
                    System.out.println("Ingrese el año:");
                    int year = scanner.nextInt();
                    stmt = conn.prepareStatement(query);
                    stmt.setInt(1, mes);
                    stmt.setInt(2, year);
                }
                case 13 -> {
                    query = """
                        SELECT *
                        FROM pago
                        WHERE forma_pago = ? AND YEAR(fecha_pago) = ?
                        ORDER BY cantidad DESC;
                    """;
                    System.out.println("Ingrese la forma de pago (e.g., 'Paypal'):");
                    String formaPago = scanner.next();
                    System.out.println("Ingrese el año:");
                    int year = scanner.nextInt();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, formaPago);
                    stmt.setInt(2, year);
                }
                case 14 -> {
                    query = """
                        SELECT DISTINCT forma_pago
                        FROM pago;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 15 -> {
                    query = """
                        SELECT *
                        FROM producto
                        WHERE gama = ? AND stock > ?
                        ORDER BY precio_venta DESC;
                    """;
                    System.out.println("Ingrese la gama de productos (e.g., 'Ornamentales'):");
                    String gama = scanner.next();
                    System.out.println("Ingrese el stock mínimo:");
                    int stockMin = scanner.nextInt();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, gama);
                    stmt.setInt(2, stockMin);
                }
                case 16 -> {
                    query = """
                        SELECT *
                        FROM cliente
                        WHERE ciudad = ? AND codigo_empleado_representante IN (?, ?);
                    """;
                    System.out.println("Ingrese la ciudad (e.g., 'Madrid'):");
                    String ciudad = scanner.next();
                    System.out.println("Ingrese el primer código de representante:");
                    int rep1 = scanner.nextInt();
                    System.out.println("Ingrese el segundo código de representante:");
                    int rep2 = scanner.nextInt();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, ciudad);
                    stmt.setInt(2, rep1);
                    stmt.setInt(3, rep2);
                }
                default -> System.out.println("Opción no válida.");
            }

            // Ejecución de la consulta y resultados
            if (stmt != null) {
                try (ResultSet rs = stmt.executeQuery()) {
                    // Obtener metadatos para imprimir nombres de columnas
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    // Imprimir encabezados
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(metaData.getColumnName(i) + "\t");
                    }
                    System.out.println();

                    // Imprimir filas de resultados
                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(rs.getString(i) + "\t");
                        }
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la conexión o consulta: " + e.getMessage());
        }
    }
}
