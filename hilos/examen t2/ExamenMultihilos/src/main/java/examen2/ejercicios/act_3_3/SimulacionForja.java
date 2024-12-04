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
public class SimulacionForja {
    public static void main(String[] args) {
        int capacidadEstanteria = 5; // Capacidad de la estantería (5 huecos)
        int capacidadSalaEspera = 4; // Capacidad de la sala de espera (4 samuráis)
        int numeroSamurais = 10; // Número total de samuráis
        int numeroMaestrosArmeros = 2; // Número de maestros armeros (2)
        int numeroPorteros = 1; // Número de porteros

        // Crear la forja
        Forja forja = new Forja(capacidadEstanteria, capacidadSalaEspera);

        // Crear y lanzar hilos para cada samurái
        for (int i = 1; i <= numeroSamurais; i++) {
            Samurai samurai = new Samurai(forja, i);
            samurai.start();
        }

        // Crear y lanzar hilos para los porteros
        for (int i = 1; i <= numeroPorteros; i++) {
            Portero portero = new Portero(forja, i);
            portero.start();
        }

        // Crear y lanzar hilos para cada maestro armero
        for (int i = 1; i <= numeroMaestrosArmeros; i++) {
            MaestroArmero maestroArmero = new MaestroArmero(forja, i);
            maestroArmero.start();
        }
    }
}


