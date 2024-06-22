package UserInterface.UIComponents;

import javax.swing.*;
import javax.swing.border.Border;

public class TextPanel extends JScrollPane {
    private JTextArea textArea;
    private static final String newline = "\n";

    public TextPanel(String title) {
        // Criando os componentes
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);

        // Adicioando Título
        Border border = BorderFactory.createTitledBorder(title);
        this.setBorder(border);

        // Configurando para o texto ser usado na visualização
        setViewportView(textArea);
    }

    // Método para anexar os logs
    public void append(String text) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(text + newline);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}
