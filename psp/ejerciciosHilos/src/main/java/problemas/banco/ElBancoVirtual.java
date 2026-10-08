package problemas.banco;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElBancoVirtual {

    private static final Random random = new Random();

    public static void main(String[] args) {

        //Solución con ReentrantLock

        CuentaReentrantLock cuentaLock = new CuentaReentrantLock(10000);

        LocalDateTime startTimeReentrant = LocalDateTime.now();

        List<Thread> clientesReentrantLock = crearClientes(cuentaLock);

        clientesReentrantLock.forEach(Thread::start);

        clientesReentrantLock.forEach(cliente->{
            try {
                cliente.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("---------ReentrantLock----------");
        System.out.println("Saldo final: "+cuentaLock.consultarSaldo());
        System.out.println("Tiempo: "+Duration.between(startTimeReentrant,LocalDateTime.now()).toMillis()+"ms");

        int operacionesFallidasLock = cuentaLock.historial.stream().filter(message-> message.contains("fallida")).toList().size();
        System.out.println("Operaciones realizadas: "+(500-operacionesFallidasLock)+"/500");


        //Solución con synchronized

        CuentaSynchronized cuentaSynchronized = new CuentaSynchronized(10000);

        LocalDateTime startTimeSynchronized = LocalDateTime.now();

        List<Thread> clientesSynchronized = crearClientes(cuentaSynchronized);

        clientesSynchronized.forEach(Thread::start);

        clientesSynchronized.forEach(cliente->{
            try {
                cliente.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("---------Synchronized----------");
        System.out.println("Saldo final: "+cuentaSynchronized.consultarSaldo());
        System.out.println("Tiempo: "+Duration.between(startTimeSynchronized,LocalDateTime.now()).toMillis()+"ms");

        int operacionesFallidasSynchronized = cuentaSynchronized.historial.stream().filter(message-> message.contains("fallida")).toList().size();
        System.out.println("Operaciones realizadas: "+(500-operacionesFallidasSynchronized)+"/500");

    }

    private static List<Thread> crearClientes(Cuenta cuenta) {
        List<Thread> clientes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Thread cliente = new Thread(()->{
                for (int j = 0; j < 10; j++) {

                        try {
                            Thread.sleep(random.nextInt(100, 301));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }


                        if (random.nextInt(1, 6) < 4) {
                            cuenta.retirar(random.nextDouble(1, 101));
                        } else {
                            cuenta.ingresar(random.nextDouble(1, 51));
                        }
                }
            });
            clientes.add(cliente);
        }
        return clientes;
    }
}
