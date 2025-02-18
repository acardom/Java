/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_3_1;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
public class Samurai extends Thread {
    private Forja forja;
    private int idSamurai;

    // Constructor
    public Samurai(Forja forja, int idSamurai) {
        this.forja = forja;
        this.idSamurai = idSamurai;
    }

    // Método que se ejecuta cuando el hilo comienza
    @Override
    public void run() {
        forja.dejarKatana(idSamurai); // El samurái deja su katana
    }
}



