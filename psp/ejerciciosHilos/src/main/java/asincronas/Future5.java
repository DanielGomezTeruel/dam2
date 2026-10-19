package asincronas;

import java.util.concurrent.CompletableFuture;

public class Future5 {
    void main() throws InterruptedException {
        CompletableFuture
                .supplyAsync(() -> 10+20)
                .thenApply(n -> n*2)
                .thenAccept(System.out::println);

        Thread.sleep(1000);
    }
}
