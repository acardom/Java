/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grandesalmacenes;

/**
 *
 * @author carde
 */
public class Almacen {

    private int numProductos;

    public Almacen(int nProductos) {
        this.numProductos = nProductos;
    }

    public boolean cogerProducto() {
        if (this.numProductos > 0) {
            this.numProductos--;
            return true;
        }
        return false;
    }
}
