/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BarSemaphore;

/**
 *
 * @author carde
 */
import java.util.concurrent.Semaphore;

import java.util.concurrent.Semaphore;

// Clase Bar que utiliza Semaphore para controlar el acceso
public class Bar {
    private Semaphore entrada; // Semáforo para controlar la entrada
    private int clientesEsperando = 0; // Número de clientes esperando para entrar

    // Constructor que inicializa el semáforo con el aforo máximo permitido
    public Bar(int aforoMaximo) {
        entrada = new Semaphore(aforoMaximo, true); // Semáforo con permisos equitativos
    }

    // Método para permitir la entrada de un cliente
    public void entrar() {
        try {
            if (!entrada.tryAcquire()) {
                // Si no hay espacio, incrementa los clientes esperando y muestra mensaje
                clientesEsperando++;
                System.out.println("Bar lleno, cliente esperando...");
                entrada.acquire(); // Esperar hasta que haya espacio
                clientesEsperando--;
            }
            System.out.println("Cliente ha entrado al bar.");
            abrirPuertaE(); // Simula la apertura de la puerta de entrada
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Método para permitir la salida de un cliente
    public void salir() {
        try {
            System.out.println("Cliente ha salido del bar.");
            entrada.release(); // Permitir que un nuevo cliente entre
            abrirPuertaS(); // Simula la apertura de la puerta de salida
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
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

// Clase principal que arranca la simulación del bar con clientes

