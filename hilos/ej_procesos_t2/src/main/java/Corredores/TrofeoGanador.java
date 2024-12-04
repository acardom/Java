/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Corredores;

/**
 *
 * @author carde
 */
public class TrofeoGanador {
    // Atributo que indica si el trofeo está disponible o no
    private Boolean estaElTrofeo = true;

    // Método sincronizado que devuelve si el trofeo está disponible
    public synchronized boolean estaDisponible() {
        return estaElTrofeo;
    }

    // Método sincronizado que intenta obtener el trofeo, devolviendo true si se lo lleva un corredor
    public synchronized boolean obtenerTrofeo() {
        if (estaElTrofeo) {
            estaElTrofeo = false; // Marca el trofeo como no disponible
            return true; // El corredor ha ganado el trofeo
        }
        return false; // El trofeo ya ha sido tomado
    }
}