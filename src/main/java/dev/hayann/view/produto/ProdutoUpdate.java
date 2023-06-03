package dev.hayann.view.produto;

import dev.hayann.model.Produto;

import javax.swing.*;
import java.awt.*;

public class ProdutoUpdate extends JDialog{

    private JTextField txtId;
    private JTextField txtDescription;

    boolean salvo = false;

    public ProdutoUpdate(Frame parent, Produto produto) {
        super(parent, "Atualizar Cadastro", true);
        setLayout(new BorderLayout());
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(produto.getId().toString());
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        txtId.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblDescription = new JLabel("Descrição:");
        txtDescription = new JTextField(produto.getDescricao());
        txtDescription.setBackground(Color.WHITE);
        txtDescription.setBorder(BorderFactory.createEtchedBorder());


        panel.add(lblId);
        panel.add(txtId);
        panel.add(lblDescription);
        panel.add(txtDescription);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            produto.setDescricao(txtDescription.getText());
            salvo = true;
            dispose();
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            salvo = false;
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean isUpdated() {
        return salvo;
    }
}
