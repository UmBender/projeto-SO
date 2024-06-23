package UserInterface.UIComponents;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;

public class TextPanel extends JScrollPane {
    private JTextPane textPane;
    private static final String newline = "\n";
    private Style style;

    public TextPanel(String title) {
        // Criando os componentes
        textPane = new JTextPane();
        textPane.setEditable(false);

        // Adicionando Título
        Border border = BorderFactory.createTitledBorder(title);
        this.setBorder(border);

        // Previne que o Scroll fique automaticamente acompanhando a linha adicionada
        DefaultCaret caret = (DefaultCaret) textPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        // Adicionando estilos
        style = textPane.addStyle("Style", null);
        StyleConstants.setForeground(style, Color.BLACK);
        StyleConstants.setFontFamily(style, "Serif");
        StyleConstants.setFontSize(style, 12);

        // Configurando para o texto ser usado na visualização
        setViewportView(textPane);
    }

    // Método para anexar os logs
    public void append(String text, Color color) {
        SwingUtilities.invokeLater(() -> {
            // Obtém o documento
            StyledDocument doc = textPane.getStyledDocument();

            // Adiciona texto ao painel
            try {
                JScrollBar verticalScrollBar = getVerticalScrollBar();
                boolean wasAtBottom = isAtBottom(verticalScrollBar);

                // Insere o texto com o estilo
                StyleConstants.setForeground(style, color);
                doc.insertString(doc.getLength(), text + newline, style);

                // Adiciona o comportamento de scroll automático, caso esteja na última linha
                if (wasAtBottom) {
                    textPane.setCaretPosition(textPane.getDocument().getLength());
                } else {
                    verticalScrollBar.setValue(verticalScrollBar.getValue());
                }

            } catch (BadLocationException e) {
                e.printStackTrace();
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