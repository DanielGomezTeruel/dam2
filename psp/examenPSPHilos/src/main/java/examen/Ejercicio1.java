package examen;

import java.util.concurrent.CompletableFuture;

/*
Corregir una cadena de `CompletableFuture` (1.5 Pto)

El objetivo del siguiente programa es calcular `((10 * 2) + 5)`,
convertir el resultado a texto y mostrar `Resultado: 25`.

El código contiene errores:

corrigelos y exploca en un comentario que estaba mal y como lo has arreglado.

--Ejercicio--

El primer error era simplemente que el segundo thenApply tenía llaves {} en su notación lambda,
evitando que compilase. Es tan sencillo como eliminarlas.

Después había un error de tipo. ThenAccept devuelve void porque consume los datos,
y al ser nuestro CompletableFuture de tipo <Integer>, no compilar.
Por eso he cambiado el tipo a CompletableFuture<Void>.

 */
public class Ejercicio1 {

    static void main() {
        CompletableFuture<Void> tarea =
                CompletableFuture.supplyAsync(() -> 10)
                        .thenApply(n -> n * 2)
                        .thenApply(n -> n + 5)
                        .thenApply(String::valueOf)
                        .thenAccept(resultado ->
                                System.out.println("Resultado: " + resultado)
                        );

        tarea.join();

    }
}
