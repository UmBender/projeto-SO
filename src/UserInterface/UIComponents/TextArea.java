package UserInterface.UIComponents;

import javax.swing.*;
import java.awt.*;

public class TextArea extends JPanel {
    private JTextArea textArea;
    private static final String newline = "\n";

    public TextArea() {
        super(new GridBagLayout());

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Configuração do GridBagLayout
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }

    public void append(String text){
        SwingUtilities.invokeLater(() -> {
            textArea.append(text + newline);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}
