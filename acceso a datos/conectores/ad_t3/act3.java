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

public class act3 {
    public static void main(String[] args) {
        // Configuración de la conexión a MySQL (PHPMyAdmin)
        String url = "jdbc:mysql://localhost:3306/jardineria"; // Cambiar por el nombre de tu base de datos
        String user = "root"; // Cambiar por tu usuario
        String password = ""; // Cambiar por tu contraseña

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa a la base de datos.");

            // Menú de selección
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione la consulta a ejecutar (7-12):");
            System.out.println("7. Empleados sin oficina y sin cliente asociado");
            System.out.println("8. Productos que nunca han aparecido en un pedido (solo nombre)");
            System.out.println("9. Productos que nunca han aparecido en un pedido (nombre, descripción, imagen)");
            System.out.println("10. Oficinas sin empleados que hayan vendido productos de gama Frutales");
            System.out.println("11. Clientes que han realizado algún pedido pero no han realizado ningún pago");
            System.out.println("12. Empleados sin clientes asociados y su jefe");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Para consumir el salto de línea que queda después de nextInt()

            // Variables para la consulta
            String query = null;
            PreparedStatement stmt = null;

            switch (opcion) {
                case 7 -> {
                    // Empleados sin oficina y sin cliente asociado
                    query = """
                        SELECT empleado.nombre, empleado.apellido1
                        FROM empleado
                        LEFT JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
                        LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
                        WHERE oficina.codigo_oficina IS NULL AND cliente.codigo_cliente IS NULL;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 8 -> {
                    // Productos que nunca han aparecido en un pedido (solo nombre)
                    query = """
                        SELECT producto.nombre
                        FROM producto
                        LEFT JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
                        WHERE detalle_pedido.codigo_producto IS NULL;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 9 -> {
                    // Productos que nunca han aparecido en un pedido (nombre, descripción, imagen)
                    query = """
                        SELECT producto.nombre, producto.descripcion, producto.imagen
                        FROM producto
                        LEFT JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
                        WHERE detalle_pedido.codigo_producto IS NULL;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 10 -> {
                    // Oficinas sin empleados que hayan vendido productos de gama Frutales
                    query = """
                        SELECT oficina.linea_direccion1, oficina.linea_direccion2
                        FROM oficina
                        LEFT JOIN empleado ON oficina.codigo_oficina = empleado.codigo_oficina
                        LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
                        LEFT JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                        LEFT JOIN detalle_pedido ON pedido.codigo_pedido = detalle_pedido.codigo_pedido
                        LEFT JOIN producto ON detalle_pedido.codigo_producto = producto.codigo_producto
                        WHERE producto.gama = 'Frutales' AND empleado.codigo_empleado IS NULL;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 11 -> {
                    // Clientes que han realizado algún pedido pero no han realizado ningún pago
                    query = """
                        SELECT cliente.nombre_cliente
                        FROM cliente
                        LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
                        INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                        WHERE pago.codigo_cliente IS NULL;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 12 -> {
                    // Empleados sin clientes asociados y su jefe
                    query = """
                        SELECT empleado.nombre, empleado.apellido1, jefe.nombre AS nombre_jefe, jefe.apellido1 AS apellido_jefe
                        FROM empleado
                        LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
                        LEFT JOIN empleado jefe ON empleado.codigo_jefe = jefe.codigo_empleado
                        WHERE cliente.codigo_cliente IS NULL;
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
