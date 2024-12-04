/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_3_3;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
import examen2.Comun;
import java.util.concurrent.Semaphore;

import java.util.concurrent.Semaphore;

public class Forja {
    private final Semaphore estanteria; // Semáforo para gestionar los huecos en la estantería (máximo 5)
    private final Semaphore salaEspera; // Semáforo para gestionar la sala de espera (máximo 5 samuráis)
    private final Semaphore katanaDisponible; // Semáforo para notificar que hay una katana disponible
    private final Semaphore reparacion; // Semáforo para permitir que ambos maestros armeros reparen simultáneamente
    private final int tiempoReparacion = 6000; // Tiempo para reparar cada katana (6 segundos)

    public Forja(int capacidadEstanteria, int capacidadSalaEspera) {
        this.estanteria = new Semaphore(capacidadEstanteria); // Capacidad máxima de la estantería (5 huecos)
        this.salaEspera = new Semaphore(capacidadSalaEspera); // Capacidad máxima de la sala de espera (4 samuráis)
        this.katanaDisponible = new Semaphore(0); // Inicialmente no hay katanas disponibles
        this.reparacion = new Semaphore(2); // Dos maestros armeros pueden trabajar simultáneamente
    }

    // Método para que el portero reciba y coloque las katanas en la estantería
    public void recibirYColocarKatana(int idPortero) {
        try {
            // El portero recoge una katana y va hacia la estantería
            System.out.println(Comun.FechaActual() + " El Portero recoge la katana " + idPortero + " y camina hacia la estantería.");
            Thread.sleep(1000); // El portero tarda 1 segundo en llegar a la estantería

            // Esperar si la estantería está llena
            estanteria.acquire(); // Intentar colocar la katana en la estantería
            System.out.println(Comun.FechaActual() + " El Portero ha dejado la katana " + idPortero + " en la estantería.");

            // Notificar que hay una katana disponible para ser reparada
            katanaDisponible.release();

            // El portero vuelve a la puerta
            Thread.sleep(1000); // El portero tarda 1 segundo en regresar
            System.out.println(Comun.FechaActual() + " El Portero ha vuelto a la puerta.");

        } catch (InterruptedException e) {
            System.out.println(Comun.FechaActual() + " El Portero fue interrumpido.");
        }
    }

    // Método para que un samurái deje su katana en la estantería
    public void dejarKatana(int idSamurai) {
        try {
            // Simula que el samurái está esperando y luego dejando su katana
            System.out.println(Comun.FechaActual() + " El samurai " + idSamurai + " llega a la sala de espera.");
            salaEspera.acquire(); // Un samurái entra en la sala de espera (máximo 4 samuráis)

            // Esperar a que el portero regrese para poder dejar la katana
            katanaDisponible.acquire(); // Solo deja la katana cuando el portero haya dejado una

            System.out.println(Comun.FechaActual() + " El samurai " + idSamurai + " ha dejado su katana.");
        } catch (InterruptedException e) {
            System.out.println(Comun.FechaActual() + " El samurai " + idSamurai + " fue interrumpido.");
        } finally {
            salaEspera.release(); // Un samurái deja la sala de espera
        }
    }

    // Método para que un maestro armero repare una katana
    public void repararKatana(int idMaestroArmero) {
        while (true) {
            try {
                // Esperar a que haya una katana disponible en la estantería
                katanaDisponible.acquire(); // Esto asegura que solo repararán si hay katanas disponibles

                reparacion.acquire(); // Un maestro armero puede empezar a reparar
                System.out.println(Comun.FechaActual() + " El Maestro Armero-" + idMaestroArmero + " empieza a reparar una katana.");
                Thread.sleep(tiempoReparacion); // Simulamos el tiempo de reparación (6 segundos)
                System.out.println(Comun.FechaActual() + " El Maestro Armero-" + idMaestroArmero + " ha terminado de reparar la katana.");

                // Liberar el semáforo para que un samurái pueda dejar otra katana en la estantería
                estanteria.release(); // Después de la reparación, el hueco se libera

            } catch (InterruptedException e) {
                System.out.println(Comun.FechaActual() + " El Maestro Armero-" + idMaestroArmero + " fue interrumpido.");
            } finally {
                reparacion.release(); // El maestro armero termina y libera el semáforo para el siguiente
            }
        }
    }
}
