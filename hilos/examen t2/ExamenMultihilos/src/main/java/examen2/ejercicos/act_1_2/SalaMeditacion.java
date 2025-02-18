/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicos.act_1_2;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
import java.util.concurrent.Semaphore;
import examen2.Comun;

public class SalaMeditacion {
    private int segundosEspera = 5; // Tiempo de meditación de cada samurái
    private final Semaphore semaforo; // Semáforo para controlar el acceso a la sala

    // Constructor
    public SalaMeditacion(int capacidad) {
        this.semaforo = new Semaphore(capacidad); // Capacidad máxima de samuráis en la sala
    }

    // Método para intentar entrar y meditar
    public void salaMeditacion(String nombreSamurai) {
        if (semaforo.tryAcquire()) { // Intenta adquirir un permiso, sin bloquearse
            try {
                System.out.println(Comun.FechaActual() + " " + nombreSamurai + " ha entrado a la sala de meditacion y espera 5 segundos para salir");
                // Simular el tiempo que tarda en meditar (5 segundos)
                Thread.sleep(segundosEspera * 1000);
            } catch (InterruptedException e) {
                
            } finally {
                // Liberar el permiso para que otro samurái pueda entrar
                semaforo.release();
            }
        } else {
            // Si no pudo entrar (ya hay 5 samuráis), se va directamente
            System.out.println(Comun.FechaActual() + " " + nombreSamurai + " no ha entrado en la sala de meditacion porque no queria esperar que hubiera un sitio libre.");
        }
    }
}






