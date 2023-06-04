package dev.hayann.view.campos;

import javax.swing.*;
import javax.swing.text.*;

public class DateTextField {

    public static JFormattedTextField getDateTextField() {

        MaskFormatter maskFormatter;
        try {
            maskFormatter = new MaskFormatter("##/##/####");
            maskFormatter.setPlaceholderCharacter(' ');
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        JFormattedTextField formattedTextField = new JFormattedTextField(maskFormatter);
        AbstractDocument document = (AbstractDocument) formattedTextField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            private boolean isNumber(String text) {
                return text.matches("\\d*");
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
