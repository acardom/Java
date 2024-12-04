/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_2;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
public class SimulacionPuente {
    public static void main(String[] args) {
        // Capacidad máxima del puente (máximo 3 personas)
        int capacidadPuente = 3;
        // Número total de personas (samuráis + campesinos)
        int numeroPersonas = 10;

        // Crear el puente
        Puente puente = new Puente(capacidadPuente);

        // Crear y lanzar hilos para cada persona (samuráis y campesinos)
        for (int i = 1; i <= numeroPersonas; i++) {
            // Alternar entre samuráis y campesinos
            String tipo = (i % 2 == 0) ? "Samurai" : "Campesino"; // Ejemplo: samuráis en las posiciones pares
            Persona persona = new Persona(puente, i, tipo); // Usar solo el ID numérico
            persona.start();
        }
    }
}

