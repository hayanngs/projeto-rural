package dev.hayann;

import dev.hayann.database.InicializadorDoBD;
import dev.hayann.view.telas.donopessoajuridica.DonoPessoaJuridicaWindow;
import dev.hayann.view.telas.municipio.MunicipioWindow;
import dev.hayann.view.telas.pessoafisica.PessoaFisicaWindow;
import dev.hayann.view.telas.pessoajuridica.PessoaJuridicaWindow;
import dev.hayann.view.telas.producao.ProducaoWindow;
import dev.hayann.view.telas.produto.ProdutoWindow;
import dev.hayann.view.telas.propriedade.PropriedadeWindow;
import dev.hayann.view.telas.proprietario.ProprietarioWindow;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JTabbedPane tabbedPane;

    public static MunicipioWindow municipioWindow = new MunicipioWindow();

    public static ProdutoWindow produtoWindow = new ProdutoWindow();

    public static PropriedadeWindow propriedadeWindow = new PropriedadeWindow();

    public static ProprietarioWindow proprietarioWindow = new ProprietarioWindow();

    public static ProducaoWindow producaoWindow = new ProducaoWindow();

    public static PessoaFisicaWindow pessoaFisicaWindow = new PessoaFisicaWindow();

    public static PessoaJuridicaWindow pessoaJuridicaWindow = new PessoaJuridicaWindow();

    public static DonoPessoaJuridicaWindow donoPessoaJuridicaWindow = new DonoPessoaJuridicaWindow();

    public MainApp() {
        setTitle("Gerenciamento Rural");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Município", municipioWindow.getPanel());
        tabbedPane.addTab("Produto", produtoWindow.getPanel());
        tabbedPane.addTab("Proprieade", propriedadeWindow.getPanel());
        tabbedPane.addTab("Produção", producaoWindow.getPanel());
        tabbedPane.addTab("Proprietario", proprietarioWindow.getPanel());
        tabbedPane.addTab("Pessoa Física", pessoaFisicaWindow.getPanel());
        tabbedPane.addTab("Pessoa Jurídica", pessoaJuridicaWindow.getPanel());
        tabbedPane.addTab("Dono Pessoa Jurídica", donoPessoaJuridicaWindow.getPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        InicializadorDoBD.inicializarBancoDeDados();
        SwingUtilities.invokeLater(MainApp::new);
    }
}
