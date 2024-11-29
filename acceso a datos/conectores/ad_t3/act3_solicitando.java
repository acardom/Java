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

public class act3_solicitando {
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

            // Variables para la consulta y parámetros de entrada
            String query = null;
            PreparedStatement stmt = null;

            switch (opcion) {
                case 7 -> {
                    // Empleados sin oficina y sin cliente asociado
                    System.out.println("Ingrese ciudad de oficina (dejar en blanco para no filtrar):");
                    String ciudadOficina = scanner.nextLine();
                    System.out.println("Ingrese ciudad de cliente (dejar en blanco para no filtrar):");
                    String ciudadCliente = scanner.nextLine();

                    query = """
                        SELECT empleado.nombre, empleado.apellido1
                        FROM empleado
                        LEFT JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
                        LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
                        WHERE (oficina.codigo_oficina IS NULL OR oficina.ciudad = ?)
                        AND (cliente.codigo_cliente IS NULL OR cliente.ciudad = ?);
                    """;

                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, ciudadOficina.isEmpty() ? "%" : ciudadOficina);
                    stmt.setString(2, ciudadCliente.isEmpty() ? "%" : ciudadCliente);
                }
                case 8 -> {
                    // Productos que nunca han aparecido en un pedido (solo nombre)
                    System.out.println("Ingrese la gama de productos (dejar en blanco para no filtrar):");
                    String gamaProducto = scanner.nextLine();

                    query = """
                        SELECT producto.nombre
                        FROM producto
                        LEFT JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
                        WHERE detalle_pedido.codigo_producto IS NULL AND (producto.gama = ? OR ? = '');
                    """;

                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, gamaProducto.isEmpty() ? "%" : gamaProducto);
                    stmt.setString(2, gamaProducto.isEmpty() ? "%" : gamaProducto);
                }
                case 9 -> {
                    // Productos que nunca han aparecido en un pedido (nombre, descripción, imagen)
                    System.out.println("Ingrese la gama de productos (dejar en blanco para no filtrar):");
                    String gamaProducto = scanner.nextLine();

                    query = """
                        SELECT producto.nombre, producto.descripcion, producto.imagen
                        FROM producto
                        LEFT JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
                        WHERE detalle_pedido.codigo_producto IS NULL AND (producto.gama = ? OR ? = '');
                    """;

                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, gamaProducto.isEmpty() ? "%" : gamaProducto);
                    stmt.setString(2, gamaProducto.isEmpty() ? "%" : gamaProducto);
                }
                case 10 -> {
                    // Oficinas sin empleados que hayan vendido productos de gama Frutales
                    System.out.println("Ingrese la ciudad de la oficina:");
                    String ciudadOficina = scanner.nextLine();

                    query = """
                        SELECT oficina.linea_direccion1, oficina.linea_direccion2
                        FROM oficina
                        LEFT JOIN empleado ON oficina.codigo_oficina = empleado.codigo_oficina
                        LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
                        LEFT JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                        LEFT JOIN detalle_pedido ON pedido.codigo_pedido = detalle_pedido.codigo_pedido
                        LEFT JOIN producto ON detalle_pedido.codigo_producto = producto.codigo_producto
                        WHERE producto.gama = 'Frutales' AND oficina.ciudad = ? AND empleado.codigo_empleado IS NULL;
                    """;

                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, ciudadOficina);
                }
                case 11 -> {
                    // Clientes que han realizado algún pedido pero no han realizado ningún pago
                    System.out.println("Ingrese el estado del pedido (ej. 'Pendiente'):");
                    String estadoPedido = scanner.nextLine();
                    System.out.println("Ingrese la fecha de inicio (YYYY-MM-DD):");
                    String fechaInicio = scanner.nextLine();
                    System.out.println("Ingrese la fecha de fin (YYYY-MM-DD):");
                    String fechaFin = scanner.nextLine();

                    query = """
                        SELECT cliente.nombre_cliente
                        FROM cliente
                        LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
                        INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                        WHERE pago.codigo_cliente IS NULL AND pedido.estado = ? 
                        AND pedido.fecha_pedido BETWEEN ? AND ?;
                    """;

                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, estadoPedido);
                    stmt.setString(2, fechaInicio);
                    stmt.setString(3, fechaFin);
                }
                case 12 -> {
                    // Empleados sin clientes asociados y su jefe
                    System.out.println("Ingrese la ciudad del empleado (dejar en blanco para no filtrar):");
                    String ciudadEmpleado = scanner.nextLine();
                    System.out.println("Ingrese el nombre del jefe (dejar en blanco para no filtrar):");
                    String nombreJefe = scanner.nextLine();

                    query = """
                        SELECT empleado.nombre, empleado.apellido1, jefe.nombre AS nombre_jefe, jefe.apellido1 AS apellido_jefe
                        FROM empleado
                        LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
                        LEFT JOIN empleado jefe ON empleado.codigo_jefe = jefe.codigo_empleado
                        WHERE cliente.codigo_cliente IS NULL 
                        AND (empleado.ciudad = ? OR ? = '')
                        AND (jefe.nombre = ? OR ? = '');
                    """;

                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, ciudadEmpleado.isEmpty() ? "%" : ciudadEmpleado);
                    stmt.setString(2, ciudadEmpleado.isEmpty() ? "%" : ciudadEmpleado);
                    stmt.setString(3, nombreJefe.isEmpty() ? "%" : nombreJefe);
                    stmt.setString(4, nombreJefe.isEmpty() ? "%" : nombreJefe);
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
