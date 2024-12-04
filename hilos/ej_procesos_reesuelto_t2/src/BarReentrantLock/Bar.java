/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BarReentrantLock;

/**
 *
 * @author carde
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bar {

    private int aforoMaximo;
    private int clientesActuales = 0;
    private int clientesEsperando = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition entrada = lock.newCondition();
    private Condition salida = lock.newCondition();
    private Condition ewokEntrada = lock.newCondition();

    public Bar(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }
// Método para permitir la entrada de un cliente

    public void entrar(String tipo) {
        lock.lock();
        try {
            while (clientesActuales >= aforoMaximo) {
                clientesEsperando++;
                System.out.println(tipo + " esperando para entrar.");
                if (tipo.equals("Ewok")) {
                    ewokEntrada.await(); // Ewok espera a entrar
                } else {
                    entrada.await(); // Gorax espera a que haya espacio
                }
                clientesEsperando--;
            }
            clientesActuales++;
            System.out.println(tipo + " ha entrado al bar.");
            if (tipo.equals("Ewok")) {
                ewokEntrada.signal(); // Permite que otro Ewok entre
            } else {
                entrada.signal(); // Permite que un Gorax entre
            }
            abrirPuertaE(); // Simula la apertura de la puerta
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
// Método para permitir la salida de un cliente

    public void salir() {
        lock.lock();
        try {
            clientesActuales--;
            System.out.println("Cliente ha salido del bar.");
            if (clientesEsperando > 0) {
                entrada.signal(); // Permite que uno de los clientes espere
            }
            abrirPuertaS(); // Simula la apertura de la puerta
        } finally {
            lock.unlock();
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
