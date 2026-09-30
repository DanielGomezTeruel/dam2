public class Ejercicio1_1_1 {


    public static void main(String[] args){
        for (int i = 0; i < 4; i++) {
            RunnableExample runnable = new RunnableExample();
            Thread hilo = new Thread(runnable);
            hilo.setName("Archivo "+(i+1));
            hilo.start();
        }
    }
}

class RunnableExample implements Runnable {

    @Override
    public void run() {
        for (int j = 0; j <= 100; j = j + 10) {
            System.out.println(Thread.currentThread().getName() + " progress = " + j + "%");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
