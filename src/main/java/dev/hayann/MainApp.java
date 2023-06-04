package dev.hayann;

import dev.hayann.database.InicializadorDoBD;
import dev.hayann.view.municipio.MunicipioWindow;
import dev.hayann.view.producao.ProducaoWindow;
import dev.hayann.view.produto.ProdutoWindow;
import dev.hayann.view.propriedade.PropriedadeWindow;
import dev.hayann.view.proprietario.ProprietarioWindow;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JTabbedPane tabbedPane;

    public static MunicipioWindow municipioWindow = new MunicipioWindow();

    public static ProdutoWindow produtoWindow = new ProdutoWindow();

    public static PropriedadeWindow propriedadeWindow = new PropriedadeWindow();

    public static ProprietarioWindow proprietarioWindow = new ProprietarioWindow();

    public static ProducaoWindow producaoWindow = new ProducaoWindow();

    public MainApp() {
        setTitle("Gerenciamento Rural");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Município", municipioWindow.getPanel());
        tabbedPane.addTab("Produto", produtoWindow.getPanel());
        tabbedPane.addTab("Proprieade", propriedadeWindow.getPanel());
        tabbedPane.addTab("Proprietario", proprietarioWindow.getPanel());
        tabbedPane.addTab("Produção", producaoWindow.getPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        InicializadorDoBD.inicializarBancoDeDados();
        SwingUtilities.invokeLater(MainApp::new);
    }
}
