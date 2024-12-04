/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SimulacionBancaria;

/**
 *
 * @author carde
 */
// Clase HiloCliente que representa un cliente realizando transacciones
public class HiloCliente implements Runnable {
    private final Cuenta cuenta;   // Cuenta compartida
    private final int cantidad;   // Cantidad a mover (positiva o negativa)
    private final String tipoOperacion; // Tipo de operación: "Ingreso" o "Retirada"

    public HiloCliente(Cuenta cuenta, int cantidad, String tipoOperacion) {
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.tipoOperacion = tipoOperacion;
    }

    @Override
    public void run() {
        // Repetimos la operación muchas veces para forzar posibles errores de concurrencia
        for (int i = 0; i < 100; i++) {
            cuenta.hacerMovimiento(cantidad, tipoOperacion);
        }
    }
}
