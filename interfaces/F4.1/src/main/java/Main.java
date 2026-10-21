import QDate.QDate;
import QDate.QDateException;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        QDate date = new QDate();
        frame.add(date);
        JButton check = new JButton();
        JOptionPane popup= new JOptionPane();
        frame.add(check);


        check.addActionListener(event->{
            try {
                date.checkValidity();
            }catch (QDateException e){
                JOptionPane.showMessageDialog(
                        frame,
                        e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        frame.setSize(400,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
