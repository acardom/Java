/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProductoresConsumidores;

/**
 *
 * @author carde
 */
import java.util.LinkedList;
import java.util.Random;

// Clase que representa la cola limitada y thread-safe
class Cola {
    private final int MAX_ELEMENTOS;
    private final LinkedList<Integer> cola;

    public Cola(int max) {
        this.cola = new LinkedList<>();
        this.MAX_ELEMENTOS = max;
    }

    // Método para verificar si la cola está vacía
    public synchronized boolean estaVacia() {
        return cola.isEmpty();
    }

    // Método para verificar si la cola está llena
    public synchronized boolean estaLlena() {
        return cola.size() == this.MAX_ELEMENTOS;
    }

    // Método para encolar un elemento de forma segura
    public synchronized boolean encolar(int numero) {
        if (estaLlena()) {
            return false;
        }
        cola.addLast(numero);
        return true;
    }

    // Método para desencolar un elemento de forma segura
    public synchronized int desencolar() {
        if (estaVacia()) {
            return -1; // Valor especial que indica que no se puede desencolar
        }
        return cola.removeFirst();
    }
}

// Clase Productor
class Productor implements Runnable {
    private final Cola colaCompartida;

    public Productor(Cola cola) {
        this.colaCompartida = cola;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int num = random.nextInt(100); // Genera un número aleatorio
            while (!colaCompartida.encolar(num)) {
                System.out.println("Productor esperando: cola llena.");
                esperarTiempoAzar(3000); // Espera un tiempo aleatorio
            }
            System.out.println("Productor encoló el número: " + num);
        }
    }

    // Método para simular tiempo de espera aleatorio
    private void esperarTiempoAzar(int maxMilisegundos) {
        try {
            Thread.sleep((long) (Math.random() * maxMilisegundos));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Clase Consumidor
class Consumidor implements Runnable {
    private final Cola colaCompartida;

    public Consumidor(Cola cola) {
        this.colaCompartida = cola;
    }

    @Override
    public void run() {
        while (true) {
            int num = colaCompartida.desencolar();
            if (num != -1) {
                System.out.println("Consumidor recuperó el número: " + num);
            } else {
                System.out.println("Consumidor esperando: cola vacía.");
                esperarTiempoAzar(3000); // Espera un tiempo aleatorio
            }
        }
    }

    // Método para simular tiempo de espera aleatorio
    private void esperarTiempoAzar(int maxMilisegundos) {
        try {
            Thread.sleep((long) (Math.random() * maxMilisegundos));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Clase principal que lanza los productores y consumidores
public class Lanzador {
    public static void main(String[] args) throws InterruptedException {
        final int MAX_PRODUCTORES = 5;
        final int MAX_CONSUMIDORES = 7;
        final int MAX_ELEMENTOS = 10;

        Thread[] hilosProductor = new Thread[MAX_PRODUCTORES];
        Thread[] hilosConsumidor = new Thread[MAX_CONSUMIDORES];

        Cola colaCompartida = new Cola(MAX_ELEMENTOS);

        // Crear y lanzar hilos productores
        for (int i = 0; i < MAX_PRODUCTORES; i++) {
            Productor productor = new Productor(colaCompartida);
            hilosProductor[i] = new Thread(productor, "Productor-" + i);
            hilosProductor[i].start();
        }

        // Crear y lanzar hilos consumidores
        for (int i = 0; i < MAX_CONSUMIDORES; i++) {
            Consumidor consumidor = new Consumidor(colaCompartida);
            hilosConsumidor[i] = new Thread(consumidor, "Consumidor-" + i);
            hilosConsumidor[i].start();
        }

        // Esperar a que todos los hilos terminen (aunque este caso es infinito)
        for (Thread hilo : hilosProductor) {
            hilo.join();
        }
        for (Thread hilo : hilosConsumidor) {
            hilo.join();
        }
    }
}
