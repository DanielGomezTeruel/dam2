package main.sincronizadoresAN;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int numCorredores = 5;
        CyclicBarrier barrera = new CyclicBarrier(numCorredores,
                () -> System.out.println("Empieza la carrera"));
        for (int i = 0; i <= numCorredores; i++) {
            int corredor = i;
            new Thread(() -> {
                System.out.println("Corredor "+corredor+" en la linea de salida");
                try{
                    barrera.await();
                }catch(Exception e){
                    Thread.currentThread().interrupt();
                }
            });

        }
    }
}
