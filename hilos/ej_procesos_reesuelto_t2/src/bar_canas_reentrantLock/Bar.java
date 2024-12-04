/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bar_canas_reentrantLock;

/**
 *
 * @author carde
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bar {

    private int cañasDisponibles; // Número de cañas que quedan en el barril
    private ReentrantLock lock = new ReentrantLock(); // ReentrantLock para sincronización
    private Condition barrilVacio = lock.newCondition(); // Condición para cuando el barril esté vacío
    private Condition cañaServida = lock.newCondition(); // Condición para cuando se sirve una caña 
// Constructor que inicializa el número de cañas disponibles en el barril


    public Bar(int nCañas) {
        this.cañasDisponibles = nCañas;
    }
// Método para que el cliente tome una caña

    public void tomarCaña() {
        lock.lock();
        try {
            while (cañasDisponibles <= 0) {
// Si el barril está vacío, el cliente espera a que se reponga
                System.out.println("Caña no disponible. Esperando al cambio de barril...");
                barrilVacio.await(); // El cliente espera
            }
// El cliente sirve una caña
            cañasDisponibles--;
            System.out.println("Caña servida. Cañas restantes: " + cañasDisponibles);
            if (cañasDisponibles == 0) {
                System.out.println("El barril está vacío. Avisando al camarero...");
                cañaServida.signal(); // Señalizar al camarero que reponga el barril
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
// Método para que el camarero reponga el barril

    public void reponer() {
        lock.lock();
        try {
            while (cañasDisponibles > 0) {
// Si el barril aún tiene cañas, el camarero espera
                cañaServida.await();
            }
// El camarero repone el barril
            cañasDisponibles = 10; // Se repone el barril con 10 cañas
            System.out.println("Barril repuesto. El camarero avisa que ya se pueden servir más cañas. ");
        barrilVacio.signalAll(); // Señalizar a todos los clientes que pueden tomar caña
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}




