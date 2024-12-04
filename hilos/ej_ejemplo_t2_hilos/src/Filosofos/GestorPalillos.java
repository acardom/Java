/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Filosofos;

/**
 *
 * @author carde
 */
public class GestorPalillos {
    
boolean palilloLibre[];
public GestorPalillos(int numPalillos){
palilloLibre=new boolean[numPalillos];
for (int i=0; i<numPalillos; i++){
            palilloLibre[i] = true;
        } //Fin del for
    } //Fin del constructor

    public synchronized boolean
            intentarCogerPalillos(int pos1, int pos2) {
        boolean seConsigue = false;
        if ((palilloLibre[pos1])
                && (palilloLibre[pos2])) {
            palilloLibre[pos1] = false;
            palilloLibre[pos2] = false;
            seConsigue = true;
        } //Fin del if
        return seConsigue;
    }

    public void liberarPalillos(int pos1, int pos2) {
        palilloLibre[pos1] = true;
        palilloLibre[pos2] = true;
    }
}
