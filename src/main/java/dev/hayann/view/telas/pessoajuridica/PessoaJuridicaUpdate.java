package dev.hayann.view.telas.pessoajuridica;

import dev.hayann.model.PessoaJuridica;
import dev.hayann.view.campos.textfield.DateTextField;
import dev.hayann.view.campos.textfield.NumberTextField;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PessoaJuridicaUpdate extends JDialog {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JTextField proprietarioFields = new JTextField();
    private JTextField CNPJField = NumberTextField.getNumberTextField();
    private JTextField razaoSocialField = new JTextField();
    private JTextField dataCriacaoField = DateTextField.getDateTextField();

    boolean salvo = false;

    public PessoaJuridicaUpdate(Frame parent, PessoaJuridica pessoaJuridica) {
        super(parent, "Atualizar Cadastro", true);
        setLayout(new BorderLayout());
        setSize(330, 270);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblProprietario = new JLabel("Proprietário:");
        proprietarioFields.setText(pessoaJuridica.getId().toString());
        proprietarioFields.setEditable(false);
        proprietarioFields.setBackground(Color.LIGHT_GRAY);
        proprietarioFields.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblRazaoSocial = new JLabel("Razão Social:");
        razaoSocialField.setText(pessoaJuridica.getRazaoSocial());
        razaoSocialField.setBackground(Color.WHITE);
        razaoSocialField.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblCNPJ = new JLabel("CNPJ:");
        CNPJField.setText(pessoaJuridica.getCnpj().toString());
        CNPJField.setBackground(Color.WHITE);
        CNPJField.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblDataCriacao = new JLabel("Data de Criação:");
        dataCriacaoField.setText(pessoaJuridica.getDateCreation().format(formatter));
        dataCriacaoField.setBackground(Color.WHITE);
        dataCriacaoField.setBorder(BorderFactory.createEtchedBorder());

        panel.add(lblProprietario);
        panel.add(proprietarioFields);
        panel.add(lblRazaoSocial);
        panel.add(razaoSocialField);
        panel.add(lblCNPJ);
        panel.add(CNPJField);
        panel.add(lblDataCriacao);
        panel.add(dataCriacaoField);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                String razaoSocial = razaoSocialField.getText();
                String CNPJ = CNPJField.getText();
                String dataCriacao = dataCriacaoField.getText();
                if (razaoSocial.isEmpty() || CNPJ.isEmpty() || dataCriacao.isEmpty()) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(parent), GenericMessages.ERROR_INCOMPLETE_FIELD);
                } else {
                    pessoaJuridica.setRazaoSocial(razaoSocial);
                    pessoaJuridica.setCnpj(Long.parseLong(CNPJ));
                    pessoaJuridica.setDateCreation(LocalDate.parse(dataCriacao, formatter));
                    salvo = true;
                    dispose();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(parent), GenericMessages.ERROR_INCOMPLETE_FIELD);
            }
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
