/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bar_canas_synchronized;

/**
 *
 * @author carde
 */
public class Bar {

    private int cañasDisponibles; // Número de cañas que quedan en el barril
    private boolean barrilRepuesto = true; // Indica si el barril ha sido repuesto
// Constructor que inicializa el número de cañas disponibles en el barril

    public Bar(int nCañas) {
        this.cañasDisponibles = nCañas;
    }
// Método para que el cliente tome una caña

    public synchronized void tomarCaña() {
        while (cañasDisponibles <= 0) {
// Si el barril está vacío, el cliente espera a que se reponga
            System.out.println("Caña no disponible. Esperando al cambio de barril...");
            try {
                wait(); // El cliente espera a que el camarero reponga el barril
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
// El cliente sirve una caña
        cañasDisponibles--;
        System.out.println("Caña servida. Cañas restantes: " + cañasDisponibles);
        if (cañasDisponibles == 0) {
            System.out.println("El barril está vacío. Avisando al camarero...");
        }
    }
// Método para que el camarero reponga el barril

    public synchronized void reponer() {
        while (cañasDisponibles > 0) {
// Si el barril aún tiene cañas, el camarero espera
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
// El camarero repone el barril
        cañasDisponibles = 10; // Se repone el barril con 10 cañas
        System.out.println("Barril repuesto. El camarero avisa que ya se pueden servir más cañas.");
notifyAll(); // Notificar a todos los clientes que ahora pueden tomar caña
    }
}


