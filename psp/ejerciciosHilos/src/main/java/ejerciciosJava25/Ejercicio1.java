package ejerciciosJava25;

public class Ejercicio1 {

    public static void main(String[] args){
        for (int i = 0; i < 4; i++) {
            Thread hilo = new Thread(()->{
                for (int j = 0; j <= 100; j=j+10) {
                    System.out.println(Thread.currentThread().getName()+" progress = "+j+"%");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            hilo.start();
        }
    }
}
