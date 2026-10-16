import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static void main() {
        Random random = new Random();

        LocalDate respuesta = LocalDate.of(random.nextInt(1990,2001), random.nextInt(1,13), random.nextInt(1,25));
        System.out.println(respuesta);
        Scanner sc = new Scanner(System.in);

        String colorDia = "";
        String colorMes = "";
        String colorAño = "";

        int dia;
        int mes;
        int año;


        while(!(colorAño.equals("verde")&&colorMes.equals("verde")&&colorDia.equals("verde"))){
            System.out.println("Adivina la fecha. Día:");
            dia = sc.nextInt();
            if (respuesta.getDayOfMonth() > dia) {
                colorDia = "azul";
            } else if (respuesta.getDayOfMonth() < dia) {
                colorDia = "rojo";
            } else {
                colorDia = "verde";
            }

            System.out.println("Mes:");
            mes = sc.nextInt();
            if (respuesta.getMonthValue() > mes) {
                colorMes = "azul";
            } else if (respuesta.getMonthValue() < mes) {
                colorMes = "rojo";
            } else {
                colorMes = "verde";
            }

            System.out.println("Año:");
            año = sc.nextInt();
            if (respuesta.getYear() > año) {
                colorAño = "azul";
            } else if (respuesta.getYear() < año) {
                colorAño = "rojo";
            } else {
                colorAño = "verde";
            }

            System.out.println(año + "-" + mes + "-" + dia);

            System.out.println(colorAño + colorMes + colorDia);
        }
        System.out.println("Adivinaste!!");
    }
}
