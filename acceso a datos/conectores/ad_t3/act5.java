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

public class act5 {
    public static void main(String[] args) {
        // Configuración de la conexión a MySQL (PHPMyAdmin)
        String url = "jdbc:mysql://localhost:3306/jardineria"; // Cambiar por el nombre de tu base de datos
        String user = "root"; // Cambiar por tu usuario
        String password = ""; // Cambiar por tu contraseña

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa a la base de datos.");

            // Menú de selección
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione la consulta a ejecutar (16-21):");
            System.out.println("16. Oficinas sin empleados representantes de ventas de clientes que han comprado productos de la gama Frutales");
            System.out.println("17. Clientes que han realizado pedidos pero no pagos");
            System.out.println("18. Clientes sin pagos");
            System.out.println("19. Clientes con pagos");
            System.out.println("20. Productos que nunca han aparecido en un pedido");
            System.out.println("21. Productos que han aparecido en un pedido");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Para consumir el salto de línea que queda después de nextInt()

            // Variables para la consulta
            String query = null;
            PreparedStatement stmt = null;

            switch (opcion) {
                case 16 -> {
                    // Oficinas sin empleados representantes de ventas de clientes que han comprado productos de la gama Frutales
                    query = """
                        SELECT oficina.linea_direccion1, oficina.linea_direccion2
                        FROM oficina
                        WHERE codigo_oficina NOT IN (
                            SELECT empleado.codigo_oficina
                            FROM empleado
                            INNER JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
                            INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                            INNER JOIN detalle_pedido ON pedido.codigo_pedido = detalle_pedido.codigo_pedido
                            INNER JOIN producto ON detalle_pedido.codigo_producto = producto.codigo_producto
                            WHERE producto.gama = 'Frutales'
                        );
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 17 -> {
                    // Solicitar el año y la ciudad al usuario
                    System.out.println("Introduce el año de los pedidos (por ejemplo, 2008):");
                    int año = scanner.nextInt();
                    scanner.nextLine(); // Para consumir el salto de línea
                    System.out.println("Introduce la ciudad:");
                    String ciudad = scanner.nextLine();

                    // Consulta 17 modificada para recibir año y ciudad
                    query = """
                        SELECT cliente.nombre_cliente
                        FROM cliente
                        INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                        LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
                        WHERE pago.codigo_cliente IS NULL
                        AND YEAR(pedido.fecha_pedido) = ?
                        AND cliente.ciudad = ?
                    """;
                    stmt = conn.prepareStatement(query);
                    stmt.setInt(1, año); // Establecer el parámetro del año
                    stmt.setString(2, ciudad); // Establecer el parámetro de la ciudad
                }
                case 18 -> {
                    // Clientes sin pagos
                    query = """
                        SELECT nombre_cliente
                        FROM cliente
                        WHERE NOT EXISTS (SELECT 1 FROM pago WHERE cliente.codigo_cliente = pago.codigo_cliente);
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 19 -> {
                    // Clientes con pagos
                    query = """
                        SELECT nombre_cliente
                        FROM cliente
                        WHERE EXISTS (SELECT 1 FROM pago WHERE cliente.codigo_cliente = pago.codigo_cliente);
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 20 -> {
                    // Productos que nunca han aparecido en un pedido
                    query = """
                        SELECT nombre
                        FROM producto
                        WHERE NOT EXISTS (SELECT 1 FROM detalle_pedido WHERE producto.codigo_producto = detalle_pedido.codigo_producto);
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 21 -> {
                    // Productos que han aparecido en un pedido
                    query = """
                        SELECT nombre
                        FROM producto
                        WHERE EXISTS (SELECT 1 FROM detalle_pedido WHERE producto.codigo_producto = detalle_pedido.codigo_producto);
                    """;
                    stmt = conn.prepareStatement(query);
                }
                default -> {
                    System.out.println("Opción no válida.");
                    return;
                }
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

