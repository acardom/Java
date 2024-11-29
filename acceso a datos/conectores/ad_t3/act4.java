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

public class act4 {
    public static void main(String[] args) {
        // Configuración de la conexión a MySQL (PHPMyAdmin)
        String url = "jdbc:mysql://localhost:3306/jardineria"; // Cambiar por el nombre de tu base de datos
        String user = "root"; // Cambiar por tu usuario
        String password = ""; // Cambiar por tu contraseña

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa a la base de datos.");

            // Menú de selección
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione la consulta a ejecutar (13-18):");
            System.out.println("13. Suma de la cantidad total de productos en cada pedido");
            System.out.println("14. Los 20 productos más vendidos");
            System.out.println("15. Facturación total de la empresa");
            System.out.println("16. Facturación agrupada por código de producto");
            System.out.println("17. Facturación agrupada por código de producto filtrada por 'OR%'");
            System.out.println("18. Productos con ventas mayores a 3000 euros");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Para consumir el salto de línea que queda después de nextInt()

            // Variables para la consulta
            String query = null;
            PreparedStatement stmt = null;

            switch (opcion) {
                case 13 -> {
                    // Suma de la cantidad total de productos en cada pedido
                    query = """
                        SELECT codigo_pedido, SUM(cantidad) AS total_cantidad
                        FROM detalle_pedido
                        GROUP BY codigo_pedido;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 14 -> {
                    // Los 20 productos más vendidos
                    query = """
                        SELECT producto.nombre, SUM(detalle_pedido.cantidad) AS total_unidades
                        FROM producto
                        INNER JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
                        GROUP BY producto.codigo_producto
                        ORDER BY total_unidades DESC
                        LIMIT 20;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 15 -> {
                    // Facturación total de la empresa
                    query = """
                        SELECT SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) AS base_imponible,
                               SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 0.21) AS iva,
                               SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 1.21) AS total_facturado
                        FROM detalle_pedido;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 16 -> {
                    // Facturación agrupada por código de producto
                    query = """
                        SELECT detalle_pedido.codigo_producto,
                               SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) AS base_imponible,
                               SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 0.21) AS iva,
                               SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 1.21) AS total_facturado
                        FROM detalle_pedido
                        GROUP BY detalle_pedido.codigo_producto;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 17 -> {
                    // Facturación agrupada por código de producto filtrada por 'OR%'
                    query = """
                        SELECT detalle_pedido.codigo_producto,
                               SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) AS base_imponible,
                               SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 0.21) AS iva,
                               SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 1.21) AS total_facturado
                        FROM detalle_pedido
                        WHERE detalle_pedido.codigo_producto LIKE 'OR%'
                        GROUP BY detalle_pedido.codigo_producto;
                    """;
                    stmt = conn.prepareStatement(query);
                }
                case 18 -> {
                    // Solicitar monto mínimo y fecha de inicio al usuario
                    System.out.println("Introduce el monto mínimo de ventas (en euros):");
                    double montoMinimo = scanner.nextDouble();
                    scanner.nextLine(); // Para consumir el salto de línea
                    System.out.println("Introduce la fecha de inicio (en formato 'YYYY-MM-DD'):");
                    String fechaInicio = scanner.nextLine();

                    // Consulta 18 modificada para recibir monto mínimo y fecha de inicio
                    query = """
                        SELECT producto.nombre,
                               SUM(detalle_pedido.cantidad) AS total_unidades_vendidas,
                               SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) AS total_facturado,
                               SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 1.21) AS total_facturado_con_iva
                        FROM producto
                        INNER JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
                        INNER JOIN pedido ON detalle_pedido.codigo_pedido = pedido.codigo_pedido
                        WHERE pedido.fecha_pedido >= ?
                        GROUP BY producto.codigo_producto
                        HAVING SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) > ?
                    """;
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, fechaInicio); // Establecer la fecha de inicio
                    stmt.setDouble(2, montoMinimo); // Establecer el monto mínimo de ventas
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
