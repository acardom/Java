/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_3_1;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
import examen2.Comun;
import java.util.concurrent.Semaphore;

public class Forja {
    private final Semaphore estanteria; // Semáforo para gestionar los huecos en la estantería (máximo 5)
    private final Semaphore katanaDisponible; // Semáforo para notificar que hay una katana disponible
    private final Semaphore reparacion; // Semáforo para permitir que ambos maestros armeros reparen simultáneamente
    private final int tiempoReparacion = 6000; // Tiempo para reparar cada katana (6 segundos)

    public Forja(int capacidad) {
        this.estanteria = new Semaphore(capacidad); // Capacidad máxima de la estantería (5 huecos)
        this.katanaDisponible = new Semaphore(0); // Inicialmente no hay katanas disponibles
        this.reparacion = new Semaphore(2); // Dos maestros armeros pueden trabajar simultáneamente
    }

    // Método para que un samurái deje su katana en la estantería
    public void dejarKatana(int idSamurai) {
        try {
            // Simula que el samurái está llegando a la estantería y esperando si está llena
            System.out.println(Comun.FechaActual() + " El samurai " + idSamurai + " llega a la estantería y se queda esperando a que haya hueco para dejar la katana.");
            estanteria.acquire(); // Intentar dejar la katana en la estantería (espera si no hay hueco)

            // El samurái ha dejado la katana en la estantería
            System.out.println(Comun.FechaActual() + " El samurai " + idSamurai + " ha dejado la katana en la estantería y se va.");

            // Notificar que hay una katana disponible para ser reparada
            katanaDisponible.release(); 

        } catch (InterruptedException e) {
            System.out.println(Comun.FechaActual() + " El samurai " + idSamurai + " fue interrumpido mientras dejaba su katana.");
        }
    }

    // Método para que un maestro armero repare una katana
    public void repararKatana(int idMaestroArmero) {
        while (true) {  // Mantener el ciclo de reparación hasta que no haya katanas disponibles
            try {
                // Esperar a que haya una katana disponible en la estantería
                katanaDisponible.acquire(); // Esto asegura que solo repararán si hay katanas disponibles

                reparacion.acquire(); // Un maestro armero puede empezar a reparar
                System.out.println(Comun.FechaActual() + " El Maestro Armero-" + idMaestroArmero + " empieza a reparar una katana.");
                estanteria.release();  // Esto permite que un samurái deje su katana
                Thread.sleep(tiempoReparacion); // Simulamos el tiempo de reparación (6 segundos)
                System.out.println(Comun.FechaActual() + " El Maestro Armero-" + idMaestroArmero + " ha terminado de reparar la katana.");
                
                // Liberar un hueco en la estantería después de reparar la katana
                

            } catch (InterruptedException e) {
                System.out.println(Comun.FechaActual() + " El Maestro Armero-" + idMaestroArmero + " fue interrumpido mientras reparaba.");
            } finally {
                reparacion.release(); // El maestro armero termina y libera el semáforo para el siguiente
            }
        }
    }
}


