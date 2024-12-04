/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bar_canas_reentrantLock;

/**
 *
 * @author carde
 */
public class Main {

    public static void main(String[] args) {
        Bar bar = new Bar(5); // Un barril con 5 cañas
// Crear el camarero en un hilo que estará pendiente de reponer el barril
        Thread camarero = new Thread(() -> {
            while (true) {
                bar.reponer(); // El camarero repone el barril cuando esté vacío
                try {
                    Thread.sleep(2000); // Simula el tiempo para reponer el barril
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        camarero.start();
// Crear clientes
        Thread cliente1 = new Thread(new Cliente(bar, 3));
        Thread cliente2 = new Thread(new Cliente(bar, 3));
        Thread cliente3 = new Thread(new Cliente(bar, 2));
        cliente1.start();
        cliente2.start();
        cliente3.start();
    }
}