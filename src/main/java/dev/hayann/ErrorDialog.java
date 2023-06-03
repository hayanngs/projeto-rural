package dev.hayann;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ErrorDialog extends JDialog {
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JButton closeButton;

    public ErrorDialog(JFrame parent, String message) {
        super(parent, true);
        setUndecorated(true);

        // Barra de título personalizada
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.GRAY);

        titleLabel = new JLabel("Erro");
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.WEST);

        closeButton = new JButton("X");
        closeButton.setFocusable(false);
        closeButton.setBackground(Color.GRAY);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        titlePanel.add(closeButton, BorderLayout.EAST);

        getContentPane().add(titlePanel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        panel.add(label);

        getContentPane().add(panel, BorderLayout.CENTER);

        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 150));
        pack();
        setLocationRelativeTo(parent);

        setVisible(true);
    }
}
