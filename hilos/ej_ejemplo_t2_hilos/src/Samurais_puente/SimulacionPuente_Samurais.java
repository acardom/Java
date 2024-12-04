/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Samurais_puente;

/**
 *
 * @author carde
 */
import java.util.concurrent.Semaphore;

// Clase que representa el puente
class Puente {
    // Semáforo para controlar el acceso al puente (máximo 5 samuráis a la vez)
    private Semaphore semaforo;

    // Constructor
    public Puente(int capacidad) {
        this.semaforo = new Semaphore(capacidad);
    }

    // Método para intentar cruzar el puente
    public void cruzarPuente(String nombreSamurai) {
        try {
            System.out.println(nombreSamurai + " está esperando para cruzar el puente.");
            // Adquirir un permiso para cruzar
            semaforo.acquire();
            System.out.println(nombreSamurai + " está cruzando el puente.");
            // Simular el tiempo que tarda en cruzar
            Thread.sleep((long) (Math.random() * 3000 + 1000));
            System.out.println(nombreSamurai + " ha cruzado el puente.");
        } catch (InterruptedException e) {
            System.out.println(nombreSamurai + " fue interrumpido mientras cruzaba el puente.");
        } finally {
            // Liberar el permiso para que otro samurái pueda cruzar
            semaforo.release();
        }
    }
}

// Clase que representa un samurái
class Samurai extends Thread {
    private Puente puente;
    private String nombre;

    // Constructor
    public Samurai(Puente puente, String nombre) {
        this.puente = puente;
        this.nombre = nombre;
    }

    // Método que se ejecuta cuando el hilo empieza
    @Override
    public void run() {
        puente.cruzarPuente(nombre);
    }
}

// Clase principal
public class SimulacionPuente_Samurais {
    public static void main(String[] args) {
        // Capacidad máxima del puente
        int capacidadPuente = 5;
        // Número total de samuráis que quieren cruzar
        int numeroSamurais = 20;

        // Crear el puente
        Puente puente = new Puente(capacidadPuente);

        // Crear y lanzar hilos para cada samurái
        for (int i = 1; i <= numeroSamurais; i++) {
            Samurai samurai = new Samurai(puente, "Samurai-" + i);
            samurai.start();
        }
    }
}
