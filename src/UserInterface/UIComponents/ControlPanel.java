package UserInterface.UIComponents;

import UserInterface.ControlInterface;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.*;

public class ControlPanel extends JPanel {
    public ControlPanel(ControlInterface actor) {
        this.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start");

        JButton suspendButton = new JButton("Suspend");
        JButton resumeButton = new JButton("Resume");
        JButton stopButton = new JButton("Stop");

        startButton.addActionListener(e -> actor.startSimulation());
        suspendButton.addActionListener(e -> actor.suspendSimulation());
        resumeButton.addActionListener(e -> actor.resumeSimulation());
        stopButton.addActionListener(e -> actor.stopSimulation());

        this.add(startButton);
        this.add(suspendButton);
        this.add(resumeButton);
        this.add(stopButton);
    }
}