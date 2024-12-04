/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ej4;

/**
 *
 * @author carde
 */
public class Carrera {

    public static void main(String[] args) {
// Creamos el trofeo
        TrofeoGanador trofeo = new TrofeoGanador();
// Creamos los corredores
        Corredor corredor1 = new Corredor("Corredor 1", trofeo);
        Corredor corredor2 = new Corredor("Corredor 2", trofeo);
// Mostrar mensaje de inicio de carrera
        System.out.println("¡La carrera ha comenzado!");
// Iniciar los hilos de los corredores
        corredor1.start();
        corredor2.start();
    }
}
