/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad_t3;

/**
 *
 * @author carde
 */

import java.sql.*;
import java.util.Scanner;

public class act1 {
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
            System.out.println("11. Pedidos rechazados en 2009");
            System.out.println("12. Pedidos entregados en enero de cualquier año");
            System.out.println("13. Pagos realizados en 2008 mediante Paypal");
            System.out.println("14. Formas de pago únicas");
            System.out.println("15. Productos de gama 'Ornamentales' con más de 100 unidades en stock");
            System.out.println("16. Clientes de Madrid con representantes 11 o 30");
            int opcion = scanner.nextInt();

            // Consulta correspondiente
            String query = switch (opcion) {
                case 11 -> """
                    SELECT *
                    FROM pedido
                    WHERE estado = 'Rechazado' AND fecha_pedido BETWEEN '2009-01-01' AND '2009-12-31';
                """;
                case 12 -> """
                    SELECT *
                    FROM pedido
                    WHERE MONTH(fecha_entrega) = 1;
                """;
                case 13 -> """
                    SELECT *
                    FROM pago
                    WHERE forma_pago = 'Paypal' AND YEAR(fecha_pago) = 2008
                    ORDER BY cantidad DESC;
                """;
                case 14 -> """
                    SELECT DISTINCT forma_pago
                    FROM pago;
                """;
                case 15 -> """
                    SELECT *
                    FROM producto
                    WHERE gama = 'Ornamentales' AND stock > 100
                    ORDER BY precio_venta DESC;
                """;
                case 16 -> """
                    SELECT *
                    FROM cliente
                    WHERE ciudad = 'Madrid' AND codigo_empleado_representante IN (11, 30);
                """;
                default -> {
                    System.out.println("Opción no válida.");
                    yield null;
                }
            };

            if (query != null) {
                try (PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

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
