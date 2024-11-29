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

public class act6 {
    public static void main(String[] args) {
        // Configuración de la conexión a MySQL (PHPMyAdmin)
        String url = "jdbc:mysql://localhost:3306/jardineria"; // Cambiar por el nombre de tu base de datos
        String user = "root"; // Cambiar por tu usuario
        String password = ""; // Cambiar por tu contraseña

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa a la base de datos.");

            // Menú de selección
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione la consulta a ejecutar (2-7):");
            System.out.println("2. Listado de clientes y el total pagado por cada uno");
            System.out.println("3. Clientes que hayan hecho pedidos en 2008");
            System.out.println("4. Clientes sin pagos con información de su representante de ventas");
            System.out.println("5. Listado de clientes con su representante de ventas y la ciudad de la oficina");
            System.out.println("6. Empleados que no son representantes de ventas de ningún cliente");
            System.out.println("7. Ciudades de oficinas con el número de empleados");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Para consumir el salto de línea que queda después de nextInt()

            // Variables para la consulta
            String query = null;
            PreparedStatement stmt = null;

            switch (opcion) {
                case 2 -> {
                    // Listado con los nombres de los clientes y el total pagado por cada uno de ellos
                    query = """
                        SELECT cliente.nombre_cliente, COALESCE(SUM(pago.total), 0) AS total_pagado
                        FROM cliente
                        LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
                        GROUP BY cliente.codigo_cliente;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 3 -> {
                    // Solicitar el año y la ciudad al usuario
                    System.out.println("Introduce el año de los pedidos (por ejemplo, 2008):");
                    int año = scanner.nextInt();
                    scanner.nextLine(); // Para consumir el salto de línea
                    

                    // Consulta 3 modificada para recibir año y ciudad
                    query = """
                        SELECT DISTINCT cliente.nombre_cliente
                        FROM cliente
                        INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
                        INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
                        INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
                        WHERE YEAR(pedido.fecha_pedido) = ? 
                        ORDER BY cliente.nombre_cliente ASC;
                    """;
                    stmt = conn.prepareStatement(query);
                    stmt.setInt(1, año); // Establecer el parámetro del año
                }
                case 4 -> {
                    // Clientes sin pagos con información de su representante de ventas
                    query = """
                        SELECT cliente.nombre_cliente, empleado.nombre AS nombre_representante, 
                               empleado.apellido1 AS apellido_representante, oficina.telefono
                        FROM cliente
                        INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
                        INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
                        LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
                        WHERE pago.codigo_cliente IS NULL;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 5 -> {
                    // Listado de clientes con su representante de ventas y la ciudad de la oficina
                    query = """
                        SELECT cliente.nombre_cliente, empleado.nombre AS nombre_representante, 
                               empleado.apellido1 AS apellido_representante, oficina.ciudad
                        FROM cliente
                        INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
                        INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 6 -> {
                    // Empleados que no son representantes de ventas de ningún cliente
                    query = """
                        SELECT empleado.nombre, empleado.apellido1, empleado.apellido2, 
                               empleado.puesto, oficina.telefono
                        FROM empleado
                        LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
                        INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
                        WHERE cliente.codigo_cliente IS NULL;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 7 -> {
                    // Ciudades de oficinas con el número de empleados
                    query = """
                        SELECT oficina.ciudad, COUNT(empleado.codigo_empleado) AS total_empleados
                        FROM oficina
                        LEFT JOIN empleado ON oficina.codigo_oficina = empleado.codigo_oficina
                        GROUP BY oficina.ciudad;
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


