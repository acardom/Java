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

public class Nivel3 {
    public static void main(String[] args) throws InterruptedException {
        int numPajarosPorEspecie = 10;
        Semaphore semaforoGeneral = new Semaphore(1);

        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < numPajarosPorEspecie; i++) {
            hilos.add(new Thread(new Pajaro("Periquito", i + 1, semaforoGeneral)));
            hilos.add(new Thread(new Pajaro("Loro", i + 1, semaforoGeneral)));
            hilos.add(new Thread(new Pajaro("Gorrión", i + 1, semaforoGeneral)));
        }

        for (Thread hilo : hilos) {
            hilo.start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Todos los pájaros muy educados han terminado de cantar.");
    }
}

