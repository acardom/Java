/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ej4;

/**
 *
 * @author carde
 */
public class TrofeoGanador {

    private boolean estaElTrofeo = true;
// Método sincronizado que verifica si el trofeo está disponible

    public synchronized Boolean estaDisponible() {
        return estaElTrofeo;
    }
// Método sincronizado que permite obtener el trofeo

    public synchronized Boolean obtenerTrofeo() {
        if (estaElTrofeo) {
            estaElTrofeo = false; // El trofeo ya ha sido tomado
            return true; // El corredor obtiene el trofeo
        }
        return false; // El trofeo ya no está disponible
    }
}
