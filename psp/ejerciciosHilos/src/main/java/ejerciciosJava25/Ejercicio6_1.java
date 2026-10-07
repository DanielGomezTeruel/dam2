package ejerciciosJava25;

import java.util.concurrent.ArrayBlockingQueue;

public class Ejercicio6_1 {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);

        Thread productor = new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    blockingQueue.put(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread consumidor = new Thread(()-> {
            for (int i = 0; i <20; i++) {
                try {
                    System.out.println(blockingQueue.take());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        productor.start();
        consumidor.start();

        productor.join();
        consumidor.join();
    }
}
