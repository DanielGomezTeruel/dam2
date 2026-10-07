package ejerciciosJava25;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Ejercicio5_2 {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger contadorVisitas = new AtomicInteger(0);

        List<Thread> visitas = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread visita = new Thread(()->{
                try {
                    Thread.sleep((long)(50+Math.random()*100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                contadorVisitas.addAndGet(1);
            });
            visitas.add(visita);
        }

        visitas.forEach(Thread::start);

        visitas.forEach(hilo->{
            try {
                hilo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Visitas totales: "+contadorVisitas);
    }
}
