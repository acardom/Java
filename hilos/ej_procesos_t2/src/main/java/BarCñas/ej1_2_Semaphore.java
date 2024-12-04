package BarCñas;

import java.util.concurrent.Semaphore; // Importamos Semaphore

public class ej1_2_Semaphore {

    // Clase Bar: Implementación de la sincronización usando Semaphore
    public static class Bar {
        private final Semaphore aforo; // Semaphore para controlar el aforo del bar
        private int ocupacion = 0;     // Número de clientes que actualmente están en el bar

        // Constructor de la clase Bar, donde inicializamos el Semaphore con el aforo máximo
        public Bar(int aforoMaximo) {
            aforo = new Semaphore(aforoMaximo); // Un número máximo de permisos para el aforo
        }

        // Método para permitir la entrada de un cliente
        public void entrar(int clienteId) {
            try {
                // Intentamos adquirir un permiso para entrar
                aforo.acquire(); // Si no hay permisos disponibles, el cliente espera

                // Se incrementa el contador de clientes dentro del bar después de obtener el permiso
                synchronized (this) {
                    ocupacion++; // Aumentamos el contador de clientes dentro del bar
                }

                // Imprimimos el estado
                System.out.println("Cliente " + clienteId + " ha entrado. Clientes actuales: " + ocupacion);
            } catch (InterruptedException e) {
                e.printStackTrace(); // En caso de error, lo imprimimos
            }
        }

        // Método para permitir la salida de un cliente
        public void salir(int clienteId) {
            // Primero decrementamos el contador de clientes dentro del bar
            synchronized (this) {
                ocupacion--; // Disminuimos el contador de clientes dentro del bar
            }

            // Liberamos el permiso para que otro cliente pueda entrar
            aforo.release();

            // Imprimimos el estado
            System.out.println("Cliente " + clienteId + " ha salido. Clientes actuales: " + ocupacion);
        }
    }

    // Clase Cliente: Representa a un cliente como un hilo que entra y sale del bar
    public static class Cliente implements Runnable {
        private Bar bar;  // Instancia de la clase Bar, para que el cliente pueda interactuar con el bar
        private int id;   // ID único del cliente

        // Constructor de la clase Cliente que asigna el bar y el id del cliente
        public Cliente(Bar bar, int id) {
            this.bar = bar;  // Asignamos la instancia del bar
            this.id = id;    // Asignamos el ID único al cliente
        }

        // Método que define lo que hace el cliente (entra, espera, sale)
        @Override
        public void run() {
            // El cliente intenta entrar en el bar
            bar.entrar(id);

            // El cliente espera dentro del bar durante 10 segundos (simulando que se queda dentro)
            try {
                Thread.sleep(10000); // El cliente se queda en el bar durante 10 segundos
            } catch (InterruptedException e) {
                e.printStackTrace(); // Si el hilo es interrumpido, imprimimos el error
            }

            // El cliente sale del bar
            bar.salir(id);
        }
    }

    // Clase Main: Punto de entrada para ejecutar el programa
    public static void main(String[] args) {
        Bar bar = new Bar(3); // Creamos una instancia de Bar con un aforo máximo de 3

        // Creamos 10 clientes con IDs del 1 al 10 que intentan entrar en el bar
        for (int i = 1; i <= 10; i++) {
            new Thread(new Cliente(bar, i)).start();  // Para cada cliente, creamos un nuevo hilo y lo iniciamos
        }
    }
}
