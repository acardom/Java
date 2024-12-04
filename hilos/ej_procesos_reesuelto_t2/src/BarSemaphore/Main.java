/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BarSemaphore;

/**
 *
 * @author carde
 */
public class Main {
    public static void main(String[] args) {
        final int AFORO_MAXIMO = 5; // Capacidad máxima del bar
        final int NUM_CLIENTES = 20; // Número total de clientes

        Bar bar = new Bar(AFORO_MAXIMO);

        // Crear y lanzar los hilos para los clientes
        for (int i = 0; i < NUM_CLIENTES; i++) {
            Cliente cliente = new Cliente(bar);
            Thread hiloCliente = new Thread(cliente, "Cliente-" + i);
            hiloCliente.start();
        }
    }
}
