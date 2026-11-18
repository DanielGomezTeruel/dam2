import domain.Chapa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class MainCarreraChapas {

    //Constantes enunciado
    static final int NUMERO_JUGADORES = 3;
    static final int META = 100;
    static final int TAMAÑO_COLA_MENSAJES = 20;

    //Recursos compartidos
    static CyclicBarrier turnoBarrier;
    static BlockingQueue<String> colaMensajes = new ArrayBlockingQueue<>(TAMAÑO_COLA_MENSAJES);
    static CountDownLatch carreraOver = new CountDownLatch(1);


    //Listas personas
    static List<Thread> hilosJugadores = new ArrayList<>();
    static List<Chapa> chapas = new ArrayList<>();

    //Otros
    static boolean corriendo = true;
    static boolean mandandoMensajes = true;
    static Random r = new Random();

    static void main() {


        Callable<String> resumen = () -> {
            StringBuilder sb = new StringBuilder();
            chapas.forEach(chapa -> sb.append("Chapa " + chapa.getId() + " está en " + chapa.getPosicion() + "\n"));
            return sb.toString();
        };

        turnoBarrier = new CyclicBarrier(NUMERO_JUGADORES, () -> {
            if (!chapas.stream().filter(chapa -> chapa.getPosicion() >= META).toList().isEmpty()) {
                carreraOver.countDown();
                corriendo = false;
            } else {

                try {
                    colaMensajes.put(resumen.call());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                chapas.forEach(chapa -> chapa.setMovido(false));
            }
        });

        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            final int id = i + 1;
            hilosJugadores.add(
                    Thread.ofPlatform().name("hiloJugador-" + id).start(() -> {
                        Chapa j = new Chapa(id);
                        chapas.add(j);
                        while (corriendo) {
                            try {
                                if (!j.isMovido()) {
                                    j.mover(r);
                                    j.setMovido(true);
                                    colaMensajes.put("Jugador " + j.getId() + " está en la posición " + j.getPosicion());

                                }
                                turnoBarrier.await();
                            } catch (InterruptedException | BrokenBarrierException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    })
            );
        }

        Thread juez = Thread.ofVirtual().name("Juez").start(() -> {
            while (mandandoMensajes) {
                String mensaje = colaMensajes.poll();
                if (mensaje != null) {
                    if (mensaje.startsWith("Ganador")) {
                        mandandoMensajes = false;
                    }
                    if (mensaje.startsWith("Chapa")) {
                        System.out.println("--- Resumen turno ---");
                        System.out.println(mensaje);
                        System.out.println("- - - - - - - -");
                    } else {
                        System.out.println(mensaje);
                    }
                }
            }
        });

        try {
            carreraOver.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        hilosJugadores.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            Chapa ganador = chapas.stream().sorted(Comparator.comparingInt(Chapa::getPosicion)).toList().getLast();
            colaMensajes.put("Ganador: jugador " + ganador.getId() + " con posicion " + ganador.getPosicion());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            juez.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


}
