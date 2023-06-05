package dev.hayann.view.dialog;

import javax.swing.*;
import java.awt.*;

public class WarningDialog extends JDialog {

    private JButton confirmButton;

    private JButton cancelButton;

    private boolean confirmado = false;

    public WarningDialog(JFrame parent, String message) {
        super(parent, "Atenção", true);

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

        getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(e -> {
            confirmado = true;
            dispose();
        });
        buttonPanel.add(confirmButton);

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
            confirmado = false;
            dispose();
        });
        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public boolean isConfirmed() {
        return confirmado;
    }
}
