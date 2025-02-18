/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_3_3;

/**
 *
 * @author DAM
 */
import java.util.concurrent.Semaphore;

public class Portero extends Thread {
    private final Forja forja;
    private final int idPortero;

    public Portero(Forja forja, int idPortero) {
        this.forja = forja;
        this.idPortero = idPortero;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            forja.recibirYColocarKatana(idPortero);  // El portero recoge y coloca la katana
        }
    }
}
