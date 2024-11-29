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

public class act2_solicitando {
    public static void main(String[] args) {
        // Configuración de la conexión a MySQL (PHPMyAdmin)
        String url = "jdbc:mysql://localhost:3306/jardineria"; // Cambiar por el nombre de tu base de datos
        String user = "root"; // Cambiar por tu usuario
        String password = ""; // Cambiar por tu contraseña

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa a la base de datos.");

            // Menú de selección
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione la consulta a ejecutar (5-10):");
            System.out.println("5. Clientes sin pagos y sus representantes");
            System.out.println("6. Dirección de oficinas con clientes en una ciudad específica");
            System.out.println("7. Clientes y sus representantes");
            System.out.println("8. Nombre de empleados y jefes");
            System.out.println("9. Clientes con pedidos no entregados a tiempo");
            System.out.println("10. Gamas de productos compradas por cada cliente");
            int opcion = scanner.nextInt();

            // Variables para parámetros de entrada
            String query = null;
            PreparedStatement stmt = null;

            switch (opcion) {
                case 5 -> {
                    query = """
                        SELECT cliente.nombre_cliente AS nombre_cliente, 
                               empleado.nombre AS nombre_representante, 
                               empleado.apellido1 AS apellido_representante, 
                               oficina.ciudad
                        FROM cliente
                        INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
                        LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
                        INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
                        WHERE pago.codigo_cliente IS NULL 
                        AND cliente.ciudad = ? 
                        AND empleado.nombre = ?;
                    """;
                    System.out.println("Ingrese la ciudad del cliente:");
                    String ciudad = scanner.next();
                    System.out.println("Ingrese el nombre del representante:");
                    String nombreRepresentante = scanner.next();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, ciudad);
                    stmt.setString(2, nombreRepresentante);
                }
                case 6 -> {
                    query = """
                        SELECT DISTINCT oficina.linea_direccion1, oficina.linea_direccion2
                        FROM oficina
                        INNER JOIN cliente ON oficina.codigo_oficina = cliente.codigo_oficina
                        WHERE cliente.ciudad = ? 
                        AND (SELECT COUNT(*) FROM cliente c WHERE c.codigo_oficina = oficina.codigo_oficina) >= ?;
                    """;
                    System.out.println("Ingrese la ciudad:");
                    String ciudad = scanner.next();
                    System.out.println("Ingrese el número mínimo de clientes:");
                    int minClientes = scanner.nextInt();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, ciudad);
                    stmt.setInt(2, minClientes);
                }
                case 7 -> {
                    query = """
                        SELECT cliente.nombre_cliente AS nombre_cliente, 
                               empleado.nombre AS nombre_representante, 
                               empleado.apellido1 AS apellido_representante, 
                               oficina.ciudad
                        FROM cliente
                        INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
                        INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
                        WHERE empleado.nombre = ? 
                        AND oficina.ciudad = ?;
                    """;
                    System.out.println("Ingrese el nombre del representante:");
                    String nombreRepresentante = scanner.next();
                    System.out.println("Ingrese la ciudad de la oficina:");
                    String ciudadOficina = scanner.next();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, nombreRepresentante);
                    stmt.setString(2, ciudadOficina);
                }
                case 8 -> {
                    query = """
                        SELECT e.nombre AS nombre_empleado, e.apellido1 AS apellido_empleado, 
                               jefe.nombre AS nombre_jefe, jefe.apellido1 AS apellido_jefe
                        FROM empleado e
                        INNER JOIN empleado jefe ON e.codigo_jefe = jefe.codigo_empleado
                        WHERE e.nombre = ? AND jefe.apellido1 = ?;
                    """;
                    System.out.println("Ingrese el nombre del empleado:");
                    String nombreEmpleado = scanner.next();
                    System.out.println("Ingrese el apellido del jefe:");
                    String apellidoJefe = scanner.next();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, nombreEmpleado);
                    stmt.setString(2, apellidoJefe);
                }
                case 9 -> {
                    query = """
                        SELECT cliente.nombre_cliente
                        FROM cliente
                        INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                        WHERE pedido.fecha_entrega > pedido.fecha_esperada 
                        AND cliente.ciudad = ?
                        AND pedido.fecha_entrega BETWEEN ? AND ?;
                    """;
                    System.out.println("Ingrese la ciudad del cliente:");
                    String ciudad = scanner.next();
                    System.out.println("Ingrese la fecha de inicio (YYYY-MM-DD):");
                    String fechaInicio = scanner.next();
                    System.out.println("Ingrese la fecha de fin (YYYY-MM-DD):");
                    String fechaFin = scanner.next();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, ciudad);
                    stmt.setString(2, fechaInicio);
                    stmt.setString(3, fechaFin);
                }
                case 10 -> {
                    query = """
                        SELECT cliente.nombre_cliente AS nombre_cliente, 
                               GROUP_CONCAT(DISTINCT producto.gama) AS gamas_compradas
                        FROM cliente
                        INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                        INNER JOIN detalle_pedido ON pedido.codigo_pedido = detalle_pedido.codigo_pedido
                        INNER JOIN producto ON detalle_pedido.codigo_producto = producto.codigo_producto
                        WHERE cliente.nombre_cliente = ? 
                        AND pedido.fecha_pedido BETWEEN ? AND ?
                        GROUP BY cliente.codigo_cliente;
                    """;
                    System.out.println("Ingrese el nombre del cliente:");
                    String nombreCliente = scanner.next();
                    System.out.println("Ingrese la fecha de inicio del pedido (YYYY-MM-DD):");
                    String fechaInicio = scanner.next();
                    System.out.println("Ingrese la fecha de fin del pedido (YYYY-MM-DD):");
                    String fechaFin = scanner.next();
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, nombreCliente);
                    stmt.setString(2, fechaInicio);
                    stmt.setString(3, fechaFin);
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
