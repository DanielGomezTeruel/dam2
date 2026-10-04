/*Ejercicio 5.1 — "Parking con plazas limitadas"
Un parking tiene 3 plazas. Modela cada plaza con un Semaphore(3).
Simula 10 coches (hilos) que intentan entrar (acquire()),
permanecen un tiempo aleatorio y salen (release()).
Imprime cuándo un coche espera porque no hay plazas.*/

import java.util.concurrent.Semaphore;

public class Ejercicio5_1 {
    public static void main(String[] args) throws InterruptedException {
        Semaphore plazas = new Semaphore(3);
        int NUM_COCHES = 10;
        for (int i = 0; i < 10; i++) {
            Thread coche = new Thread(()->{
                for (boolean noEncuentraSitio=true;noEncuentraSitio;) {
                    if (plazas.tryAcquire()) {

                        System.out.println(Thread.currentThread().getName() + " aparca");
                        try {
                            Thread.sleep((long) (Math.random() * 300));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        plazas.release();
                        System.out.println(Thread.currentThread().getName() + " se va");
                        noEncuentraSitio=false;
                    } else {
                        System.out.println(Thread.currentThread().getName() + " no encuentra sitio");
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            },"coche "+i);
            coche.start();
        }
    }
}
