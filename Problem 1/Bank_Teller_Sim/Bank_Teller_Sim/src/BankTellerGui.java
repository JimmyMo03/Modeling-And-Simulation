import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

//GUI CLASS
public class BankTellerGui {
    public javax.swing.JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JTextField numberOfCustomers;
    private JButton StartButton;
    private JLabel label1;
    private JTextField output1;
    private JLabel N;
    private JTextField output2;
    private JTextField output3;
    private JTextField output4;
    private JTextField output5;
    private JTextField output6;
    private JTextField output7;
    private JLabel label2;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JTextField output8;
    private JTextField output14;
    private JTextField output9;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JTextField output10;
    private JTextField output11;
    private JLabel label11;
    private JLabel label12;
    private JTextField output12;
    private JLabel label13;
    private JTextField output13;
    private JLabel label14;

    public BankTellerGui() {
        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int n = parseInt(numberOfCustomers.getText().trim());

                Teller T = new Teller();
                T.startSimulation(n);

                output1.setText(String.format("%.3f", T.avgOrdinaryWT) + "");
                output2.setText(String.format("%.3f", T.avgDistinguishedWT) + "");
                output3.setText(String.format("%.3f", T.avgServiceTime) + "");
                output4.setText(String.format("%.3f", T.percentageOrdinary)+ "%");
                output5.setText(String.format("%.3f", T.percentageDistinguished)+ "%");
                output6.setText(String.format("%.3f", (T.totalIdleTime/ (double) T.simulationEndTime))+ "");
                output7.setText(T.totalOrdinaryCustomers + "");
                output8.setText(T.totalDistinguishedCustomers + "");
                output9.setText(String.format("%.3f", T.avgServiceTimeOrd));
                output10.setText(String.format("%.3f", T.avgServiceTimeDist));
                output11.setText(String.format("%.3f", T.avgInterTimeOrd));
                output12.setText(String.format("%.3f", T.avgInterTimeDIst));
                output13.setText(String.format("%.3f", T.avgSystemTime));
                output14.setText(String.format("%.3f", T.PWT)+ "%");

                Customer.lastArrivalTime = 0;
            }
        });
    }
}
