package examen2.ejercicos.act_1_2;

import examen2.ejercicios.act_1_1.*;
/**
 *
 * @author Alberto Cárdeno Domínguez
 */
// Clase que representa un samurái (hilo)
class Samurai extends Thread {
    private SalaMeditacion salaMeditacion;
    private String nombre;

    // Constructor
    public Samurai(SalaMeditacion salaMeditacion, String nombre) {
        this.salaMeditacion = salaMeditacion;
        this.nombre = nombre;
    }

    // Método que se ejecuta cuando el hilo empieza
    @Override
    public void run() {
        salaMeditacion.salaMeditacion(nombre); // El samurái intenta meditar
    }
}

