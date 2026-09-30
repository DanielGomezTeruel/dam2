import java.util.ArrayList;
import java.util.List;

public class MainHilosSinAtomic {
    public static int contador = 0;
    public static final int NUM_HILOS = 100;

    void main(){
        List<Thread> hilos = new ArrayList<>();


        for (int i = 0; i < NUM_HILOS; i++) {
            Thread hilo = new Thread(() -> {
                contador++;
            });
            hilo.start();
            hilos.add(hilo);
        }
        hilos.forEach(hilo->{
            try {
                hilo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(contador);
    }
}
