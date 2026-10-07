package problemas.banco;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public interface Cuenta {
    double saldo = 10000;
    List<String> historial = new ArrayList<>();
    ReentrantLock lock = new ReentrantLock();


    public boolean retirar(double cantidad);
    public void ingresar(double cantidad);
    public double consultarSaldo();
    public List<String> obtenerHistorial();
}

