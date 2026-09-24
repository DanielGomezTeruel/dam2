//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class Main {
//
//    public final ReentrantLock lock = new ReentrantLock();
//    public final int contador = 0;
//
//
//
//
//    public Main(){
//
//    }
//

//    public static void sumarConLock(){
//        boolean noHecho = true;
//        while(noHecho) {
//            if (lock.tryLock()) {
//                contador++;
//                noHecho=false;
//                lock.unlock();
//            } else {
//                System.out.println("suma perdida");
//                try {
//                    Thread.sleep(100);
//                }catch(InterruptedException e){
//                    throw new RuntimeException();
//                }
//            }
//        }
//    }
//
//    public void parking(){
//        boolean noHecho = true;
//        while(noHecho) {
//            if (parking.tryAcquire()) {
//                IO.println("Plazas libres: "+ parking.availablePermits());
//                IO.println("sitio encontrado");
//                try {
//                    Thread.sleep((long) (Math.random()*5000));
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                noHecho=false;
//                IO.println("me voy");
//                parking.release();
//                IO.println("Plazas libres: "+ parking.availablePermits());
//            } else {
//                System.out.println("no hay hueco");
//                try {
//                    Thread.sleep(3000);
//                }catch(InterruptedException e){
//                    throw new RuntimeException();
//                }
//            }
//        }
//    }
//    void main() throws InterruptedException {
//
//        Main m=new Main();
//
//        List<Thread> hilos = new ArrayList<>();
//        //ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//        AtomicInteger cont = new AtomicInteger(0);
//
//        for (int i = 0; i < NUM_COCHES; i++) {
//            Thread hilo = new Thread(m::parking);
//            hilos.add(hilo);
//            executorService.execute(()-> {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                Main.sumarConSemaphore();
//            });
//        }
//        hilos.forEach(Thread::start);
////
////        hilos.forEach(thread ->
////                {
////                    try {
////                        thread.join();
////                    } catch (InterruptedException e) {
////                        throw new RuntimeException(e);
////                    }
////                }
////                );
////        executorService.shutdown();
//        Thread.sleep(1000);
////        //System.out.println(cont);
////        System.out.println(contador);
//    }
//}
