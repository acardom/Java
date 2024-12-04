/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BarberoSemaforo;

/**
 *
 * @author carde
 */
import java.util.concurrent.Semaphore;
import java.util.Random;

class BarberShop {

    private static final int NUM_WAITING_CHAIRS = 5;
    private static final Semaphore chairSemaphore = new Semaphore(NUM_WAITING_CHAIRS, true); // Sillas de espera
    private static final Semaphore barberSemaphore = new Semaphore(0, true); // El barbero está esperando clientes
    private static final Semaphore accessSemaphore = new Semaphore(1, true); // Control de acceso al sillón de cortar pelo

    public void cutHair() throws InterruptedException {
        Random rand = new Random();
        while (true) {
// Si hay clientes en la silla de cortar el pelo, el barbero debe atenderlos
            barberSemaphore.acquire();
            accessSemaphore.acquire();
            System.out.println("Barbero cortando el pelo...");
            Thread.sleep(rand.nextInt(6) * 1000 + 5000); // Tiempo de corte entre 5 y 10 segundos 
            System.out.println("Barbero limpiando el suelo...");
            Thread.sleep(5000); // Tiempo de limpieza
            accessSemaphore.release();
        }
    }

    public void newClient() throws InterruptedException {
        Random rand = new Random();
        if (chairSemaphore.tryAcquire()) {
            System.out.println("Cliente entrando y tomando asiento.");
            barberSemaphore.release(); // Llama al barbero si está disponible
        } else {
            System.out.println("Cliente no tiene silla, dando una vuelta...");
            Thread.sleep(rand.nextInt(16) * 1000 + 5000); // Da una vuelta entre 5 y 20 segundos 
// Si no hay silla tras 2 intentos, el cliente se va

            if (rand.nextBoolean()) {
                System.out.println("Cliente se va porque no hay sillas.");
            }
        }
    }
}

class BarberThread extends Thread {

    private final BarberShop shop;

    public BarberThread(BarberShop shop) {
        this.shop = shop;
    }

    public void run() {
        try {
            shop.cutHair();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ClientThread extends Thread {

    private final BarberShop shop;

    public ClientThread(BarberShop shop) {
        this.shop = shop;
    }

    public void run() {
        try {
            shop.newClient();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class BarberShopSemaphore {

    public static void main(String[] args) {
        BarberShop shop = new BarberShop();
        Thread barber = new BarberThread(shop);
        barber.start();
        for (int i = 0; i < 20; i++) {
            Thread client = new ClientThread(shop);
            client.start();
        }
    }
}
