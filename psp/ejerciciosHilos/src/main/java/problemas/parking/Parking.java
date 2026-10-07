package problemas.parking;


import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Parking {

    public static final ReentrantLock barreraEntrada = new ReentrantLock();
    public static final ReentrantLock barreraSalida = new ReentrantLock();

    public static final Random random = new Random();

    public static final int PLAZAS = 20;
    public static final int PLAZAS_VIP = 5;
    public static final int NUM_COCHES = 100;

    public final BlockingQueue<TipoVehiculo> cola = new ArrayBlockingQueue(10);
    public final Semaphore parking = new Semaphore(PLAZAS);
    public final Semaphore parkingVip = new Semaphore(PLAZAS_VIP);

    public final ExecutorService executor = Executors.newFixedThreadPool(20);

    public final AtomicInteger contadorCochesQueAparcan = new AtomicInteger(0);
    public final AtomicInteger contadorCochesVipQueAparcan = new AtomicInteger(0);
    public final AtomicInteger contadorCochesQueSeVan = new AtomicInteger(0);



    public void runCoche(TipoVehiculo tipoVehiculo){
        if(TipoVehiculo.VIP.equals(tipoVehiculo)){
            System.out.println("Entra coche VIP");
            runCocheVip(tipoVehiculo);
        }else {
            System.out.println("Entra coche normal");
            runCocheNormal(tipoVehiculo);
        }
    }

    public void runCocheNormal(TipoVehiculo tipoVehiculo)
    {
        if (parking.tryAcquire()) {
                System.out.println("Coche "+tipoVehiculo+" aparca");
                try {
                    Thread.sleep((long) (Math.random() * 20000+10000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                contadorCochesQueAparcan.incrementAndGet();
                System.out.println("Coche "+tipoVehiculo+" abandona su sitio");
                parking.release();
        } else {
                IO.println("Coche "+tipoVehiculo+" se va");
                contadorCochesQueSeVan.incrementAndGet();
        }

    }

    public void runCocheVip(TipoVehiculo tipoVehiculo){
        if(parkingVip.tryAcquire())
        {
            System.out.println("Coche "+tipoVehiculo+" aparca en VIP");
            try {
                Thread.sleep((long) (Math.random() * 20000+10000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            contadorCochesQueAparcan.incrementAndGet();
            System.out.println("Coche "+tipoVehiculo+" abandona su sitio");
            parkingVip.release();
        }else{
            System.out.println("Coche "+tipoVehiculo+" se va al parking normal");
            runCocheNormal(tipoVehiculo);
        }
    }

    public void crearCoches()
    {
        for (int i = 0; i < NUM_COCHES; i++) {
            TipoVehiculo tipo=random.nextInt(1,3)==1?TipoVehiculo.NORMAL:TipoVehiculo.VIP;

            executor.submit(() ->{
                    barreraEntrada.lock();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    barreraEntrada.unlock();

                    this.runCoche(tipo);

                    barreraSalida.lock();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    barreraSalida.unlock();

            });
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
        IO.println("Coches VIP que aparcan: "+contadorCochesVipQueAparcan);

    }
}
