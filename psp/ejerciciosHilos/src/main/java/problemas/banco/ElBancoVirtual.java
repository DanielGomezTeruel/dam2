package problemas.banco;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ElBancoVirtual {
    public static void main(String[] args) {
        Random random = new Random();
        CuentaBancaria cuenta = new CuentaBancaria(10000);

        LocalDateTime startTime = LocalDateTime.now();

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

        clientes.forEach(Thread::start);

        clientes.forEach(cliente->{
            try {
                cliente.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("---------ReentrantLock----------");
        System.out.println("Saldo final: "+cuenta.consultarSaldo());
        System.out.println("Tiempo: "+Duration.between(startTime,LocalDateTime.now()).toMillis()+"ms");

        int operacionesFallidas = cuenta.historial.stream().filter(message-> message.contains("fallida")).toList().size();
        System.out.println("Operaciones realizadas: "+(500-operacionesFallidas)+"/500");
    }
}
