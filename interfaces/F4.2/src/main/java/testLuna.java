import javax.swing.*;

public class testLuna {
    static void main() {
        JFrame f = new JFrame();
        QDate test = new QDate();
        test.setBounds(10, 10, 300, 200);

        f.add(test);

        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
    }
}
