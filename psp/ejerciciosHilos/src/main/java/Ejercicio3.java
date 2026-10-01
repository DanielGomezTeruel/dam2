/*Ejercicio 3.1 — "Cuenta bancaria con condición de carrera"
Crea una clase CuentaBancaria con un saldo (int) y un mét odo
depositar(int cantidad) que haga saldo = saldo + cantidad (sin sincronizar).
Lanza 100 hilos que depositen 1 euro cada uno, de forma concurrente, y comprueba
que el saldo final no es 100 (condición de carrera).

Requisitos:

Primero demuestra el fallo (ejecuta varias veces y observa resultados distintos).
Corrige el problema declarando depositar como synchronized.
Repite con un bloque synchronized(this) en vez de sincronizar el mét odo completo.*/

public class Ejercicio3 {
    public int saldoBancario=0;

    public void depositar(int cantidad){
        saldoBancario=saldoBancario+cantidad;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread hilo = new Thread(() -> {
                this.depositar(1);
            });
        }
    }
}
