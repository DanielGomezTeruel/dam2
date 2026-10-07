package ejerciciosJava25;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Ejercicio8_2 {



    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(4,Ejercicio8_2::accionAlLlegarTodos);
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Thread hilo = new Thread(()->{
                for (int j = 0; j < 3; j++) {
                    try {
                        Thread.sleep((long) (Math.random()*1000));
                        System.out.println(Thread.currentThread().getName()+" ha completado la fase "+(j+1));
                        barrier.await();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }, "hilo "+i);
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }

    private static void accionAlLlegarTodos(){System.out.println("Fase completada, todos avanzan");}
}
