import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("BankTellerGui");      //This is the main function for running the simulation
        frame.setContentPane(new BankTellerGui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}