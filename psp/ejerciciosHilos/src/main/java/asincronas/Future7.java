package asincronas;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Future7 {

    public static CompletableFuture<String> obtenerUsuario() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

            return "Oscar";
        });
    }

    public static CompletableFuture<String> obtenerEmail(String usuario) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

            return usuario + "@example.com";
        });
    }

    void main(){

        CompletableFuture<String> mailCompleto =
                CompletableFuture
                        .supplyAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }

                    return "Oscar";
                }).thenCompose(Future7::obtenerEmail);


        mailCompleto.thenAccept(System.out::println).join();
    }
}
