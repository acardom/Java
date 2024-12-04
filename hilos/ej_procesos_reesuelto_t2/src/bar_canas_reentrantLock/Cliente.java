/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bar_canas_reentrantLock;

/**
 *
 * @author carde
 */
public class Cliente implements Runnable {

    private Bar bar;
    private int cañasAConsumir;

    public Cliente(Bar bar, int cañasAConsumir) {
        this.bar = bar;
        this.cañasAConsumir = cañasAConsumir;
    }

    @Override
    public void run() {
        for (int i = 0; i < cañasAConsumir; i++) {
            bar.tomarCaña(); // El cliente intenta tomar una caña
            try {
                Thread.sleep(500); // El cliente espera entre caña y caña
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
