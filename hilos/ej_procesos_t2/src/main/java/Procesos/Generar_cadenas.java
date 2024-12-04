/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Procesos;
import java.util.Random;
import java.util.Scanner;


public class Generar_cadenas {
    public static void main(String[] args) {
        // Si hay argumentos, se espera que sea el número de cadenas
        if (args.length > 0) {
            int cantidad = Integer.parseInt(args[0]);
            generarCadenas(cantidad);
        } else {
            // Si no hay argumentos, solicitar al usuario
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce la cantidad de cadenas a generar: ");
            int cantidad = scanner.nextInt();
            generarCadenas(cantidad);
            scanner.close();
        }
    }

    // Método para generar varias cadenas
    public static void generarCadenas(int cantidad) {
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            String cadena = generarCadena(random);
            System.out.println(cadena);
        }
    }

    // Método para generar una cadena de 10 caracteres aleatorios
    private static String generarCadena(Random random) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            // Generar un carácter aleatorio del alfabeto
            char letra = (char) (random.nextBoolean() ? 
                                 random.nextInt(26) + 'a' : // Minúscula
                                 random.nextInt(26) + 'A'); // Mayúscula
            sb.append(letra);
        }
        return sb.toString();
    }
}
