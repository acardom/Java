/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pajaros;

/**
 *
 * @author carde
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Nivel1 {
    public static void main(String[] args) throws InterruptedException {
        int numPajaros = 30;
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < numPajaros; i++) {
            Pajaro pajaro = new Pajaro("NoEducado", i + 1, new Semaphore(Integer.MAX_VALUE));
            Thread hilo = new Thread(pajaro);
            hilos.add(hilo);
            hilo.start();
        }

        // Esperar a que todos los hilos terminen
        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Todos los pájaros no educados han terminado de cantar.");
    }
}
