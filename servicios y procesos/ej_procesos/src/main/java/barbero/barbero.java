/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barbero;

/**
 *
 * @author carde
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class barbero {
    private static final int NUM_SILLAS = 5; // Número de sillas de espera en la barbería
    private static final Semaphore sillasDisponibles = new Semaphore(NUM_SILLAS, true); // Controla cuántas sillas están libres
    private static final Semaphore sillaBarbero = new Semaphore(1, true); // Controla el sillón del barbero (solo uno puede estar ocupado a la vez)
    private static final Semaphore barberoListo = new Semaphore(0, true); // Indica si el barbero está listo para atender a un cliente
    private static boolean barberoDurmiendo = true; // Estado del barbero (si está durmiendo o no)

    public static void main(String[] args) {
        // Hilo del barbero
        Thread barbero = new Thread(() -> {
            while (true) { // El barbero siempre está en un bucle infinito
                try {
                    // Espera a que un cliente lo despierte (barberoListo.acquire())
                    barberoListo.acquire();
                    barberoDurmiendo = false; // El barbero está despierto
                    System.out.println("Barbero: Me despierto para atender a un cliente.");

                    // El barbero toma una decisión al azar: dormitar o cortar el pelo
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        System.out.println("Barbero: Me hago el loco y sigo dormitando...");
                        // Duerme entre 3 y 6 segundos antes de volver a hacer algo
                        Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 6000));
                    } else {
                        System.out.println("Barbero: Cortando el pelo...");
                        // Tarda entre 5 y 10 segundos en cortar el pelo
                        Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 10000));
                        System.out.println("Barbero: Limpiando el suelo...");
                        // Tarda 5 segundos adicionales en limpiar el suelo
                        Thread.sleep(5000);
                    }

                    // Termina de atender al cliente, libera el sillón del barbero
                    System.out.println("Barbero: Cliente atendido. Silla del barbero libre.");
                    sillaBarbero.release();

                    // Si no hay más clientes en las sillas de espera, el barbero vuelve a dormir
                    if (sillasDisponibles.availablePermits() == NUM_SILLAS) { // Todas las sillas están libres
                        System.out.println("Barbero: Nadie más, me voy a dormir...");
                        barberoDurmiendo = true; // El barbero vuelve a dormir
                        // Duerme entre 10 y 20 segundos
                        Thread.sleep(ThreadLocalRandom.current().nextInt(10000, 20000));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Manejo de interrupciones del hilo
                }
            }
        });

        // Inicia el hilo del barbero
        barbero.start();

        // Generar clientes
        for (int i = 0; i < 10; i++) { // Generamos 20 clientes para probar
            final int clienteId = i + 1; // Asignamos un ID único a cada cliente
            Thread cliente = new Thread(() -> {
                try {
                    System.out.println("Cliente " + clienteId + ": Llego a la barbería.");

                    // El cliente intenta ocupar una silla de espera
                    if (sillasDisponibles.tryAcquire()) { // Si hay una silla disponible
                        System.out.println("Cliente " + clienteId + ": Me siento en una silla de espera.");

                        // Espera su turno para sentarse en el sillón del barbero
                        sillaBarbero.acquire(); // Intenta ocupar el sillón del barbero
                        sillasDisponibles.release(); // Libera su silla de espera
                        System.out.println("Cliente " + clienteId + ": Me siento en la silla del barbero.");

                        // Despierta al barbero para que lo atienda
                        barberoListo.release();

                        // Espera hasta que el barbero termine de atenderlo
                        sillaBarbero.acquire();
                        System.out.println("Cliente " + clienteId + ": Salgo de la barbería satisfecho.");
                        sillaBarbero.release(); // Libera el sillón del barbero para el siguiente cliente
                    } else {
                        // Si no hay sillas de espera disponibles, el cliente se va
                        System.out.println("Cliente " + clienteId + ": No hay sillas disponibles, me voy.");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Manejo de interrupciones del hilo
                }
            });

            // Inicia el hilo del cliente
            cliente.start();

            // Simula el tiempo de llegada entre clientes (1 a 3 segundos)
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Manejo de interrupciones del hilo
            }
        }
    }
}

