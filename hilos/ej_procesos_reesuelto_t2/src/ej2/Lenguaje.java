/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ej2;

/**
 *
 * @author carde
 */
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Random;

public class Lenguaje {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Uso: java -jar lenguaje <Nombre> <NumConjuntos> <Fichero>");
            return;
        }
        String nombre = args[0];
        int numConjuntos;
        String fichero = args[2];
        try {
            numConjuntos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("El segundo parámetro debe ser un número.");
            return;
        }
        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
// Bloqueo del archivo
            synchronized (Lenguaje.class) {
                raf.seek(raf.length()); // Posicionarse al final del fichero
                raf.writeBytes("Escrito por " + nombre + "\n");
                Random random = new Random();
                for (int i = 0; i < numConjuntos; i++) {
                    String randomString = generateRandomString(random, 10); // Cadena de 10 letras 
                    raf.writeBytes(randomString + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al acceder al fichero: " + e.getMessage());
        }
    }

    private static String generateRandomString(Random random, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + random.nextInt(26)); // Letras minúsculas
            sb.append(c);
        }
        return sb.toString();
    }
}

