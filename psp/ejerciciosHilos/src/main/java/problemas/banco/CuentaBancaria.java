package problemas.banco;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CuentaBancaria {
    private double saldo;
    List<String> historial = new ArrayList<>();
    ReentrantLock lock = new ReentrantLock();

    public CuentaBancaria(double saldo){
        this.saldo=saldo;
    }

    public boolean retirar(double cantidad){
        lock.lock();
        try {

            boolean respuesta;

            if (saldo >= cantidad) {
                saldo = saldo - cantidad;
                historial.add("Retirada de " + cantidad + " euros " + LocalDateTime.now());
                respuesta = true;
            } else {
                historial.add("Retirada fallida  " + LocalDateTime.now());
                respuesta = false;
            }

            return respuesta;

        }finally{
            lock.unlock();
        }
    }
    public void ingresar(double cantidad){
        lock.lock();
        try {
            historial.add("Ingreso de " + cantidad + " euros " + LocalDateTime.now());
            saldo = saldo + cantidad;
        }finally{
            lock.unlock();
        }
    }
    public double consultarSaldo(){
        lock.lock();
        try {
            historial.add("Saldo consultado "+ LocalDateTime.now());
            return saldo;
        }finally{
            lock.unlock();
        }
    }
    public List<String> obtenerHistorial(){
        return historial;
    }
}