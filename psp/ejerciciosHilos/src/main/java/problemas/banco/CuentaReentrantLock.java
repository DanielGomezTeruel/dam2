package problemas.banco;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CuentaReentrantLock implements Cuenta{
    private double saldo=10000;
    List<String> historial = new ArrayList<>();
    ReentrantLock lock = new ReentrantLock();

    public CuentaReentrantLock(double saldo){
        this.saldo=saldo;
    }

    @Override
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

    @Override
    public void ingresar(double cantidad){
        lock.lock();
        try {
            historial.add("Ingreso de " + cantidad + " euros " + LocalDateTime.now());
            saldo = saldo + cantidad;
        }finally{
            lock.unlock();
        }
    }

    @Override
    public double consultarSaldo(){
        lock.lock();
        try {
            historial.add("Saldo consultado "+ LocalDateTime.now());
            return saldo;
        }finally{
            lock.unlock();
        }
    }

    @Override
    public List<String> obtenerHistorial(){
        return historial;
    }
}