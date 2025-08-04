package BarCñas;

import java.util.LinkedList;
import java.util.Queue;

public class ej1_3_Alienigenas {

    // Clase Bar con prioridad para los Ewoks
    public static class Bar {
        private int ocupacion = 0;  // Número de clientes que actualmente están en el bar.
        private final int aforoMaximo; // Capacidad máxima del bar.

        // Colas separadas para Ewok y Gorax
        private final Queue<Integer> colaEwok = new LinkedList<>();
        private final Queue<Integer> colaGorax = new LinkedList<>();

        // Constructor de la clase Bar
        public Bar(int aforoMaximo) {
            this.aforoMaximo = aforoMaximo;
        }

        // Método synchronized para permitir la entrada con prioridad a los Ewok
        public synchronized void entrar(int clienteId, String tipo) {
            // Dependiendo del tipo, añadimos a la cola correspondiente
            if (tipo.equals("Ewok")) {
                colaEwok.add(clienteId);
            } else {
                colaGorax.add(clienteId);
            }

            // Mientras no pueda entrar, esperamos
            while (ocupacion >= aforoMaximo || (!colaEwok.isEmpty() && !tipo.equals("Ewok"))) {
                try {
                    wait(); // Espera hasta que haya espacio y prioridad
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Si es su turno, lo sacamos de la cola y entra
            if (tipo.equals("Ewok") && !colaEwok.isEmpty()) {
                colaEwok.poll(); // Quitamos al Ewok de la cola
            } else if (!colaGorax.isEmpty()) {
                colaGorax.poll(); // Quitamos al Gorax de la cola
            }

            ocupacion++;
            System.out.println("Ha conseguido entrar en el bar el cliente " + clienteId + ", tipo: " + tipo +
                    ". En la cola esperando hay:\n\tEwok: " + colaEwok.size() +
                    "\n\tGorax: " + colaGorax.size());

            notifyAll(); // Notificamos a los demás hilos
        }

        // Método synchronized para permitir la salida de un cliente
        public synchronized void salir(int clienteId, String tipo) {
            ocupacion--;
            System.out.println("Ha salido del bar el cliente " + clienteId + ", tipo: " + tipo);

            notifyAll(); // Notificamos que un espacio ha quedado libre
        }
    }

    // Clase Cliente que representa a un alienígena (Ewok o Gorax)
    public static class Cliente implements Runnable {
        private Bar bar;
        private int id;
        private String tipo;

        // Constructor de Cliente
        public Cliente(Bar bar, int id, String tipo) {
            this.bar = bar;
            this.id = id;
            this.tipo = tipo;
        }

        @Override
        public void run() {
            // El cliente intenta entrar en el bar
            bar.entrar(id, tipo);

            // Simula que se queda en el bar durante 10 segundos
            try {
                Thread.sleep(10000); // 10 segundos dentro del bar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // El cliente sale del bar
            bar.salir(id, tipo);
        }
    }

    // Clase Main para ejecutar el programa
    public static void main(String[] args) {
        Bar bar = new Bar(3); // Creamos un bar con aforo máximo de 3

        // Creamos 12 clientes alternando entre Ewoks y Gorax
        for (int i = 1; i <= 12; i++) {
            String tipo = (i % 2 == 0) ? "Gorax" : "Ewok"; // Alterna entre Ewok y Gorax
            new Thread(new Cliente(bar, i, tipo)).start();
        }
    }
}
