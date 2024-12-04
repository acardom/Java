/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BarSynchronized;

/**
 *
 * @author carde
 */
// Clase Bar que usa synchronized para controlar el acceso
public class Bar {
    private int aforoMaximo;        // Máximo número de clientes que pueden estar en el bar
    private int clientesActuales = 0; // Número de clientes actualmente en el bar
    private int clientesEsperando = 0; // Número de clientes esperando para entrar

    // Constructor que inicializa el aforo máximo
    public Bar(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    // Método sincronizado para permitir la entrada de un cliente
    public synchronized void entrar() {
        while (clientesActuales >= aforoMaximo) {
            try {
                clientesEsperando++; // Incrementar el número de clientes esperando
                System.out.println("Bar lleno, cliente esperando...");
                wait(); // Esperar hasta que haya espacio en el bar
                clientesEsperando--; // Decrementar los clientes esperando
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        clientesActuales++; // Incrementar el número de clientes en el bar
        System.out.println("Cliente ha entrado al bar.");
        abrirPuertaE(); // Simula la apertura de la puerta de entrada
    }

    // Método sincronizado para permitir la salida de un cliente
    public synchronized void salir() {
        if (clientesActuales > 0) {
            clientesActuales--; // Decrementar el número de clientes en el bar
            System.out.println("Cliente ha salido del bar.");
            notify(); // Notificar a un cliente esperando que puede entrar
        }
        abrirPuertaS(); // Simula la apertura de la puerta de salida
    }

    // Simulación de la apertura de la puerta de entrada
    private void abrirPuertaE() {
        System.out.println("Letrero: Puedes entrar.");
    }

    // Simulación de la apertura de la puerta de salida
    private void abrirPuertaS() {
        System.out.println("Letrero: Puedes salir.");
    }
}

// Clase Cliente que implementa Runnable para representar a un cliente
class Cliente implements Runnable {
    private Bar bar;

    // Constructor que recibe el bar compartido
    public Cliente(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void run() {
        bar.entrar(); // Simula que el cliente intenta entrar al bar
        try {
            Thread.sleep(10000); // Simula que el cliente permanece en el bar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        bar.salir(); // Simula que el cliente sale del bar
    }
}
