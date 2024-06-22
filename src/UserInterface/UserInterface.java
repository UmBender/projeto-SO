package UserInterface;

// UI Modules
import javax.swing.*;

import UserInterface.UIComponents.SubmissionPanel;
import UserInterface.UIComponents.ControlPanel;
import UserInterface.UIComponents.TextPanel;

// Java utils
import java.awt.*;

public class UserInterface implements NotificationInterface, Runnable {
    private SubmissionInterface longTermScheduler;
    private ControlInterface shortTermScheduler;
    TextPanel instructionsLogPanel = new TextPanel("Instruções Log");
    TextPanel shortTermSchedulerLogPanel = new TextPanel("ShortTermScheduler Log");
    TextPanel longTermSchedulerLogPanel = new TextPanel("LongTermScheduler Log");

    public UserInterface() {}

    public void setThreads(ControlInterface shortTermScheduler, SubmissionInterface longTermScheduler) {
        this.shortTermScheduler = shortTermScheduler;
        this.longTermScheduler = longTermScheduler;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new GridLayout(3, 1));
        logPanel.add(instructionsLogPanel);
        logPanel.add(shortTermSchedulerLogPanel);
        logPanel.add(longTermSchedulerLogPanel);

        // Criando painel de controle
        JPanel controlPanel = new ControlPanel(shortTermScheduler);
        frame.add(controlPanel, BorderLayout.NORTH);

        // Criando painel de submissão
        JPanel submissionPanel = new SubmissionPanel(longTermScheduler);
        frame.add(submissionPanel, BorderLayout.SOUTH);

        // Criando paineis de logs
        frame.add(logPanel, BorderLayout.CENTER);

        // Setando GUI para visualização
        frame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    @Override
    public void display(String info) {
        SwingUtilities.invokeLater(() -> {
            String[] command = info.split(" ");

            switch (command[0]){
                case "<ss>":
                    this.shortTermSchedulerLogPanel.append(info.substring(4));
                    break;
                case "<ls>":
                    this.longTermSchedulerLogPanel.append(info.substring(4));
                    break;
                default:
                    this.instructionsLogPanel.append(info.substring(4));
            }
        });
    }
}
