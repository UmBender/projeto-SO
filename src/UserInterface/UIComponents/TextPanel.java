package UserInterface.UIComponents;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

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

        // Previne que o Scroll fique automáticamente acompanhando a linha adicionada
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        // Configurando para o texto ser usado na visualização
        setViewportView(textArea);
    }

    // Método para anexar os logs
    public void append(String text) {
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            boolean wasAtBottom = isAtBottom(verticalScrollBar);

            textArea.append(text + newline);

            // Adiciona o comportamento de scroll automático, caso esteja na última linha
            if (wasAtBottom) {
                textArea.setCaretPosition(textArea.getDocument().getLength());
            } else {
                verticalScrollBar.setValue(verticalScrollBar.getValue());
            }
        });
    }

    private boolean isAtBottom(JScrollBar verticalScrollBar) {
        int value = verticalScrollBar.getValue();
        int extent = verticalScrollBar.getModel().getExtent();
        int maximum = verticalScrollBar.getMaximum();
        return value + extent == maximum;
    }
}
