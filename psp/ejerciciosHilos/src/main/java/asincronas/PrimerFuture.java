package asincronas;

import java.sql.SQLOutput;
import java.util.concurrent.*;

/*Práctica 1 — Primer Future
Crea un programa que:

Cree un ExecutorService con dos hilos.
Envíe una tarea mediante submit().
La tarea debe tardar 2 segundos.
Debe devolver el número 42.
El programa principal debe mostrar:
"Tarea enviada"
"Esperando resultado..."
el resultado.
Objetivo
Comprender:

Callable
ExecutorService
Future
get()
*/
public class PrimerFuture {
    static void main() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Integer> tarea = (()->{
                Thread.sleep(2000);
            return 42;
        });

        System.out.println("Tarea enviada");

        Future<Integer> resultado = executor.submit(tarea);

        while(!resultado.isDone()){
            System.out.println("Esperando...");

            Thread.sleep(500);
        }


        System.out.println("Resultado: "+resultado.get());

        executor.shutdown();

    }
}
