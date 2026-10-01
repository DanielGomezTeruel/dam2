public class Ejercicio2_2 {

    public static void main(String[] args) {
        Thread cocinero = new Thread(() ->{
            System.out.println(Thread.currentThread().getState());
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Cocinando...");

                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getState());
                }
            } catch (InterruptedException e) {
                System.out.println("Cocción interrumpida");
                System.out.println(Thread.currentThread().getState());
            }
        });
        System.out.println(cocinero.getState());
        cocinero.start();
        System.out.println(cocinero.getState());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cocinero.interrupt();

    }
}
