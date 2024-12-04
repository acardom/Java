/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ej3;

/**
 *
 * @author carde
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConfiguracionesPajaros {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Configuración 1: Pájaros no educados");
        configuracion1();
        System.out.println("\nConfiguración 2: Pájaros educados por raza");
        configuracion2();
        System.out.println("\nConfiguración 3: Pájaros cantando por turnos");
        configuracion3();
        System.out.println("\nConfiguración 4: Pájaros con adiestrador");
        configuracion4();
        System.out.println("\nConfiguración 5: Pájaros independientes pero coordinados");
        configuracion5();
    }
// Configuración 1: Pájaros no educados

    private static void configuracion1() throws InterruptedException {
        List<Pajaro> pajaros = crearPajaros();
        ExecutorService executor = Executors.newFixedThreadPool(pajaros.size());
        long inicio = System.currentTimeMillis();
        for (Pajaro pajaro : pajaros) {
            executor.submit(() -> {
                try {
                    pajaro.run();
                    pajaro.cantar();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long fin = System.currentTimeMillis();
        System.out.println("Duración: " + (fin - inicio) / 1000.0 + " segundos");
    }
// Configuración 2: Pájaros educados por raza

    private static void configuracion2() throws InterruptedException {
        List<Pajaro> pajaros = crearPajaros();
        ExecutorService executor = Executors.newFixedThreadPool(pajaros.size());
        long inicio = System.currentTimeMillis();
        pajaros.forEach(p -> executor.submit(() -> {
            try {
                p.run();
                synchronized (p.getClass()) { // Un canto por raza
                    p.cantar();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long fin = System.currentTimeMillis();
        System.out.println("Duración: " + (fin - inicio) / 1000.0 + " segundos");
    }
// Configuración 3: Pájaros cantando por turnos

    private static void configuracion3() throws InterruptedException {
        List<Pajaro> pajaros = crearPajaros();
        ExecutorService executor = Executors.newFixedThreadPool(1); // Un hilo para asegurar turnos
        long inicio = System.currentTimeMillis();
        for (Pajaro pajaro : pajaros) {
            executor.submit(() -> {
                try {
                    pajaro.run();
                    pajaro.cantar();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long fin = System.currentTimeMillis();
        System.out.println("Duración: " + (fin - inicio) / 1000.0 + " segundos");
    }
// Configuración 4: Pájaros con adiestrador

    private static void configuracion4() throws InterruptedException {
        List<Pajaro> pajaros = crearPajaros();
        long inicio = System.currentTimeMillis();
        List<Thread> hilos = new ArrayList<>();
        for (Pajaro pajaro : pajaros) {
            Thread hilo = new Thread(() -> {
                try {
                    pajaro.run(); // Esperar 5 segundos antes de cantar
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            });
            hilos.add(hilo);
            hilo.start();
        }
// Simula el adiestrador
        Thread.sleep(8000); // Espera hasta que todos estén listos
        Random random = new Random();
        while (!hilos.isEmpty()) {
            int index = random.nextInt(hilos.size());
            Pajaro elegido = pajaros.get(index);
            synchronized (elegido) {
                try {
                    elegido.cantar();
                    hilos.remove(index);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        long fin = System.currentTimeMillis();
        System.out.println("Duración: " + (fin - inicio) / 1000.0 + " segundos");
    }
// Configuración 5: Pájaros independientes pero coordinados

    private static void configuracion5() throws InterruptedException {
        List<Pajaro> pajaros = crearPajaros();
        ExecutorService executor = Executors.newFixedThreadPool(pajaros.size());
        long inicio = System.currentTimeMillis();
        for (Pajaro pajaro : pajaros) {
            executor.submit(() -> {
                try {
                    pajaro.run();
                    pajaro.cantar();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long fin = System.currentTimeMillis();
        System.out.println("Duración: " + (fin - inicio) / 1000.0 + " segundos");
    }

    private static List<Pajaro> crearPajaros() {
        List<Pajaro> pajaros = new ArrayList<>();
        String[] razas = {"Periquito", "Loro", "Gorrión"};
        for (int i = 0; i < 10; i++) {
            pajaros.add(new Pajaro(razas[i % razas.length], i + 1));
        }
        return pajaros;
    }
}
