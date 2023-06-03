package dev.hayann.view.municipio;

import dev.hayann.model.Municipio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MunicipioUpdate extends JDialog {
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtUf;

    boolean salvo = false;

    public MunicipioUpdate(Frame parent, Municipio municipio) {
        super(parent, "Atualizar Cadastro", true);
        setLayout(new BorderLayout());
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(municipio.getId().toString());
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        txtId.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(municipio.getName());
        txtNome.setBackground(Color.WHITE);
        txtNome.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblUf = new JLabel("UF:");
        txtUf = new JTextField(municipio.getUf());
        txtUf.setBackground(Color.WHITE);
        txtUf.setBorder(BorderFactory.createEtchedBorder());

        panel.add(lblId);
        panel.add(txtId);
        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblUf);
        panel.add(txtUf);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            municipio.setName(txtNome.getText());
            municipio.setUf(txtUf.getText());
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
