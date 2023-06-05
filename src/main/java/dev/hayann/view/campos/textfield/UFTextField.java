package dev.hayann.view.campos.textfield;

import javax.swing.*;
import javax.swing.text.*;

public class UFTextField {

    public static JFormattedTextField getUFTextField() {

        JFormattedTextField formattedTextField = new JFormattedTextField();
        AbstractDocument document = (AbstractDocument) formattedTextField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            private final int maxLength = 2;
            private final String regexPattern = "[a-zA-Z]*";

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newStr.length() <= maxLength && newStr.matches(regexPattern)) {
                    super.insertString(fb, offset, text, attrs);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength());
                newStr = newStr.substring(0, offset) + text + newStr.substring(offset + length);
                if (newStr.length() <= maxLength && newStr.matches(regexPattern)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        return formattedTextField;
    }
}
