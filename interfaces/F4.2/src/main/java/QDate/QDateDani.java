package QDate;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

public class QDateDani extends JPanel {
    private Date date;
    private Integer year;
    private Integer month;
    private Integer day;
    List<Integer> days = new ArrayList<>();
    List<Integer> months = new ArrayList<>();


    private final JTextField yearUI = new JTextField();
    private JComboBox<Object> monthUI;
    private JComboBox<Object> dayUI;

    private JLabel yearLabel = new JLabel("Year:");
    private JLabel monthLabel = new JLabel("Month:");
    private JLabel dayLabel = new JLabel("Day:");

    public QDateDani(){
        year=1900;
        month=0;
        day=1;
        date = new Date(1900, 0,1);
        create();
        updateDateUI();
    }

    public QDateDani(int year, int month, int day){
        try {
            date = new Date(year, month, day);
            this.year = year;
            this.month = month;
            this.day = day;
            checkValidity();
            create();
            updateDateUI();
        }catch (InputMismatchException e){
            throw new QDateExceptionDani("Error: Fecha inexistente");
        }
    }

    public Date getDate() {
        checkValidity();
        return date;
    }

    public void setDate(int year, int month, int day) {
        try {
            date = new Date(year, month, day);
            this.year = year;
            this.month = month;
            this.day = day;
            checkValidity();
            updateDateUI();
        }catch (InputMismatchException e){
            throw new QDateExceptionDani("Error: Fecha inexistente");
        }
    }

    public void setDayColor(Color color){
        dayUI.setBackground(color);
    }

    public void setDayColor(String color){
        dayUI.setBackground(Color.getColor(color));
    }

    public void setMonthColor(Color color){
        monthUI.setBackground(color);
    }

    public void setMonthColor(String color){
        monthUI.setBackground(Color.getColor(color));
    }

    public void setYearColor(Color color){
        yearUI.setBackground(color);
    }

    public void setYearColor(String color){
        yearUI.setBackground(Color.getColor(color));
    }


    boolean checkValidity(){
        update();
        boolean bisiesto = false;
        if(year==null||month==null||day==null){
            throw new QDateExceptionDani("Error: Debes rellenar con números todos los campos");
        }
        if((year%100!=0&&year%4==0)||year%400==0){
            bisiesto=true;
        }

        switch (month){
            case 0, 2, 4, 6, 7, 9, 11:
                if(day>31||day<1){
                    throw new QDateExceptionDani("Error: Fecha inexistente");
                }
                break;
            case 3, 5, 8, 10:
                if(day>30||day<1){
                    throw new QDateExceptionDani("Error: Fecha inexistente");
                }
                break;
            case 1:
                if(bisiesto?day>29:day>28||day<0){
                    throw new QDateExceptionDani("Error: Fecha inexistente");
                }
                break;
            default:
                throw new QDateExceptionDani("Error: Fecha inexistente");
        }
        return true;
    }

    void updateDateUI(){
        yearUI.setText(String.valueOf(year));
        monthUI.addItem(month);
        dayUI.addItem(day);
    }

    void create(){
        for (int i = 1; i < 32; i++) {
            days.add(i);
            if(i<13) {
                months.add(i);
            }
        }
        monthUI = new JComboBox<>(months.toArray());
        dayUI = new JComboBox<>(days.toArray());

        yearUI.setBounds(10,30,40,25);
        monthUI.setBounds(60,30,40,25);
        dayUI.setBounds(110,30,40,25);

        yearLabel.setBounds(10,10,40,15);
        monthLabel.setBounds(60,10,40,15);
        dayLabel.setBounds(110,10,40,15);

        add(yearUI);
        add(monthUI);
        add(dayUI);

        add(yearLabel);
        add(monthLabel);
        add(dayLabel);

        setSize(155,60);
        setLayout(null);
        setVisible(true);
    }

    void update() {
        try {
            year = Integer.parseInt(yearUI.getText()) -1900;
            month = (int) monthUI.getSelectedItem() - 1;
            day = (int) dayUI.getSelectedItem();
            date = new Date(year, month, day);
        }catch (NumberFormatException e){
            throw new QDateExceptionDani("Error: Debes rellenar con números todos los campos");
        }
    }
}
