/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EscrituraSimultanea;

/**
 *
 * @author carde
 */
import java.io.IOException;

public class Colaborar {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java -jar colaborar <NombreArchivo>");
            return;
        }

        String nombreArchivo = args[0];
        int numInstancias = 10;

        for (int i = 1; i <= numInstancias; i++) {
            int cantidadPalabras = i * 10; // 10, 20, 30, ...
            String nombreInstancia = "Instancia" + i;

            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-jar", "lenguaje.jar", nombreInstancia, String.valueOf(cantidadPalabras), nombreArchivo);

            try {
                Process proceso = pb.start();
                proceso.waitFor(); // Esperar a que termine la instancia
            } catch (IOException | InterruptedException e) {
                System.err.println("Error al lanzar la instancia " + nombreInstancia + ": " + e.getMessage());
            }
        }
    }
}
