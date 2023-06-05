package dev.hayann.view.telas.proprietariopropriedade;

import dev.hayann.model.ProprietarioPropriedade;
import dev.hayann.view.campos.textfield.DateTextField;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProprietarioPropriedadeUpdate extends JDialog {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JTextField proprietarioIdFields = new JTextField();
    private JTextField propriedadeIdFields = new JTextField();
    private JTextField dataAquisicaoField = DateTextField.getDateTextField();

    boolean salvo = false;

    public ProprietarioPropriedadeUpdate(Frame parent, ProprietarioPropriedade proprietarioPropriedade) {
        super(parent, "Atualizar Cadastro", true);
        setLayout(new BorderLayout());
        setSize(330, 270);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblProprietario = new JLabel("Proprietário:");
        proprietarioIdFields.setText(proprietarioPropriedade.getIdProprietario().toString());
        proprietarioIdFields.setEditable(false);
        proprietarioIdFields.setBackground(Color.LIGHT_GRAY);
        proprietarioIdFields.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblPropriedade = new JLabel("Proprietário:");
        propriedadeIdFields.setText(proprietarioPropriedade.getIdPropriedade().toString());
        propriedadeIdFields.setEditable(false);
        propriedadeIdFields.setBackground(Color.LIGHT_GRAY);
        propriedadeIdFields.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblDataCriacao = new JLabel("Data de Criação:");
        dataAquisicaoField.setText(proprietarioPropriedade.getDateAcquisition().format(formatter));
        dataAquisicaoField.setBackground(Color.WHITE);
        dataAquisicaoField.setBorder(BorderFactory.createEtchedBorder());

        panel.add(lblProprietario);
        panel.add(proprietarioIdFields);
        panel.add(lblPropriedade);
        panel.add(propriedadeIdFields);
        panel.add(lblDataCriacao);
        panel.add(dataAquisicaoField);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                String dataAquisicao = dataAquisicaoField.getText();
                if (dataAquisicao.isEmpty()) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(parent), GenericMessages.ERROR_INCOMPLETE_FIELD);
                } else {
                    proprietarioPropriedade.setDateAcquisition(LocalDate.parse(dataAquisicao, formatter));
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
