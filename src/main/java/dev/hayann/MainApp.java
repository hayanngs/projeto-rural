package dev.hayann;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JTabbedPane tabbedPane;

    public MainApp() {
        // Configurações da janela
        setTitle("Sistema de Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Criar o JTabbedPane
        tabbedPane = new JTabbedPane();

        // Adicionar a tela do Município
        MunicipioWindow municipioWindow = new MunicipioWindow();
        tabbedPane.addTab("Município", municipioWindow.getPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}
