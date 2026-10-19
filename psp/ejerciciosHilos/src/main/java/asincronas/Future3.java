package asincronas;

import java.util.concurrent.*;

public class Future3 {
    void main() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Callable<Integer> t1 =(() -> {
            Thread.sleep(2000);
            return 10;
        });

        Callable<Integer> t2 =(() -> {
            Thread.sleep(1000);
            return 20;
        });
        Callable<Integer> t3 =(() -> {
            Thread.sleep(3000);
            return 30;
        });

        Future<Integer> f1 = executor.submit(t1);

        Future<Integer> f2 = executor.submit(t2);

        Future<Integer> f3 = executor.submit(t3);

        System.out.println(f1.get()+f2.get()+f3.get());
        System.exit(0);
    }
}
