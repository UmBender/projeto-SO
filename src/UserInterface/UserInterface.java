package UserInterface;

//UI Modules
import javax.swing.*;

import UserInterface.UIComponents.SubmissionPanel;
import UserInterface.UIComponents.TextAreaPanel;
import UserInterface.UIComponents.ControlPanel;

//Java utils
import java.awt.*;

public class UserInterface implements NotificationInterface, Runnable {
    private SubmissionInterface longTermScheduler;
    private ControlInterface shortTermScheduler;
    private TextAreaPanel textAreaPanel;

    public UserInterface(){
        this.textAreaPanel = new TextAreaPanel();
    }

    public void setThreads(ControlInterface shortTermScheduler, SubmissionInterface longTermScheduler){
        this.shortTermScheduler = shortTermScheduler;
        this.longTermScheduler = longTermScheduler;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Criação de uma instância de TextArea
        frame.getContentPane().add(textAreaPanel, BorderLayout.CENTER);

        // Criando painel de controle
        JPanel controlPanel = new ControlPanel(shortTermScheduler);
        frame.getContentPane().add(controlPanel, BorderLayout.NORTH);

        // Criando painel de submissão
        JPanel submissionPanel = new SubmissionPanel(longTermScheduler);
        frame.add(submissionPanel, BorderLayout.SOUTH);

        // Criando a visualização GUI
        frame.setVisible(true);

    }

    public void run(){
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    @Override
    public void display(String info) {
        this.textAreaPanel.append(info);
    }
}