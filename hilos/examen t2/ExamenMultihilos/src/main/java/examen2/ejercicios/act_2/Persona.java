/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_2;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */


public class Persona extends Thread {
    private Puente puente;
    private int idPersona; // ID persona
    private String tipo; // Tipo: Samurai o Campesino

    // Constructor
    public Persona(Puente puente, int idPersona, String tipo) {
        this.puente = puente;
        this.idPersona = idPersona;
        this.tipo = tipo;
    }

    // Método que se ejecuta cuando el hilo empieza
    @Override
    public void run() {
        puente.cruzarPuente(idPersona, tipo); //intenta cruzar el puente
    }
}


