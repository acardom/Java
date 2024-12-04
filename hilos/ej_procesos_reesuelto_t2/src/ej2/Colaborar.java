/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ej2;

/**
 *
 * @author carde
 */
import java.io.IOException;
public class Colaborar {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java -jar colaborar <Fichero>");
            return;
        }
        String fichero = args[0];
        int numInstancias = 10;
        for (int i = 1; i <= numInstancias; i++) {
            int numConjuntos = i * 10; // Generar 10, 20, 30, ..., 100 conjuntos
            String nombre = "Instancia" + i;
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-jar", "lenguaje.jar", nombre, String.valueOf(numConjuntos), fichero
            );
            try {
                pb.start();
            } catch (IOException e) {
                System.err.println("Error al lanzar la instancia: " + nombre + ", " + e.getMessage());
            }
        }
    }
}