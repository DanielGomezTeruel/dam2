package ejerciciosJava25;

public class Ejercicio2 {
    public static void main(String[] args){
        Thread regresiva = new Thread(() -> {
            for (int i = 10; i > 0; i--) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        regresiva.start();
        try {
            regresiva.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Despegue!");
    }
}
