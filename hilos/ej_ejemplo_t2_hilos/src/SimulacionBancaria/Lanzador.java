/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SimulacionBancaria;

/**
 *
 * @author carde
 */

// Clase principal Lanzador
public class Lanzador {
    public static void main(String[] args) throws InterruptedException {
        // Inicialización de la cuenta con saldo inicial de 100
        Cuenta cuenta = new Cuenta(100);

        // Definimos el número de procesos de cada tipo
        final int NUM_OPS_CON_100 = 40;
        final int NUM_OPS_CON_50 = 20;
        final int NUM_OPS_CON_20 = 60;

        // Arrays para almacenar los hilos
        Thread[] hilosIngresan100 = new Thread[NUM_OPS_CON_100];
        Thread[] hilosRetiran100 = new Thread[NUM_OPS_CON_100];
        Thread[] hilosIngresan50 = new Thread[NUM_OPS_CON_50];
        Thread[] hilosRetiran50 = new Thread[NUM_OPS_CON_50];
        Thread[] hilosIngresan20 = new Thread[NUM_OPS_CON_20];
        Thread[] hilosRetiran20 = new Thread[NUM_OPS_CON_20];

        // Lanzar hilos para ingresos y retiros de 100
        for (int i = 0; i < NUM_OPS_CON_100; i++) {
            hilosIngresan100[i] = new Thread(new HiloCliente(cuenta, 100, "Ingreso"));
            hilosRetiran100[i] = new Thread(new HiloCliente(cuenta, -100, "Retirada"));
            hilosIngresan100[i].start();
            hilosRetiran100[i].start();
        }

        // Lanzar hilos para ingresos y retiros de 50
        for (int i = 0; i < NUM_OPS_CON_50; i++) {
            hilosIngresan50[i] = new Thread(new HiloCliente(cuenta, 50, "Ingreso"));
            hilosRetiran50[i] = new Thread(new HiloCliente(cuenta, -50, "Retirada"));
            hilosIngresan50[i].start();
            hilosRetiran50[i].start();
        }

        // Lanzar hilos para ingresos y retiros de 20
        for (int i = 0; i < NUM_OPS_CON_20; i++) {
            hilosIngresan20[i] = new Thread(new HiloCliente(cuenta, 20, "Ingreso"));
            hilosRetiran20[i] = new Thread(new HiloCliente(cuenta, -20, "Retirada"));
            hilosIngresan20[i].start();
            hilosRetiran20[i].start();
        }

        // Esperar a que terminen todos los hilos de ingresos y retiros
        for (int i = 0; i < NUM_OPS_CON_100; i++) {
            hilosIngresan100[i].join();
            hilosRetiran100[i].join();
        }
        for (int i = 0; i < NUM_OPS_CON_50; i++) {
            hilosIngresan50[i].join();
            hilosRetiran50[i].join();
        }
        for (int i = 0; i < NUM_OPS_CON_20; i++) {
            hilosIngresan20[i].join();
            hilosRetiran20[i].join();
        }

        // Mostrar el historial de operaciones
        cuenta.mostrarOperaciones();

        // Verificar si la simulación fue correcta
        if (cuenta.esSimulacionCorrecta()) {
            System.out.println("La simulación fue correcta. Saldo final: " + cuenta.getSaldo());
        } else {
            System.out.println("La simulación falló.");
            System.out.println("Saldo final: " + cuenta.getSaldo());
            System.out.println("Revise sus synchronized.");
        }
    }
}