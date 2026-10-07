package problemas.banco;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CuentaSynchronized implements Cuenta{
    private double saldo=10000;
    List<String> historial = new ArrayList<>();


    public CuentaSynchronized(double saldo){
        this.saldo=saldo;
    }

    @Override
    public synchronized boolean retirar(double cantidad){

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
    }

    @Override
    public synchronized void ingresar(double cantidad){
        historial.add("Ingreso de " + cantidad + " euros " + LocalDateTime.now());
        saldo = saldo + cantidad;
    }

    @Override
    public synchronized double consultarSaldo(){
        historial.add("Saldo consultado "+ LocalDateTime.now());
        return saldo;
    }

    @Override
    public synchronized List<String> obtenerHistorial(){
        return historial;
    }
}