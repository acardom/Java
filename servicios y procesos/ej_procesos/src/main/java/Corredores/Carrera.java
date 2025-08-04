/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Corredores;

/**
 *
 * @author carde
 */
public class Carrera {
    public static void main(String[] args) {
        System.out.println("¡Comienza la carrera!");

        // Crear el trofeo compartido entre los corredores
        TrofeoGanador trofeo = new TrofeoGanador();

        // Crear los corredores, pasando el nombre y el trofeo como parámetros
        Corredor corredor1 = new Corredor("coyote", trofeo);
        Corredor corredor2 = new Corredor("correcaminos", trofeo);

        // Iniciar la carrera (hilos)
        corredor1.start(); // El corredor 1 empieza a correr
        corredor2.start(); // El corredor 2 empieza a correr

        // Esperar a que ambos corredores terminen de correr
        try {
            corredor1.join(); // Espera a que el corredor 1 termine
            corredor2.join(); // Espera a que el corredor 2 termine
        } catch (InterruptedException e) {
            e.printStackTrace(); // Manejo de excepción si ocurre un error con el join
        }

        // Mostrar el resultado de la carrera, quien ha ganado y quien ha perdido
        if (corredor1.esGanador()) {
            System.out.println(corredor1.getNombre() + " ha ganado el trofeo.");
            System.out.println(corredor2.getNombre() + " no logró ganar el trofeo.");
        } else if (corredor2.esGanador()) {
            System.out.println(corredor2.getNombre() + " ha ganado el trofeo.");
            System.out.println(corredor1.getNombre() + " no logró ganar el trofeo.");
        }

        System.out.println("La carrera ha terminado.");
    }
}