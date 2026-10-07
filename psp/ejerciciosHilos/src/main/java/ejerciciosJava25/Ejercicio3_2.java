package ejerciciosJava25;

public class Ejercicio3_2 {
    public static void main(String[] args) throws InterruptedException {

        CuentaBancaria a = new CuentaBancaria(1, 1000);
        CuentaBancaria b = new CuentaBancaria(2, 1000);

        Thread hilo1 = new Thread(() -> a.transferir(b, 100),"cuenta A");

        Thread hilo2 = new Thread(() -> b.transferir(a, 50), "cuenta B");

        hilo1.start();
        hilo2.start();

        hilo1.join();
        hilo2.join();

        Thread.sleep(1000);
    }
}

class CuentaBancaria{
    private final int id;
    private int saldo;

    public CuentaBancaria(int id, int saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public void transferir(CuentaBancaria destino, int cantidad) {
        synchronized (this) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (destino) {
                if (saldo >= cantidad) {
                    saldo -= cantidad;
                    destino.saldo += cantidad;
                }
            }
        }
    }
}
