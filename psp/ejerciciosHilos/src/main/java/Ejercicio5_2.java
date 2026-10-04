import java.util.concurrent.atomic.AtomicInteger;

public class Ejercicio5_2 {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger contadorVisitas = new AtomicInteger(0);

        for (int i = 0; i < 1000; i++) {
            Thread visita = new Thread(()->{
                contadorVisitas.addAndGet(1);
            });
            visita.start();
        }
        Thread.sleep(100);
        System.out.println("Visitas totales: "+contadorVisitas);
    }
}
