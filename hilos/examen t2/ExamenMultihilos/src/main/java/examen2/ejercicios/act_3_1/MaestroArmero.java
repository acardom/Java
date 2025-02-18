/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_3_1;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
public class MaestroArmero extends Thread {
    private Forja forja;
    private int idMaestroArmero;

    // Constructor
    public MaestroArmero(Forja forja, int idMaestroArmero) {
        this.forja = forja;
        this.idMaestroArmero = idMaestroArmero;
    }

    // Método que se ejecuta cuando el hilo comienza
    @Override
    public void run() {
        forja.repararKatana(idMaestroArmero); // El maestro armero empieza a reparar
    }
}

