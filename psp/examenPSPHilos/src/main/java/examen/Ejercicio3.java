package examen;
/*
 PROBLEMA 3 - El Restaurante Concurrente de clase dado como solucion
 Patron Productor-Consumidor con 5 camareros (productores), 3 cocineros
 (consumidores) y 100 clientes (hilos virtuales).
 con los siguientes cambios
 1Pto El cierre del resturante deberá de ser más controlado, y no salir hasta parar a los cocineros y camareros.
 0,5 Pto  Tb pon precio a los platos y acumula cuanto ha ganado el restaurente en el servicio.
 2Pto Flujo de un cliente (hilo virtual):
   llega -> espera mesa libre durante un tiempo(elige el tiempo para que no se vayan todos) -> se sienta si la consigue y si no se va-> pide -> espera el plato
         -> se lo come -> libera la mesa -> se va
 2Pto Los camareros tienen PRIORIDAD para servir platos: antes de tomar un pedido nuevo
     o de meter uno en cocina, siempre miran si hay platos listos para entregarlos a los clientes.
 2Pto Cambia los clientes por un grupo de clientes.  Flujo de un grupo:
       la primera parte hasta que se sientan es igual que un cliente solo -> el camarero toma la comanda (un plato por persona)
             -> espera a que le sirvan TODOS los platos -> comen -> liberan la mesa -> se van

*/


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Ejercicio3 {

    enum TipoPlato {
        ENSALADA(2000, 10),
        PASTA(3000, 12),
        PIZZA(4000, 15),
        CARNE(5000,22);

        final int tiempoMs;
        final int precio;
        TipoPlato(int tiempoMs, int precio) {
            this.tiempoMs = tiempoMs;
            this.precio = precio;
        }
        static TipoPlato aleatorio(Random r) {
            return values()[r.nextInt(values().length)];
        }
    }

    record Pedido(int cliente, TipoPlato plato, long horaCreacionMs, CountDownLatch servido) {
        void servir(){
            servido.countDown();
        }
    }

    // --- Constantes del enunciado ---
    static final int COCINEROS = 3;
    static final int CAMAREROS = 5;
    static final int CLIENTES = 100;
    static final int CAPACIDAD_MESA = 10;
    static final int CANTIDAD_MESAS = 20;
    static final int CAPACIDAD_PLATOS_LISTOS = 10;
    static final long LLEGADA_CLIENTE_MS = 500;
    static final long TIEMPO_CAMARERO_MS = 1000;
    static final long TIEMPO_COMIENDO_MS = 200;


    // --- Recursos compartidos ---
    static final BlockingQueue<Pedido> mesa = new ArrayBlockingQueue<>(CAPACIDAD_MESA);
    static final Semaphore mesas = new Semaphore(CANTIDAD_MESAS);
    static final CountDownLatch clientesAtendidos = new CountDownLatch(CLIENTES);
    // Cola intermedia clientes -> camareros (ilimitada: los clientes nunca se quedan sin atender)
    static final BlockingQueue<Pedido> colaClientes = new ArrayBlockingQueue<>(CLIENTES);
    // Cola de pedidos completos -> Camareros recogen platos y los entregan a los clientes
    static final BlockingQueue<Pedido> platosListos = new ArrayBlockingQueue<>(CAPACIDAD_PLATOS_LISTOS);

    //Booleano para el cierre
    static boolean abierto = true;


    // --- Estadisticas ---
    static final AtomicInteger platosServidos = new AtomicInteger();
    static final AtomicInteger ingresosTotales = new AtomicInteger();
    static final AtomicLong esperaTotalMs = new AtomicLong();
    static final AtomicInteger mesaLlenaCount = new AtomicInteger();
    static final AtomicLong cocinerosEsperandoMs = new AtomicLong();

    //--Listas personas--
    static final List<Thread> camareros = new ArrayList<>();
    static final List<Thread> cocineros = new ArrayList<>();
    static final List<Thread> clientes = new ArrayList<>();


    static String ahora() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== RESTAURANTE CONCURRENTE ===");
        System.out.println("Iniciando servicio con " + COCINEROS + " cocineros y "
                + CAMAREROS + " camareros...\n");

        // --- Cocineros (consumidores) ---
        for (int i = 1; i <= COCINEROS; i++) {
            final int id = i;
            cocineros.add(
            Thread.ofVirtual().name("cocinero-" + id).start(() -> {
                while (abierto) {
                    try {

                        long t0 = System.currentTimeMillis();
                        Pedido p = mesa.poll(); // intenta coger un pedido
                        if (p != null) {
                            cocinerosEsperandoMs.addAndGet(System.currentTimeMillis() - t0);

                            try {
                                Thread.sleep(p.plato.tiempoMs); // cocina el plato
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            esperaTotalMs.addAndGet(System.currentTimeMillis() - p.horaCreacionMs());
                            platosServidos.incrementAndGet();
                            System.out.println("[" + ahora() + "] Cocinero-" + id + " termina "
                                    + p.plato + " para Cliente-" + p.cliente());

                            platosListos.put(p);
                            System.out.println("[" + ahora() + "] Cocinero-" + id + " termina pedido de Cliente-" + p.cliente());
                            }
                        } catch(InterruptedException e){
                            Thread.currentThread().interrupt(); // cierre del restaurante
                        }
                }
            })
            );
        }

        // --- Camareros (productores) ---
        for (int i = 1; i <= CAMAREROS; i++) {
            final int id = i;
            camareros.add(
            Thread.ofVirtual().name("camarero-" + id).start(() -> {

                    while (abierto) {
                        try {
                            //Prioridad para los platos ya cocinados
                            Pedido listo = platosListos.poll();
                            if (listo != null) {
                                listo.servir();
                            } else {
                                Pedido p = colaClientes.poll(); // revisa si hay comandas de clientes, pero no se queda bloqueado
                                if (p != null) {
                                    Thread.sleep(TIEMPO_CAMARERO_MS); // toma el pedido
                                    System.out.println("[" + ahora() + "] Camarero-" + id
                                            + " toma pedido " + p.plato);
                                    if (mesa.remainingCapacity() == 0) {
                                        mesaLlenaCount.incrementAndGet();
                                    }
                                    mesa.put(p); // espera si la mesa esta llena
                                }
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();

                        }
                    }

            })
            );
        }

        // --- Clientes (hilos virtuales, llegan cada 500 ms) ---
            for (int i = 1; i <= CLIENTES; i++) {
                final int id = i;
                Thread.sleep(LLEGADA_CLIENTE_MS);
                clientes.add(Thread.startVirtualThread (() -> {
                            clientesAtendidos.countDown();
                            try {
                                if(mesas.tryAcquire(2000, TimeUnit.MILLISECONDS)) {
                                        System.out.println("[" + ahora() + "] Grupo-" + String.format("%03d", id)
                                                + " se sienta");

                                        try {
                                            Random random = new Random();

                                            int tamanyoDelGrupo = random.nextInt(1,5);
                                            CountDownLatch servido = new CountDownLatch(tamanyoDelGrupo);
                                            List<TipoPlato> platosPedidos = new ArrayList<>();
                                            for (int j = 0; j < tamanyoDelGrupo; j++) {
                                                platosPedidos.add(TipoPlato.aleatorio(random));
                                                colaClientes.put(new Pedido(id, platosPedidos.get(j), System.currentTimeMillis(), servido));
                                            }

                                            servido.await();

                                            Thread.sleep(TIEMPO_COMIENDO_MS);

                                            platosPedidos.forEach(plato -> {
                                                System.out.println("[" + ahora() + "] Grupo-" + String.format("%03d", id)
                                                        + " come " + plato);
                                                ingresosTotales.addAndGet(plato.precio);
                                            });

                                            System.out.println("[" + ahora() + "] Grupo-" + String.format("%03d", id)
                                                    + " paga y se va");
                                            mesas.release();
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                        }
                                    }else{
                                    System.out.println("[" + ahora() + "] Grupo-" + String.format("%03d", id)
                                            + " se cansa de esperar y se va sin pedir");
                                    }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        })
                );
            }


        clientesAtendidos.await(); // el restaurante no cierra hasta atender a los 100

        clientes.forEach(cliente ->{
            try {
                cliente.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        abierto = false;


        camareros.forEach(camarero->{
            try {
                camarero.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


        //cocineros.forEach(Thread::interrupt); //Necesitan interrupt porque take es bloqueante
        cocineros.forEach(cocinero->{
            try {
                cocinero.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


        System.out.println("\n--- ESTADISTICAS FINALES ---");
        System.out.println("Clientes atendidos: " + CLIENTES + "/" + CLIENTES + " ✅");
        System.out.println("Platos servidos: " + platosServidos.get());
        System.out.println("Tiempo promedio de espera: "
                + (esperaTotalMs.get() / 1000.0 / CLIENTES) + "s");
        System.out.println("Ingresos totales del servicio: "+ingresosTotales);
        System.out.println("Mesa llena (veces): " + mesaLlenaCount.get());
        System.out.println("Cocineros esperando (tiempo acumulado): "
                + (cocinerosEsperandoMs.get() / 1000) + "s total");
        System.out.println("Eficiencia: el sistema soporta " + CAMAREROS + " pedidos/s en entrada"
                + " y " + COCINEROS + " platos en paralelo en cocina.");

    }

}
