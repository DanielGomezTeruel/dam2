package problemas.restaurante;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ElRestauranteConcurrente {
    public static void main(String[] args) {
        Random random = new Random();

        BlockingQueue<Pedido> pedidosClientes = new ArrayBlockingQueue<>(100);

        BlockingQueue<Pedido> mesaPedidos = new ArrayBlockingQueue<>(10);

        List<Thread> cocineros = new ArrayList<>();

        ExecutorService camareros = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            camareros.submit(() -> {
                for (boolean trabajando=true;trabajando;) {
                    try {
                        Pedido pedido = pedidosClientes.take();

                        System.out.println(
                                "Camarero toma pedido de "
                                        + pedido.getInfoCliente()
                                        + ": "
                                        + pedido.getTipoPlato()
                        );

                        Thread.sleep(1000);

                        mesaPedidos.put(pedido);

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
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        trabajando = false;
                    }
                }
            },"Cocinero "+i);
            cocineros.add(cocinero);
        }
        cocineros.forEach(Thread::start);


        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.startVirtualThread((getPedido(random, pedidosClientes))).setName( "Cliente "+i);
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

    }

    private static Runnable getPedido(Random random, BlockingQueue pedidosClientes) {
        return () -> {
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
            Pedido pedido = new Pedido(Thread.currentThread().getName(), plato);

            try {
                pedidosClientes.put(pedido);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };
    }
}
