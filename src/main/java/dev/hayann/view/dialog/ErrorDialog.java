package dev.hayann.view.dialog;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends JDialog {

    private JButton confirmButton;

    public ErrorDialog(JFrame parent, String message) {
        super(parent, "Erro", true);

        setUndecorated(true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        panel.add(label);

        getContentPane().add(panel);

        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 150));
        pack();
        setLocationRelativeTo(parent);

        getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(e -> {
            dispose();
        });
        buttonPanel.add(confirmButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
