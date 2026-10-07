package ejerciciosJava25;

public class Ejercicio3 {
    public static int saldoBancario=0;

    public synchronized static void depositar(int cantidad){
        saldoBancario=saldoBancario+cantidad;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread hilo = new Thread(() -> {
                depositar(1);
            });
            hilo.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Saldo: "+saldoBancario);
    }


}

