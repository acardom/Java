package BarCñas; // Declaramos el paquete al que pertenece el archivo (esto puede variar dependiendo de tu estructura de proyecto).

// Clase principal que contiene toda la lógica del sistema de bar
public class ej1_1_synchronized {

    // Clase Bar: Implementación de la sincronización usando synchronized.
    public static class Bar {
        private int ocupacion = 0;  // Número de clientes que actualmente están en el bar.
        private final int aforoMaximo; // Capacidad máxima del bar (cuántos clientes pueden estar dentro).

        // Constructor de la clase Bar, donde inicializamos el aforo máximo
        public Bar(int aforoMaximo) {
            this.aforoMaximo = aforoMaximo;  // Asignamos el aforo máximo del bar al valor proporcionado.
        }

        // Método synchronized para permitir la entrada de un cliente
        public synchronized void entrar(int clienteId) {
            // Mientras el bar esté lleno, el cliente debe esperar
            while (ocupacion >= aforoMaximo) {
                try {
                    wait();  // El hilo (cliente) espera hasta que se libere espacio.
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Si se interrumpe la espera, imprimimos el error.
                }
            }

            // Si el bar no está lleno, el cliente puede entrar
            ocupacion++;  // Aumentamos el número de ocupación del bar.
            System.out.println("Cliente " + clienteId + " ha entrado. Clientes actuales: " + ocupacion); // Imprimimos el ID del cliente que entra y la ocupación actual.

            // Notificamos que un cliente ha entrado y que hay espacio para más
            notifyAll();  // Despierta a todos los hilos que están esperando.
        }

        // Método synchronized para permitir la salida de un cliente
        public synchronized void salir(int clienteId) {
            // Si hay clientes dentro del bar, uno puede salir
            if (ocupacion > 0) {
                ocupacion--;  // Reducimos el número de ocupación porque un cliente sale.
                System.out.println("Cliente " + clienteId + " ha salido. Clientes actuales: " + ocupacion);  // Imprimimos el ID del cliente que sale y la ocupación actual.

                // Si hay clientes esperando para entrar, uno puede entrar
                notifyAll();  // Despierta a los hilos (clientes) que están esperando para entrar.
            }
        }
    }

    // Clase Cliente: Representa a un cliente como un hilo que entra y sale del bar.
    public static class Cliente implements Runnable {
        private Bar bar;  // Instancia de la clase Bar, para que el cliente pueda interactuar con el bar.
        private int id;   // ID único del cliente.

        // Constructor de la clase Cliente que asigna el bar y el id del cliente
        public Cliente(Bar bar, int id) {
            this.bar = bar;  // Asignamos la instancia del bar.
            this.id = id;    // Asignamos el ID único al cliente.
        }

        // Método que define lo que hace el cliente (entra, espera, sale)
        @Override
        public void run() {
            // El cliente intenta entrar en el bar (acciona el pulsador de entrada)
            bar.entrar(id);

            // El cliente espera dentro del bar durante 10 segundos (simulando que se queda dentro)
            try {
                Thread.sleep(10000); // El cliente se queda en el bar durante 10 segundos.
            } catch (InterruptedException e) {
                e.printStackTrace(); // Si el hilo es interrumpido, imprimimos el error.
            }

            // El cliente sale del bar (acciona el pulsador de salida)
            bar.salir(id);
        }
    }

    // Clase Main: Punto de entrada para ejecutar el programa.
    public static void main(String[] args) {
        Bar bar = new Bar(3); // Creamos una instancia de Bar con un aforo máximo de 3.

        // Creamos 10 clientes con IDs del 1 al 10 que intentan entrar en el bar.
        for (int i = 1; i <= 10; i++) {
            new Thread(new Cliente(bar, i)).start();  // Para cada cliente, creamos un nuevo hilo y lo iniciamos.
        }
    }
}
