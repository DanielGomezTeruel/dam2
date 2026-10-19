package asincronas;

import java.util.concurrent.CompletableFuture;

public class Future6 {
    void main() throws InterruptedException {
        CompletableFuture
                .supplyAsync(()-> 10)
                .thenApply(n-> n*2)
                .thenApply(n-> n+5)
                .thenApply(Object::toString)
                .thenAccept(System.out::println)
                .join();


    }
}
