/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BarReentrantLock;

/**
 *
 * @author carde
 */
public class Main {
    public static void main(String[] args) {
        final int AFORO_MAXIMO = 5; // Aforo máximo del bar
        final int NUM_EWOKS = 10;  // Número de Ewoks
        final int NUM_GORAX = 10;  // Número de Gorax

        Bar bar = new Bar(AFORO_MAXIMO);

        // Crear y lanzar hilos para Ewoks
        for (int i = 0; i < NUM_EWOKS; i++) {
            Cliente ewok = new Cliente(bar, "Ewok");
            Thread hiloEwok = new Thread(ewok, "Ewok-" + i);
            hiloEwok.start();
        }

        // Crear y lanzar hilos para Gorax
        for (int i = 0; i < NUM_GORAX; i++) {
            Cliente gorax = new Cliente(bar, "Gorax");
            Thread hiloGorax = new Thread(gorax, "Gorax-" + i);
            hiloGorax.start();
        }
    }
}
