/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Corredores;

/**
 *
 * @author carde
 */
public class Corredor extends Thread {
    private String nombre; // Nombre del corredor
    private TrofeoGanador trofeo; // Trofeo que se comparte entre todos los corredores
    private boolean esGanador = false; // Indica si el corredor ha ganado el trofeo

    // Constructor que recibe el nombre del corredor y el trofeo compartido
    public Corredor(String nombre, TrofeoGanador trofeo) {
        this.nombre = nombre;
        this.trofeo = trofeo;
    }

    // Método que ejecuta el comportamiento del corredor (avanzar en la carrera)
    @Override
    public void run() {
        // El corredor avanza 100 metros cada vez, hasta llegar a 900 metros
        for (int metros = 100; metros <= 900; metros += 100) {
            // Muestra en consola cuánto ha corrido el corredor
            System.out.println(nombre + ": " + metros + " m.");

            try {
                Thread.sleep(500); // Simula el tiempo necesario para avanzar 100 metros (0.5 segundos)
            } catch (InterruptedException e) {
                e.printStackTrace(); // Maneja cualquier error si el hilo es interrumpido
            }

            // Si el corredor ha llegado a los 900 metros y no ha ganado el trofeo
            if (metros == 900 && !esGanador && trofeo.estaDisponible()) {
                trofeo.obtenerTrofeo();
                esGanador = true; // Este corredor obtiene el trofeo
            }
        }
    }

    // Método para verificar si el corredor ha ganado el trofeo
    public boolean esGanador() {
        return esGanador;
    }

    // Método para obtener el nombre del corredor
    public String getNombre() {
        return nombre;
    }
}