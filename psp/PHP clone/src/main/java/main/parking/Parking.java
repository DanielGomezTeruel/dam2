package main.parking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Parking {

    public static final int PLAZAS = 10;
    public static final int NUM_COCHES = 100;
    public final Semaphore parking = new Semaphore(PLAZAS);
    public final ExecutorService executor = Executors.newFixedThreadPool(20);

    public final AtomicInteger contadorCochesQueAparcan = new AtomicInteger(0);
    public final AtomicInteger contadorCochesQueSeVan = new AtomicInteger(0);


    public void runCoche(Semaphore semaphore)
    {
        if(semaphore.tryAcquire())
        {
            try {
                Thread.sleep((long) (Math.random()*1500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            contadorCochesQueAparcan.incrementAndGet();
        }else
        {
            IO.println("Me voy");
            contadorCochesQueSeVan.incrementAndGet();
        }
    }

    public void crearCoches()
    {
        for (int i = 0; i < NUM_COCHES; i++) {
            executor.submit(() -> this.runCoche(parking));
        }
    }

    public void esperarFinal(){
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void debug(){
        IO.println("Coches que se van: "+contadorCochesQueSeVan);
        IO.println("Coches que aparcan: "+contadorCochesQueAparcan);
    }
}
