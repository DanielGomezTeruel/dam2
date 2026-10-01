import java.util.ArrayList;
import java.util.List;

//No se hace así
public class Ejercicio1_2 {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            hilos.add(
            Thread.ofVirtual().name("Hilo "+i).start(()->{
                for (int j = 0; j <= 100; j=j+10) {
                    System.out.println(Thread.currentThread().getName()+" progress = "+j+"%");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
        }
        hilos.forEach(hilo -> {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
