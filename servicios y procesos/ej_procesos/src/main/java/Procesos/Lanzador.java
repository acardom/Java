package Procesos;

import java.util.Scanner;

public class Lanzador {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Pedir al usuario el número de instancias (máximo 10)
        System.out.print("Introduce el número de instancias (máximo 10): ");
        int numInstancias = scanner.nextInt();
        
        // Validar que el número de instancias esté entre 1 y 10
        if (numInstancias < 1 || numInstancias > 10) {
            System.out.println("El número de instancias debe estar entre 1 y 10.");
            return;
        }

        // Pedir al usuario cuántas cadenas generar por instancia solo una vez
        System.out.print("Introduce cuántas cadenas generar por instancia: ");
        int numCadenas = scanner.nextInt();

        // Lanzar las instancias del generador
        for (int i = 0; i < numInstancias; i++) {
            System.out.println("Instancia " + (i + 1) + ":");
            // Llamar al método main de Generar_cadenas
            Generar_cadenas.generarCadenas(numCadenas);
        }
        
        scanner.close();
    }
}
