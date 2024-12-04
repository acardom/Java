/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pajaros;

/**
 *
 * @author carde
 */
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Nivel4 {
    public static void main(String[] args) throws InterruptedException {
        int numPajarosPorEspecie = 10;
        Semaphore semaforoGeneral = new Semaphore(1);
        LinkedBlockingQueue<Pajaro> colaEspera = new LinkedBlockingQueue<>();

        for (int i = 0; i < numPajarosPorEspecie; i++) {
            colaEspera.add(new Pajaro("Periquito", i + 1, semaforoGeneral));
            colaEspera.add(new Pajaro("Loro", i + 1, semaforoGeneral));
            colaEspera.add(new Pajaro("Gorrión", i + 1, semaforoGeneral));
        }

        while (!colaEspera.isEmpty()) {
            Thread hilo = new Thread(colaEspera.poll());
            hilo.start();
            hilo.join();
        }

        System.out.println("Todos los pájaros con adiestrador han terminado de cantar.");
    }
}

