package examen;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
## Ejercicio 2. Entender un programa asíncrono defectuoso (1 puntos)

Una aplicación consulta dos servicios independientes: temperatura y humedad.
Se desea que ambas consultas comiencen sin esperar a que termine la otra y que,
cuando las dos hayan acabado, se muestre el resultado.


--Ejercicio--
El programa tenía una función distinta a la buscada: hacía ambas operaciones y moestraba sus resultados, pero de manera escalonada.
Para lograr que ambos resultado se muestren a la vez he utilizado thenCombine para crear un tercer CompletableFuture, que luego printeo. De esta manera,
ambos CompletableFutures comienzan su ejecución simultáneamente, pero no muestro el resultado hasta que no han terminado ambos.
 */
public class Ejercicio2 {

    static void main() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CompletableFuture<String> temperatura =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "25 ºC";
                }, executor);

        CompletableFuture<String> humedad =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "60 %";
                }, executor);

        CompletableFuture<String> resultado =
            temperatura
                    .thenCombine(humedad, (grados, porcentaje) ->
                        "Temperatura: "+grados+" Humedad: "+porcentaje
                    );

        resultado.thenAccept(System.out::println).join();

        executor.shutdown();
    }
}
