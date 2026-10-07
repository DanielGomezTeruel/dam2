package ejerciciosJava25;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Ejercicio5_3 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread hilo = new Thread(()->{
                try {
                    for(boolean retry=true;retry;) {
                        if (reentrantLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName()+" tiene el lock");
                            Thread.sleep(300);
                            reentrantLock.unlock();
                            System.out.println(Thread.currentThread().getName()+" suelta el lock");
                            retry=false;
                        }else{
                            System.out.println(Thread.currentThread().getName()+" reintenta");
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            },"hilo "+i);
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }
}
