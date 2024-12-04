/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_1_1;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
public class main {
    public static void main(String[] args) {
        // Capacidad máxima de la sala (máximo 5 samuráis)
        int capacidadSala = 5;
        // Número total de samuráis que quieren meditar
        int numeroSamurais = 20;

        // Crear la sala de meditación
        SalaMeditacion salaMeditacion = new SalaMeditacion(capacidadSala);

        // Crear y lanzar hilos para cada samurái
        for (int i = 1; i <= numeroSamurais; i++) {
            Samurai samurai = new Samurai(salaMeditacion, "Samurai-" + i);
            samurai.start();
        }
    }
}
