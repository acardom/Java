/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2.ejercicios.act_2;

/**
 *
 * @author Alberto Cárdeno Domínguez
 */
import examen2.Comun;
import java.util.concurrent.Semaphore;
import java.util.concurrent.LinkedBlockingQueue;

public class Puente {
    private Semaphore semaforo; // Para controlar el acceso
    private LinkedBlockingQueue<String> colaEsperando; // Para gestionar las personas esperando
    private int tiempoCruce = 2000; // 2 segundos para cruzar el puente
    private int pasando; //para ver cuantos paaron ya

    // Constructor
    public Puente(int capacidad) {
        this.semaforo = new Semaphore(capacidad); // Capacidad máxima del puente
        this.colaEsperando = new LinkedBlockingQueue<>();    
    }

    // Método para intentar cruzar el puente
    public void cruzarPuente(int idPersona, String tipo) {
        try {

            // Añadir la persona a la cola de espera
            colaEsperando.put(String.valueOf(idPersona));

            // Adquirir permiso para cruzar el puente
            semaforo.acquire();
            System.out.println(Comun.FechaActual() + " Persona con ID: " + idPersona + " está cruzando el puente."+ 
                               " Total de personas esperando en el puente: " + (colaEsperando.size()+pasando));

            pasando--;//restamos los que pasaron ya
            // Simulamos el tiempo que tarda en cruzar el puente
            Thread.sleep(tiempoCruce);

        } catch (InterruptedException e) {
            
        } finally {
            // Liberar el permiso para que otra persona pueda cruzar
            semaforo.release();
        }
    }
}



