/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ej3;

/**
 *
 * @author carde
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pajaro implements Runnable {

    private final String raza;
    private final int id;
    private final Lock lock = new ReentrantLock();
    private static final int ESPERAR_CANTAR = 5000; // 5 segundos
    private static final int DURACION_CANTAR = 2000; // 2 segundos

    public Pajaro(String raza, int id) {
        this.raza = raza;
        this.id = id;
    }

    public void cantar() throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(DURACION_CANTAR); // Simula cantar
            System.out.println("Pájaro " + id + " de raza " + raza + " está cantando.");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(ESPERAR_CANTAR); // Espera antes de intentar cantar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Lock getLock() {
        return lock;
    }
}
