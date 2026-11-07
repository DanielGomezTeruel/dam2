
import QDate.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        QDate date = new QDate();
        frame.add(date);
        JButton check = new JButton("Check");
        check.setBounds(40, 70, 80, 30);
        JOptionPane popup= new JOptionPane();
        frame.add(check);


        check.addActionListener(event->{
            try {
                date.getDate();
            }catch (QDateException e){
                JOptionPane.showMessageDialog(
                        frame,
                        e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        frame.setSize(180,150);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
