import javax.swing.*;
import java.util.Date;

public class MainGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        QDate dateInicialUI = new QDate();
        dateInicialUI.setBounds(10, 10, 155, 70);
        QDate dateFinalUI = new QDate();
        dateFinalUI.setBounds(10, 70, 155, 70);
        frame.add(dateInicialUI);
        frame.add(dateFinalUI);
        JButton check = new JButton("Check");
        check.setBounds(50, 150, 80, 30);
        JOptionPane popup= new JOptionPane();
        frame.add(check);


        check.addActionListener(event->{
            try {
                Date dateInicial = dateInicialUI.getDate();
                Date dateFinal = dateFinalUI.getDate();

                if(dateInicial.before(dateFinal)){
                    long milisegundosPorDia = 1000L * 60 * 60 * 24;
                    long diferenciaDias = (dateFinal.getTime() - dateInicial.getTime()) / milisegundosPorDia;
                    JOptionPane.showMessageDialog(
                            frame,
                            "Diferencia en días: "+ diferenciaDias,
                            "Diferencia",
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

        frame.setSize(180,300);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
