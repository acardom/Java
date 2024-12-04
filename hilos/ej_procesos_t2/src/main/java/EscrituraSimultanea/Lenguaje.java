/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EscrituraSimultanea;

/**
 *
 * @author carde
 */
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;

public class Lenguaje {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Uso: java -jar lenguaje <NombreInstancia> <Cantidad> <NombreArchivo>");
            return;
        }

        String nombre = args[0];
        int cantidad;
        String nombreArchivo = args[2];

        try {
            cantidad = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("El segundo parámetro debe ser un número entero.");
            return;
        }

        Random random = new Random();
        try (RandomAccessFile archivo = new RandomAccessFile(nombreArchivo, "rw");
             FileChannel canal = archivo.getChannel();
             FileLock bloqueo = canal.lock()) {

            archivo.seek(archivo.length()); // Posicionarse al final del archivo
            for (int i = 0; i < cantidad; i++) {
                String palabraAleatoria = generarPalabraAleatoria(random, 5 + random.nextInt(6));
                archivo.writeBytes("Escrito por " + nombre + ": " + palabraAleatoria + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    private static String generarPalabraAleatoria(Random random, int longitud) {
        StringBuilder palabra = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            char letra = (char) ('a' + random.nextInt(26));
            palabra.append(letra);
        }
        return palabra.toString();
    }
}
