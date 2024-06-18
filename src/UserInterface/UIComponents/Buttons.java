package UserInterface.UIComponents;

import UserInterface.SubmissionInterface;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Buttons extends JPanel implements ActionListener {
    protected JButton b1;
    private SubmissionInterface longTermScheduler;

    public Buttons(SubmissionInterface longTermScheduler) {
        this.longTermScheduler = longTermScheduler;

        b1 = new JButton("submitJob");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setActionCommand("submitJob");

        // Registra este objeto como ouvinte do botão b1
        b1.addActionListener(this);

        // Adiciona o botão ao painel
        add(b1);
    }

    public void actionPerformed(ActionEvent e) {
        if ("submitJob".equals(e.getActionCommand())) {
            longTermScheduler.submitJob("C:\\Users\\Tigrocomputer\\Documents\\code-workspace (local)\\SchedulerSimulator\\src\\resources\\teste.txt");
        }
    }

}