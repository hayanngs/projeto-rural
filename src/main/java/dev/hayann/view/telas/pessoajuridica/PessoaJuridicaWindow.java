package dev.hayann.view.telas.pessoajuridica;

import dev.hayann.model.PessoaJuridica;
import dev.hayann.model.Proprietario;
import dev.hayann.repository.PessoaJuridicaRepository;
import dev.hayann.repository.ProdutoRepository;
import dev.hayann.view.campos.combobox.PessoaJuridicaComboBox;
import dev.hayann.view.campos.combobox.ProprietarioComboBox;
import dev.hayann.view.campos.textfield.DateTextField;
import dev.hayann.view.campos.textfield.NumberTextField;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.dialog.WarningDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Vector;

public class PessoaJuridicaWindow {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private PessoaJuridicaRepository pessoaJuridicaRepository = new PessoaJuridicaRepository();
    private ProdutoRepository produtoRepository = new ProdutoRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox proprietarioComboBox = ProprietarioComboBox.getProprietarioComboBoxPessoaJuridica();
    private JTextField CNPJField = NumberTextField.getNumberTextField();
    private JTextField razaoSocialField = new JTextField();
    private JTextField dataCriacaoField = DateTextField.getDateTextField();

    public PessoaJuridicaWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Proprietário");
        tableModel.addColumn("Razão Social");
        tableModel.addColumn("CNPJ");
        tableModel.addColumn("Data de Criação");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel proprietarioLabel = new JLabel("Proprietário:");
        JLabel razaoSocialLabel = new JLabel("Razão Social:");
        JLabel CNPJLabel = new JLabel("CNPJ:");
        JLabel dataCriacaoLabel = new JLabel("Data de Criação:");

        JPanel proprietarioPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        proprietarioPanel.add(proprietarioLabel);
        proprietarioPanel.add(proprietarioComboBox);
        formPanel.add(proprietarioPanel);

        JPanel razaoSocialPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        razaoSocialPanel.add(razaoSocialLabel);
        razaoSocialPanel.add(razaoSocialField);
        formPanel.add(razaoSocialPanel);

        JPanel CNPJPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        CNPJPanel.add(CNPJLabel);
        CNPJPanel.add(CNPJField);
        formPanel.add(CNPJPanel);

        JPanel dataCriacaoPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        dataCriacaoPanel.add(dataCriacaoLabel);
        dataCriacaoPanel.add(dataCriacaoField);
        formPanel.add(dataCriacaoPanel);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            if (proprietarioComboBox.getSelectedItem() == null || proprietarioComboBox.getSelectedIndex() == 0) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.PROPRIETARIO_EMPTY_COMBO_BOX_ERROR);
            } else {
                String idProprietario = ((Proprietario) Objects.requireNonNull(proprietarioComboBox.getSelectedItem())).getId().toString();
                String razaoSocial = razaoSocialField.getText();
                String CNPJ = CNPJField.getText();
                String dataCriacao = dataCriacaoField.getText();

                if (idProprietario.isEmpty() || razaoSocial.isEmpty() || CNPJ.isEmpty() || dataCriacao.isEmpty()) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
                } else {
                    try {
                        PessoaJuridica pessoaJuridica = new PessoaJuridica(
                                Integer.parseInt(idProprietario),
                                Integer.parseInt(CNPJ),
                                razaoSocial,
                                LocalDate.parse(dataCriacao, formatter)
                        );
                        pessoaJuridicaRepository.persist(pessoaJuridica);
                        addRow(pessoaJuridica);
                        clearFields();
                        PessoaJuridicaComboBox.reloadPessoaJuridicaComboBox();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INSERT);
                    }
                }
            }
        });

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    String idProprietario = tableModel.getValueAt(selectedRow, 0).toString();
                    String razaoSocial = tableModel.getValueAt(selectedRow, 1).toString();
                    String CNPJ = tableModel.getValueAt(selectedRow, 2).toString();
                    String dataCriacao = tableModel.getValueAt(selectedRow, 3).toString();
                    PessoaJuridica pessoaJuridica = new PessoaJuridica(
                            Integer.parseInt(idProprietario),
                            Integer.parseInt(CNPJ),
                            razaoSocial,
                            LocalDate.parse(dataCriacao, formatter)
                    );
                    if (openUpdateDialog(pessoaJuridica)) {
                        pessoaJuridicaRepository.update(pessoaJuridica);
                        loadData();
                        PessoaJuridicaComboBox.reloadPessoaJuridicaComboBox();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_UPDATE);
                }
            }
        });

        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    WarningDialog warningDialog = new WarningDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.WARNING_DELETE);
                    if (warningDialog.isConfirmed()) {
                        Integer id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                        pessoaJuridicaRepository.delete(id);
                        tableModel.removeRow(selectedRow);
                        PessoaJuridicaComboBox.reloadPessoaJuridicaComboBox();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_DELETE);
                }
            }
        });

        JButton reloadButton = new JButton("Recarregar");
        reloadButton.addActionListener(e -> loadData());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(reloadButton);

        JPanel buttonContainerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        buttonContainerPanel.add(buttonPanel, gbc);

        formPanel.add(buttonContainerPanel);

        panel.add(formPanel, BorderLayout.SOUTH);
        loadData();
    }

    private void addRow(PessoaJuridica pessoaJuridica) {
        Vector<String> row = new Vector<>();
        row.add(pessoaJuridica.getId().toString());
        row.add(pessoaJuridica.getRazaoSocial());
        row.add(pessoaJuridica.getCnpj().toString());
        row.add(pessoaJuridica.getDateCreation().format(formatter));
        tableModel.addRow(row);
    }

    private void cleanTable() {
        int totalRows = tableModel.getRowCount();
        for (int i = totalRows - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void loadData() {
        try {
            cleanTable();
            java.util.List<PessoaJuridica> producoes = pessoaJuridicaRepository.findAll();
            producoes.forEach(this::addRow);
        } catch (Exception exception) {
            exception.printStackTrace();
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    private boolean openUpdateDialog(PessoaJuridica pessoaJuridica) {
        PessoaJuridicaUpdate dialog = new PessoaJuridicaUpdate((Frame) SwingUtilities.getWindowAncestor(panel), pessoaJuridica);
        dialog.setVisible(true);
        return dialog.isUpdated();
    }

    private void clearFields() {
        CNPJField.setText("");
        razaoSocialField.setText("");
        dataCriacaoField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
