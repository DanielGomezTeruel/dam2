package asincronas;

import java.util.concurrent.CompletableFuture;

public class Future8 {
    void main(){

        CompletableFuture<String> consultarHumedad =
                CompletableFuture
                        .supplyAsync(()->{
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return "25%";
                        });

        CompletableFuture<String> consultarTemperatura =
                CompletableFuture
                        .supplyAsync(()->{
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return "18ºC";
                        });

        CompletableFuture<String> print =
        consultarHumedad
                .thenCombine(consultarTemperatura, (humedad, temperatura) ->
                    "Humedad: "+humedad+
                    "Temperatura: "+temperatura
                );


        print.thenAccept(System.out::println).join();
    }
}
