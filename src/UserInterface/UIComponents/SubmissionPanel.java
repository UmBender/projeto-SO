package UserInterface.UIComponents;

import UserInterface.SubmissionInterface;

import javax.swing.*;

import java.awt.*;

public class SubmissionPanel extends JPanel {
    public SubmissionPanel(SubmissionInterface actor) {
        this.setLayout(new FlowLayout());

        // Input Path Area
        JLabel jobLabel = new JLabel("Job File:");
        JTextField jobField = new JTextField(20);
        JTextArea textArea = new JTextArea();

        // Submit Button
        JButton submitButton = new JButton("Submit Job");
        submitButton.addActionListener(e -> {
            String fileName = jobField.getText();
            boolean success = actor.submitJob(fileName);
        });

        // Display Queues button
        JButton displayButton = new JButton("Display LongTearm Queues");
        displayButton.addActionListener(e -> actor.displaySubmissionQueue());

        this.add(jobLabel);
        this.add(jobField);
        this.add(textArea);
        this.add(submitButton);
        this.add(displayButton);
    }
}