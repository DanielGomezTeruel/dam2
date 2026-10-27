import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Random;


import QDate.*;

public class Main{
    Random random = new Random();

    JFrame frame;
    JLabel title;
    JLabel choose;
    QDate dateInicialUI;
    QDate dateFinalUI;
    JButton check;
    JOptionPane popup;
    QDate dateGuesser;
    JLabel guesserLabel;
    JButton guessValidator;

    Date dateInicial;
    Date dateFinal;

    Date incognita;

    void main(String[] args) {

        frame = new JFrame();

        title = new JLabel("Date guesser");
        title.setBounds(20, 5, 300, 40);
        title.setFont(new Font("Arial", Font.BOLD, 15));

        frame.add(title);

        choose = new JLabel("Choose the game's bounds:");
        choose.setBounds(55, 30, 300, 40);

        frame.add(choose);

        dateInicialUI = new QDate();
        dateInicialUI.setBounds(50, 60, 155, 70);
        dateFinalUI = new QDate();
        dateFinalUI.setBounds(50, 120, 155, 70);

        frame.add(dateInicialUI);
        frame.add(dateFinalUI);

        check = new JButton("Generate");
        check.setBounds(80, 200, 100, 30);

        frame.add(check);

        popup= new JOptionPane();


        check.addActionListener(event->{
            try {
                dateInicial = dateInicialUI.getDate();
                dateFinal = dateFinalUI.getDate();

                if(dateInicial.before(dateFinal)){
                    generarIncognita();
                    JOptionPane.showMessageDialog(
                            frame,
                            "Fecha generada con éxito",
                            "Valid",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else if(dateInicial.after(dateFinal)) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Orden incorrecto",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }else{
                    JOptionPane.showMessageDialog(
                            frame,
                            "Introduce fechas diferentes",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            }catch (QDateException e){
                JOptionPane.showMessageDialog(
                        frame,
                        e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        guesserLabel = new JLabel("Guess the date:");
        guesserLabel.setBounds(65, 230, 300, 40);

        frame.add(guesserLabel);

        dateGuesser = new QDate();
        dateGuesser.setBounds(50, 260, 155, 70);

        frame.add(dateGuesser);

        guessValidator = new JButton("Check my guess");
        guessValidator.setBounds(60, 350, 140, 30);

        frame.add(guessValidator);



        frame.setSize(300,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void generarIncognita() {
        int year;
        int month;
        int day;
        boolean bisiesto = false;

        year = random.nextInt(dateInicial.getYear(), dateFinal.getYear()+1);
        if((year%100!=0&&year%4==0)||year%400==0){
            bisiesto=true;
        }
        if(year==dateInicial.getYear()){
            month = random.nextInt(dateInicial.getMonth(), 12);
            if(month==dateInicial.getMonth()){
                day = random.nextInt(dateInicial.getDay(), getDayBound(month, bisiesto));
            }else{
                day =  random.nextInt(1, getDayBound(month, bisiesto));
            }
        } else if (year==dateFinal.getYear()) {
            month = random.nextInt(0, dateFinal.getMonth()+1);
            if(month==dateFinal.getMonth()){
                day = random.nextInt(1, dateFinal.getDay());
            }else{
                day =  random.nextInt(1, getDayBound(month, bisiesto));
            }
        }else{
            month = random.nextInt(0, 12);
            day =  random.nextInt(1, getDayBound(month, bisiesto));
        }

        incognita = new Date(year, month, day);
    }

    private int getDayBound(int month, boolean bisiesto) {
        switch (month) {
            case 0, 2, 4, 6, 7, 9, 11:
                return 32;

            case 3, 5, 8, 10:
                return 31;

            case 1:
                return bisiesto?30:29;

            default:
                return 28;

        }
    }
}

