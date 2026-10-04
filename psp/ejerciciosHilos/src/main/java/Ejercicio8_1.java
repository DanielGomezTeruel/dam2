import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class Ejercicio8_1 {

    public static void main(String[] args) throws InterruptedException {

        int numCorredores = 5;

        CountDownLatch corredoresListos = new CountDownLatch(numCorredores);

        CountDownLatch salida = new CountDownLatch(1);

        Thread[] corredores = new Thread[numCorredores];

        for (int i = 0; i < numCorredores; i++) {

            final int numero = i + 1;

            corredores[i] = new Thread(() -> {

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

            corredores[i].start();
        }

        // El juez espera a que los 5 estén preparados
        preparados.await();

        System.out.println("Todos los corredores están preparados.");

        // El juez da la salida
        System.out.println("🏁 ¡SALIDA!");

        salida.countDown();

        // Esperamos a que todos los corredores terminen
        for (Thread corredor : corredores) {
            corredor.join();
        }

        System.out.println("Todos los corredores han empezado.");
    }
}
