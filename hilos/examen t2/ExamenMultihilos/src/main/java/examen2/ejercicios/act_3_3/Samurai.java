/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_3_3;

import examen2.ejercicios.act_3_1.*;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
public class Samurai extends Thread {
    private final Forja forja;
    private final int idSamurai;

    public Samurai(Forja forja, int idSamurai) {
        this.forja = forja;
        this.idSamurai = idSamurai;
    }

    @Override
    public void run() {
        forja.dejarKatana(idSamurai);  // El samurái deja su katana en la estantería
    }
}




