package dev.hayann.view.proprietario;

import dev.hayann.model.Proprietario;
import dev.hayann.view.campos.textfield.NumberTextField;

import javax.swing.*;
import java.awt.*;

public class ProprietarioUpdate extends JDialog {

    private JTextField txtId;

    private JTextField nameField;

    private JTextField telefone1Field = NumberTextField.getNumberTextField();

    private JTextField telefone2Field = NumberTextField.getNumberTextField();

    private JTextField telefone3Field = NumberTextField.getNumberTextField();

    boolean salvo = false;

    public ProprietarioUpdate(Frame parent, Proprietario proprietario) {
        super(parent, "Atualizar Cadastro", true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(proprietario.getId().toString());
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        txtId.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblName = new JLabel("Nome:");
        nameField = new JTextField(proprietario.getName());
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblTelefone1 = new JLabel("Telefone 1:");
        telefone1Field.setText(proprietario.getTelefone1().toString());
        telefone1Field.setBackground(Color.WHITE);
        telefone1Field.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblTelefone2 = new JLabel("Telefone 2:");
        telefone2Field.setText(proprietario.getTelefone2().toString());
        telefone2Field.setBackground(Color.WHITE);
        telefone2Field.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblTelefone3 = new JLabel("Telefone 3:");
        telefone3Field.setText(proprietario.getTelefone3().toString());
        telefone3Field.setBackground(Color.WHITE);
        telefone3Field.setBorder(BorderFactory.createEtchedBorder());

        panel.add(lblId);
        panel.add(txtId);
        panel.add(lblName);
        panel.add(nameField);
        panel.add(lblTelefone1);
        panel.add(telefone1Field);
        panel.add(lblTelefone2);
        panel.add(telefone2Field);
        panel.add(lblTelefone3);
        panel.add(telefone3Field);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            proprietario.setName(nameField.getText());
            proprietario.setTelefone1(telefone1Field.getText().isEmpty() ? 0 : Long.parseLong(telefone1Field.getText()));
            proprietario.setTelefone2(telefone2Field.getText().isEmpty() ? 0 : Long.parseLong(telefone2Field.getText()));
            proprietario.setTelefone3(telefone3Field.getText().isEmpty() ? 0 : Long.parseLong(telefone3Field.getText()));
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
