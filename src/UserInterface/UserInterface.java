package UserInterface;

//UI Modules
import javax.swing.*;
import UserInterface.UIComponents.TextArea;
import UserInterface.UIComponents.Buttons;

//Java utils
import java.awt.*;

public class UserInterface implements NotificationInterface, Runnable {
    private SubmissionInterface longTermScheduler;
    private ControlInterface shortTermScheduler;
    private TextArea textArea;

    public UserInterface(){
        this.textArea = new TextArea();
    }

    public void setThreads(ControlInterface shortTermScheduler, SubmissionInterface longTermScheduler){
        this.shortTermScheduler = shortTermScheduler;
        this.longTermScheduler = longTermScheduler;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("UserIntarface GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criação de uma instância de TextArea
        TextArea textAreaPanel = textArea;
        frame.getContentPane().add(textAreaPanel, BorderLayout.CENTER);

        // Adicionando botões (ou outros componentes) abaixo do TextArea
        JPanel buttonPanel = new Buttons(longTermScheduler);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Criando a visualização GUI
        frame.pack();
        frame.setVisible(true);

    }

    public void run(){
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    @Override
    public void display(String info) {
        this.textArea.append(info);
    }
}