package dev.hayann.view.campos.textfield;

import javax.swing.*;
import javax.swing.text.*;

public class NumberTextField {

    public static JFormattedTextField getNumberTextField() {

        JFormattedTextField formattedTextField = new JFormattedTextField();
        AbstractDocument document = (AbstractDocument) formattedTextField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            private boolean isNumber(String text) {
                return text.matches("\\d+\\.?\\d*?");
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, text);

                if (isNumber(sb.toString())) {
                    super.insertString(fb, offset, text, attrs);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);

                if (isNumber(sb.toString())) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        return formattedTextField;
    }
}
