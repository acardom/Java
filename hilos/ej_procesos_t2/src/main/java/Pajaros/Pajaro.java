/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pajaros;

/**
 *
 * @author carde
 */
import java.util.concurrent.Semaphore;

public class Pajaro implements Runnable {
    private final String especie;
    private final int id;
    private final Semaphore permisoCantar;

    public Pajaro(String especie, int id, Semaphore permisoCantar) {
        this.especie = especie;
        this.id = id;
        this.permisoCantar = permisoCantar;
    }

    @Override
    public void run() {
        try {
            // Esperar 5 segundos antes de intentar cantar
            Thread.sleep(5000);

            // Adquirir permiso para cantar
            permisoCantar.acquire();
            System.out.println("El pájaro " + id + " de la especie " + especie + " está cantando.");
            
            // Simular el canto (2 segundos)
            Thread.sleep(2000);
            System.out.println("El pájaro " + id + " de la especie " + especie + " terminó de cantar.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El pájaro " + id + " fue interrumpido.");
        } finally {
            // Liberar permiso después de cantar
            permisoCantar.release();
        }
    }
}