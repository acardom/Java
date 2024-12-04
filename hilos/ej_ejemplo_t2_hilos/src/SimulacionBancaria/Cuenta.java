/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SimulacionBancaria;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carde
 */
// Clase Cuenta que controla el saldo de la cuenta bancaria
// Clase Cuenta que controla el saldo de la cuenta bancaria
public class Cuenta {
    private int saldo;                   // Saldo actual
    private final int saldoInicial;      // Saldo inicial para la comprobación
    private final List<String> operaciones; // Lista para registrar todas las operaciones

    public Cuenta(int saldo) {
        this.saldoInicial = saldo;
        this.saldo = saldo;
        this.operaciones = new ArrayList<>();
    }

    // Método sincronizado para realizar un movimiento (ingreso o retiro)
    public synchronized void hacerMovimiento(int cantidad, String tipoOperacion) {
        this.saldo += cantidad;
        String operacion = tipoOperacion + " " + cantidad + "€. Saldo actual: " + saldo + "€";
        operaciones.add(operacion);
        System.out.println(operacion);
    }

    // Comprobar si la simulación fue correcta
    public boolean esSimulacionCorrecta() {
        return this.saldo == this.saldoInicial;
    }

    // Obtener el saldo actual
    public int getSaldo() {
        return this.saldo;
    }

    // Mostrar todas las operaciones realizadas
    public void mostrarOperaciones() {
        System.out.println("=== Historial de Operaciones ===");
        for (String operacion : operaciones) {
            System.out.println(operacion);
        }
    }
}