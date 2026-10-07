package ejerciciosJava25;/*Ejercicio 6.2 — "Contador de palabras concurrente"
Varios hilos leen fragmentos distintos de un texto grande y
actualizan un ConcurrentHashMap<String, Integer> con la frecuencia
de cada palabra, usando merge(palabra, 1, Integer::sum).

Requisito extra: compara el resultado y el rendimiento frente a
usar un HashMap normal con synchronized.*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Ejercicio6_2 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        String[] texto=new String[]
                {"Ejercicio 6.2 — Contador de palabras concurrente",
                 "Varios hilos leen fragmentos distintos de un texto grande",
                 "actualizan un ConcurrentHashMap<String, Integer> con la frecuencia"};

        List<Thread> lectores = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int referencia = i;
            Thread lector = new Thread(()->{
                List<String> palabras= Arrays.stream(texto[referencia].split("\\s+")).toList();
                palabras.forEach(palabra->map.merge(palabra,1,Integer::sum));
            });
            lectores.add(lector);
        }

        lectores.forEach(Thread::start);

        lectores.forEach(hilo->{
            try {
                hilo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(map);
    }
}
