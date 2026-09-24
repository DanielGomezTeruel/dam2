import java.util.ArrayList;
import java.util.List;

public class MainHilosSinAtomic {
    public static final int contador = 0;
    public static final int NUM_HILOS = 100;

    void main(){
        List<Thread> hilos = new ArrayList<>();


        for (int i = 0; i < NUM_HILOS; i++) {
            Thread hilo = new Thread(() -> {

            });
        }
    }
}
