import QDate.QDateDani;
import QDate.QDateExceptionDani;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainConQDateLuna {
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
    Date guess;

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
        dateInicialUI.setBounds(30, 65, 300, 60);
        dateFinalUI = new QDate();
        dateFinalUI.setBounds(30, 120, 300, 60);

        frame.add(dateInicialUI);
        frame.add(dateFinalUI);

        check = new JButton("Generate");
        check.setBounds(120, 180, 100, 30);

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
        dateGuesser.setBounds(30, 260, 300, 60);

        frame.add(dateGuesser);

        guessValidator = new JButton("Check my guess");
        guessValidator.setBounds(100, 320, 140, 30);

        frame.add(guessValidator);

        guessValidator.addActionListener(event->{
            try{
                guess = dateGuesser.getDate();
                if(guess.before(incognita))
                {

                    dateGuesser.setBackground(Color.CYAN);
                    JOptionPane.showMessageDialog(
                            frame,
                            "Tu fecha es anterior a la incógnita",
                            "Anterior",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                else if (guess.after(incognita))
                {
                    dateGuesser.setBackground(Color.RED);
                    JOptionPane.showMessageDialog(
                            frame,
                            "Tu fecha es posterior a la incógnita",
                            "Posterior",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                else
                {
                    dateGuesser.setBackground(Color.GREEN);
                    JOptionPane.showMessageDialog(
                            frame,
                            "Acertaste!! La fecha era "+formatDate(incognita),
                            "Acierto",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }catch(QDateException e){
                JOptionPane.showMessageDialog(
                        frame,
                        e.getMessage(),
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });


        frame.setSize(360,450);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private String formatDate(Date incognita) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        incognita.setYear(incognita.getYear());
        return formato.format(incognita);
    }

    private void generarIncognita() {
        dateGuesser.setBackground(null);
        int year;
        int month;
        int day;
        boolean bisiesto = false;

        System.out.println(dateInicial.getYear());
        System.out.println(dateInicial.getMonth());

        System.out.println(dateFinal.getYear());

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
        System.out.println(incognita.getYear()+" "+incognita.getMonth()+" "+incognita.getDate());
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

