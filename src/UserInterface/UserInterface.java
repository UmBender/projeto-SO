package UserInterface;

// UI Modules
import javax.swing.*;

import UserInterface.UIComponents.SubmissionPanel;
import UserInterface.UIComponents.ControlPanel;
import UserInterface.UIComponents.TextPanel;

// Java utils
import java.awt.*;
import java.util.HashMap;

public class UserInterface implements NotificationInterface, Runnable {
    private SubmissionInterface longTermScheduler;
    private ControlInterface shortTermScheduler;
    TextPanel parserLogPanel = new TextPanel("Parser Log");
    TextPanel shortTermSchedulerLogPanel = new TextPanel("ShortTermScheduler Log");
    TextPanel longTermSchedulerLogPanel = new TextPanel("LongTermScheduler Log");
    HashMap<String, Color> colorMap = new HashMap<>();

    public UserInterface() {}

    public void setThreads(ControlInterface shortTermScheduler, SubmissionInterface longTermScheduler) {
        this.shortTermScheduler = shortTermScheduler;
        this.longTermScheduler = longTermScheduler;
    }

    public void createAndShowGUI() {
        // Criando Frame
        JFrame frame = new JFrame("Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Adicionando paineis de Log
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new GridLayout(3, 1));
        logPanel.add(parserLogPanel);
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
        frame.setVisible(true);

        this.createColorHashMap();
    }

    public void run() {
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createColorHashMap() {
        colorMap.put("<1>", Color.BLACK);
        colorMap.put("<2>", Color.RED);
        colorMap.put("<3>", Color.BLUE);
    }

    @Override
    public void display(String info) {
        SwingUtilities.invokeLater(() -> {
            String[] command = info.split(" ");

            // Parseamento das instruções para cada painel e cor
            switch (command[0]){
                case "<ss>":
                    this.shortTermSchedulerLogPanel.append(info.substring(8), colorMap.get(command[1]));
                    break;
                case "<ls>":
                    this.longTermSchedulerLogPanel.append(info.substring(8), colorMap.get(command[1]));
                    break;
                case "<is>":
                    this.parserLogPanel.append(info.substring(8), colorMap.get(command[1]));
                    break;
            }
        });
    }
}
