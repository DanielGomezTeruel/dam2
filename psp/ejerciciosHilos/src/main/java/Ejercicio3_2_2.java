public class Ejercicio3_2_2 {
    public static void main(String[] args) throws InterruptedException {

        CuentaBancaria2 a = new CuentaBancaria2(1, 1000);
        CuentaBancaria2 b = new CuentaBancaria2(2, 1000);

        Thread hilo1 = new Thread(() -> a.transferir(b, 100),"cuenta A");

        Thread hilo2 = new Thread(() -> b.transferir(a, 50), "cuenta B");

        hilo1.start();
        hilo2.start();

        hilo1.join();
        hilo2.join();

        Thread.sleep(1000);
        System.out.println("Transacciones finalizadas sin deadlock");
    }
}

class CuentaBancaria2{
    private final int id;
    private int saldo;

    public CuentaBancaria2(int id, int saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public void transferir(CuentaBancaria2 destino, int cantidad) {

        Object lock1=null;
        Object lock2=null;
        if(destino.id>this.id){
            lock1=destino;
            lock2=this;
        }else{
            lock1=this;
            lock2=destino;
        }

        synchronized (lock1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock2) {
                if (saldo >= cantidad) {
                    saldo -= cantidad;
                    destino.saldo += cantidad;
                }
            }
        }
    }
}

