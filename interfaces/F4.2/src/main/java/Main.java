import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import QDate.*;

public class Main extends JFrame {//inheriting JFrame
    JFrame f = new JFrame();
    Random random = new Random();
    Date respuesta = null;
    public static int contador = 0;



    void Simple(){

        QDate dateInicialUI = new QDate();
        dateInicialUI.setBounds(10, 10, 155, 70);
        QDate dateFinalUI = new QDate();
        dateFinalUI.setBounds(10, 70, 155, 70);

        f.add(dateInicialUI);
        f.add(dateFinalUI);

        JLabel contadorLabel = new JLabel("Intentos: "+contador);
        contadorLabel.setBounds(250,100,100,100);
        f.add(contadorLabel);

        JLabel title = new JLabel("Guess the date");
        title.setBounds(130,50,100,40);
        f.add(title);


        JButton generateDate = new JButton("Generate date");
        generateDate.setBounds(250,80,100, 40);
        generateDate.addActionListener(e -> {
            generateDate();
            System.out.println(respuesta);
            contador = 0;
            contadorLabel.setText("Intentos: "+contador);
            title.setText("Guess the date");
        });

        f.add(generateDate);


        JLabel error = new JLabel("");
        error.setBounds(70,20,300,50);
        error.setForeground(Color.gray);
        f.add(error);



        JButton check=new JButton("Check");
        check.setBounds(130,250,100, 40);
        check.addActionListener(e -> {

            try {
                if(day.getText()!=null&&month.getText()!=null&&year.getText()!=null) {
                    error.setText("");



                    String colorDia = "";
                    String colorMes = "";
                    String colorAño = "";

                    int dia = Integer.parseInt(day.getText());
                    int mes = Integer.parseInt(month.getText());
                    int año = Integer.parseInt(year.getText());


                    if (respuesta.getDayOfMonth() > dia) {
                        colorDia = "azul";
                        day.setBackground(Color.blue);
                    } else if (respuesta.getDayOfMonth() < dia) {
                        colorDia = "rojo";
                        day.setBackground(Color.red);
                    } else {
                        colorDia = "verde";
                        day.setBackground(Color.green);
                    }

                    System.out.println("Mes:");

                    if (respuesta.getMonthValue() > mes) {
                        colorMes = "azul";
                        month.setBackground(Color.blue);
                    } else if (respuesta.getMonthValue() < mes) {
                        colorMes = "rojo";
                        month.setBackground(Color.red);
                    } else {
                        colorMes = "verde";
                        month.setBackground(Color.green);
                    }

                    System.out.println("Año:");

                    if (respuesta.getYear() > año) {
                        colorAño = "azul";
                        year.setBackground(Color.blue);
                    } else if (respuesta.getYear() < año) {
                        colorAño = "rojo";
                        year.setBackground(Color.red);
                    } else {
                        colorAño = "verde";
                        year.setBackground(Color.green);
                    }

                    if (colorAño.equals("verde") && colorMes.equals("verde") && colorDia.equals("verde")) {
                        title.setText("Acertaste!!");
                    } else {
                        contador++;
                        contadorLabel.setText("Intentos: " + contador);
                    }
                }

            }catch(NumberFormatException ex){
                error.setText("Debes introducir números en todos los campos");
            }
        });
        f.add(check);


        JLabel dayLabel = new JLabel("Day: ");
        dayLabel.setBounds(80,100,100,40);
        f.add(dayLabel);

        JLabel monthLabel = new JLabel("Month: ");
        monthLabel.setBounds(80,150,100,40);
        f.add(monthLabel);

        JLabel yearLabel = new JLabel("Year: ");
        yearLabel.setBounds(80,200,100,40);
        f.add(yearLabel);


        f.setSize(400,500);
        f.setLayout(null);
        f.setVisible(true);



    }

    public void main(String[] args) {
        new Main().Simple();

        generateDate();

        System.out.println(respuesta);



        validate();

    }
    void generateDate(){
        respuesta = Date(random.nextInt(dateInicialUI.getDate().getYear(),dateFinalUI.getDate().getYear()+1), random.nextInt(1,13), random.nextInt(1,25));
    }

    void validate(){
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

