package QDate;

import javax.swing.*;
import java.util.Date;
import java.util.InputMismatchException;

public class QDate extends JPanel {
    Date date;
    Integer year;
    Integer month;
    Integer day;

    JTextField yearUI = new JTextField();
    JComboBox<Integer> monthUI = new JComboBox<>();
    JComboBox<Integer> dayUI = new JComboBox<>();

    public QDate(){
        year=1900;
        month=0;
        day=0;
        date = new Date(1900, 0,0);
        update();
    }

    public QDate(Integer year, Integer month, Integer day){
        try {
            date = new Date(year, month, day);
            this.year = year;
            this.month = month;
            this.day = day;
            check();
            update();
        }catch (InputMismatchException e){
            throw new QDateException("Error: Fecha inexistente");
        }
    }

    public Date getDate() {
        check();
        return date;
    }

    public void setDate(Integer year, Integer month, Integer day) {
        try {
            date = new Date(year, month, day);
            this.year = year;
            this.month = month;
            this.day = day;
            check();
            update();
        }catch (InputMismatchException e){
            throw new QDateException("Error: Fecha inexistente");
        }
    }

    void update(){
        yearUI.setText(String.valueOf(year));
        monthUI.addItem(month);
        dayUI.addItem(day);
    }

    void check(){
        boolean bisiesto = false;
        if(year==null||month==null||day==null){
            throw new QDateException("Error: Debes rellenar todos los campos");
        }
        if((year%100!=0&&year%4==0)||year%400==0){
            bisiesto=true;
        }

        switch (month){
            case 0, 2, 4, 6, 7, 9, 11:
                if(day>30||day<0){
                    throw new QDateException("Error: Fecha inexistente");
                }
                break;
            case 3, 5, 8, 10:
                if(day>29||day<0){
                    throw new QDateException("Error: Fecha inexistente");
                }
                break;
            case 1:
                if(bisiesto?day>28:day>27||day<0){
                    throw new QDateException("Error: Fecha inexistente");
                }
                break;
            default:
                throw new QDateException("Error: Fecha inexistente");
        }
    }
}
