package dev.hayann.view.pessoafisica;

import dev.hayann.model.PessoaFisica;
import dev.hayann.view.campos.combobox.PessoaFisicaComboBox;
import dev.hayann.view.campos.textfield.DateTextField;
import dev.hayann.view.campos.textfield.NumberTextField;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PessoaFisicaUpdate extends JDialog {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JTextField proprietarioFields = new JTextField();
    private JTextField nameField = new JTextField();
    private JTextField CPFField = NumberTextField.getNumberTextField();
    private JTextField RGField = NumberTextField.getNumberTextField();
    private JTextField nascimentoField = DateTextField.getDateTextField();
    private JComboBox conjugeComboBox = PessoaFisicaComboBox.getPessoaFisicaComboBoxUpdate();

    boolean salvo = false;

    public PessoaFisicaUpdate(Frame parent, PessoaFisica pessoaFisica) {
        super(parent, "Atualizar Cadastro", true);
        setLayout(new BorderLayout());
        setSize(330, 370);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblProprietario = new JLabel("Proprietário:");
        proprietarioFields.setText(pessoaFisica.getIdProprietarioPessoaFisica().toString());
        proprietarioFields.setEditable(false);
        proprietarioFields.setBackground(Color.LIGHT_GRAY);
        proprietarioFields.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblNome = new JLabel("Nome:");
        nameField.setText(pessoaFisica.getName());
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblCPF = new JLabel("CPF:");
        CPFField.setText(pessoaFisica.getCpf().toString());
        CPFField.setBackground(Color.WHITE);
        CPFField.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblRG = new JLabel("RG:");
        RGField.setText(pessoaFisica.getRg().toString());
        RGField.setBackground(Color.WHITE);
        RGField.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblNascimento = new JLabel("Data Nascimento:");
        nascimentoField.setText(pessoaFisica.getDataNascimento().format(formatter));
        nascimentoField.setBackground(Color.WHITE);
        nascimentoField.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblConjuge = new JLabel("Cônjuge:");
        conjugeComboBox.setBackground(Color.WHITE);
        conjugeComboBox.setBorder(BorderFactory.createEtchedBorder());

        panel.add(lblProprietario);
        panel.add(proprietarioFields);
        panel.add(lblNome);
        panel.add(nameField);
        panel.add(lblCPF);
        panel.add(CPFField);
        panel.add(lblRG);
        panel.add(RGField);
        panel.add(lblNascimento);
        panel.add(nascimentoField);
        panel.add(lblConjuge);
        panel.add(conjugeComboBox);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            pessoaFisica.setName(nameField.getText());
            pessoaFisica.setCpf(Integer.parseInt(CPFField.getText()));
            pessoaFisica.setRg(Integer.parseInt(RGField.getText()));
            pessoaFisica.setDataNascimento(LocalDate.parse(nascimentoField.getText(), formatter));
            pessoaFisica.setIdConjuge(((PessoaFisica) Objects.requireNonNull(conjugeComboBox.getSelectedItem())).getIdProprietarioPessoaFisica());
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
