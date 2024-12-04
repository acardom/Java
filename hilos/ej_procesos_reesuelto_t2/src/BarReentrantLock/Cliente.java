/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BarReentrantLock;

/**
 *
 * @author carde
 */
public class Cliente implements Runnable {

    private Bar bar;
    private String tipo;

    public Cliente(Bar bar, String tipo) {
        this.bar = bar;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        bar.entrar(tipo); // El cliente entra dependiendo de su tipo
        try {
            Thread.sleep(10000); // Simula el tiempo que el cliente pasa en el bar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        bar.salir(); // El cliente sale
    }
}
