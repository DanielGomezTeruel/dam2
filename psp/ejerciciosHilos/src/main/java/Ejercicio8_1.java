import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class Ejercicio8_1 {

    public static void main(String[] args) throws InterruptedException {

        int numCorredores = 5;

        CountDownLatch corredoresListos = new CountDownLatch(numCorredores);

        CountDownLatch salida = new CountDownLatch(1);

        List<Thread> corredores = new ArrayList<>();

        for (int i = 0; i < numCorredores; i++) {

            final int numero = i + 1;

            Thread corredor = new Thread(() -> {

                try {

                    Thread.sleep((int)(500+Math.random()*1000));

                    System.out.println(Thread.currentThread().getName() + " está preparado.");

                    corredoresListos.countDown();

                    salida.await();

                    System.out.println(Thread.currentThread().getName() + " sale");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }, "corredor-" + numero);

            corredores.add(corredor);
        }

        corredores.forEach(Thread::start);

        corredoresListos.await();

        System.out.println("el juez da la salida");

        salida.countDown();

        corredores.forEach(hilo->{
            try {
                hilo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("todos los corredores han salido");
    }
}
