import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class ElRestauranteConcurrente {
    static AtomicLong ganancias = new AtomicLong(0);

    static List<Duration> tiemposDeEspera = new ArrayList<>();

    public static void main(String[] args) {



        Random random = new Random();

        Semaphore mesas = new Semaphore(25);

        BlockingQueue<Pedido> pedidosClientesIda = new ArrayBlockingQueue<>(10);

        BlockingQueue<Pedido> mesaPedidos = new ArrayBlockingQueue<>(10);

        BlockingQueue<Pedido> platosHechos = new ArrayBlockingQueue<>(10);

        List<Thread> cocineros = new ArrayList<>();

        ExecutorService camareros = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            camareros.submit(() -> {
                for (boolean trabajando=true;trabajando;) {
                    try {
                        if(platosHechos.peek()!=null){
                            Pedido pedido= platosHechos.take();
                            System.out.println(
                                    "Camarero entrega de "
                                            + pedido.getInfoCliente()
                                            + ": "
                                            + pedido.getTipoPlato()
                            );

                            Thread.sleep(500);

                            pedido.listo();

                        }else{
                            Pedido pedido = pedidosClientesIda.take();

                            System.out.println(
                                    "Camarero toma pedido de "
                                            + pedido.getInfoCliente()
                                            + ": "
                                            + pedido.getTipoPlato()
                            );

                            Thread.sleep(500);

                            mesaPedidos.put(pedido);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        trabajando = false;
                    }
                }
            });
        }

        for (int i = 0; i < 3; i++) {
            Thread cocinero = new Thread(() -> {
                for (boolean trabajando=true;trabajando;) {
                    try {
                        Pedido pedido = mesaPedidos.take();

                        System.out.println(Thread.currentThread().getName() + " está cocinando "+pedido.getTipoPlato());
                        Thread.sleep(pedido.getTipoPlato().getTiempoMs());
                        System.out.println(Thread.currentThread().getName() + " termina de cocinar "+pedido.getTipoPlato());
                        platosHechos.put(pedido);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        trabajando = false;
                    }
                }
            },"Cocinero "+i);
            cocineros.add(cocinero);
        }
        cocineros.forEach(Thread::start);


        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.startVirtualThread(() -> {
                try {
                    if (mesas.tryAcquire(10000, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + " se sienta");
                        LocalDateTime startTimeCliente = LocalDateTime.now();

                        TipoPlato plato = TipoPlato.ENSALADA;
                        switch (random.nextInt(1, 5)) {
                            case 2:
                                plato = TipoPlato.PASTA;
                                break;

                            case 3:
                                plato = TipoPlato.PIZZA;
                                break;

                            case 4:
                                plato = TipoPlato.CARNE;
                                break;
                        }

                        CountDownLatch esperaPedido = new CountDownLatch(1);
                        Pedido pedido = new Pedido(Thread.currentThread().getName(), plato, esperaPedido);


                        pedidosClientesIda.put(pedido);
                        esperaPedido.await();
                        System.out.println(Thread.currentThread().getName() + " paga y se va");
                        tiemposDeEspera.add(Duration.between(startTimeCliente, LocalDateTime.now()));
                        pagar(plato.getPrecio());
                        mesas.release();

                    }else{
                        System.out.println(Thread.currentThread().getName() + " se harta de esperar y se va sin pedir");
                    }
                    }catch(InterruptedException e){
                        throw new RuntimeException(e);
                    }            }).setName( "Cliente "+i);
        }

        camareros.shutdown();

        try {
            camareros.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (!mesaPedidos.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        cocineros.forEach(Thread::interrupt);

        cocineros.forEach(cocinero->{
            try {
                cocinero.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Ganancias totales: "+ganancias+"€");

        System.out.println("Tiempo promedio de espera: "+tiemposDeEspera.stream().mapToLong(Duration::toSeconds).average().orElse(0.0)+" segundos");

    }

    private static void pagar( long cantidad) {
        ganancias.addAndGet(cantidad);
    }

}
