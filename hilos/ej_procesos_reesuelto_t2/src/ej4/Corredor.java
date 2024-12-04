/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ej4;

/**
 *
 * @author carde
 */
public class Corredor extends Thread {

    private String nombre;
    private TrofeoGanador trofeo;
    private boolean esGanador = false;

    public Corredor(String nombre, TrofeoGanador trofeo) {
        this.nombre = nombre;
        this.trofeo = trofeo;
    }

    @Override
    public void run() {
        try {
// Simulamos el tiempo de carrera con un bucle
            for (int i = 1; i <= 10; i++) {
                System.out.println(nombre + " corriendo... Paso " + i);
                Thread.sleep(500); // Hacemos que el corredor "corra" con una pausa de medio segundo
            }
// Verificamos si el trofeo está disponible
            if (trofeo.estaDisponible()) {
// El corredor intenta obtener el trofeo
                esGanador = trofeo.obtenerTrofeo();
                if (esGanador) {
                    System.out.println(nombre + " ha ganado la carrera y ha obtenido el trofeo!");
                } else {
                    System.out.println(nombre + " ha perdido la carrera. El trofeo ya fue obtenido.");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("El corredor " + nombre + " fue interrumpido.");
        }
    }
}
