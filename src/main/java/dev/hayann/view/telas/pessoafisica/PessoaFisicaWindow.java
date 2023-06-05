package dev.hayann.view.telas.pessoafisica;

import dev.hayann.model.PessoaFisica;
import dev.hayann.model.Proprietario;
import dev.hayann.repository.PessoaFisicaRepository;
import dev.hayann.repository.ProdutoRepository;
import dev.hayann.view.campos.combobox.PessoaFisicaComboBox;
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

public class PessoaFisicaWindow {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private PessoaFisicaRepository pessoaFisicaRepository = new PessoaFisicaRepository();
    private ProdutoRepository produtoRepository = new ProdutoRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox proprietarioComboBox = ProprietarioComboBox.getProprietarioComboBox();
    private JTextField nameField = new JTextField();
    private JTextField CPFField = NumberTextField.getNumberTextField();
    private JTextField RGField = NumberTextField.getNumberTextField();
    private JTextField nascimentoField = DateTextField.getDateTextField();
    private JComboBox conjugeComboBox = PessoaFisicaComboBox.getPessoaFisicaComboBox();

    public PessoaFisicaWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Proprietário");
        tableModel.addColumn("Nome");
        tableModel.addColumn("CPF");
        tableModel.addColumn("RG");
        tableModel.addColumn("Nascimento");
        tableModel.addColumn("Cônjuge");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel proprietarioLabel = new JLabel("Proprietário:");
        JLabel nameLabel = new JLabel("Nome:");
        JLabel CPFLabel = new JLabel("CPF:");
        JLabel RGLabel = new JLabel("RG:");
        JLabel nascimentoLabel = new JLabel("Nascimento");
        JLabel conjugeLabel = new JLabel("Cônjuge:");

        JPanel proprietarioPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        proprietarioPanel.add(proprietarioLabel);
        proprietarioPanel.add(proprietarioComboBox);
        formPanel.add(proprietarioPanel);

        JPanel namePanel = new JPanel(new GridLayout(1, 2, -10, 10));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        formPanel.add(namePanel);

        JPanel CPFPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        CPFPanel.add(CPFLabel);
        CPFPanel.add(CPFField);
        formPanel.add(CPFPanel);

        JPanel RGPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        RGPanel.add(RGLabel);
        RGPanel.add(RGField);
        formPanel.add(RGPanel);

        JPanel nascimentoPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        nascimentoPanel.add(nascimentoLabel);
        nascimentoPanel.add(nascimentoField);
        formPanel.add(nascimentoPanel);

        JPanel conjugePanel = new JPanel(new GridLayout(1, 2, -10, 10));
        conjugePanel.add(conjugeLabel);
        conjugePanel.add(conjugeComboBox);
        formPanel.add(conjugePanel);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            if (proprietarioComboBox.getSelectedItem() == null || proprietarioComboBox.getSelectedIndex() == 0) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.PROPRIETARIO_EMPTY_COMBO_BOX_ERROR);
            } else {
                String idProprietario = ((Proprietario) Objects.requireNonNull(proprietarioComboBox.getSelectedItem())).getId().toString();
                String name = nameField.getText();
                String CPF = CPFField.getText();
                String RG = RGField.getText();
                String nascimento = nascimentoField.getText();

                if (idProprietario.isEmpty() || name.isEmpty() || CPF.isEmpty() || RG.isEmpty() || nascimento.isEmpty()) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
                } else {
                    try {
                        Integer idConjuge = conjugeComboBox.getSelectedItem() == null || conjugeComboBox.getSelectedIndex() == 0 ? null : ((PessoaFisica) (conjugeComboBox.getSelectedItem())).getIdProprietarioPessoaFisica();
                        PessoaFisica pessoaFisica = new PessoaFisica(
                                Integer.parseInt(idProprietario),
                                Integer.parseInt(CPF),
                                Integer.parseInt(RG),
                                name,
                                LocalDate.parse(nascimento, formatter),
                                idConjuge
                        );
                        pessoaFisicaRepository.persist(pessoaFisica);
                        addRow(pessoaFisica);
                        clearFields();
                        PessoaFisicaComboBox.reloadPessoaFisicaComboBox();
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
                    String name = tableModel.getValueAt(selectedRow, 1).toString();
                    String CPF = tableModel.getValueAt(selectedRow, 2).toString();
                    String RG = tableModel.getValueAt(selectedRow, 3).toString();
                    String nascimento = tableModel.getValueAt(selectedRow, 4).toString();
                    PessoaFisica conjuge = pessoaFisicaRepository.findByName(tableModel.getValueAt(selectedRow, 5).toString());
                    Integer idConjuge = conjuge == null ? null : conjuge.getIdProprietarioPessoaFisica();
                    PessoaFisica pessoaFisica = new PessoaFisica(
                            Integer.parseInt(idProprietario),
                            Integer.parseInt(CPF),
                            Integer.parseInt(RG),
                            name,
                            LocalDate.parse(nascimento, formatter),
                            idConjuge
                    );
                    if (openUpdateDialog(pessoaFisica)) {
                        pessoaFisicaRepository.update(pessoaFisica);
                        loadData();
                        PessoaFisicaComboBox.reloadPessoaFisicaComboBox();
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
                        pessoaFisicaRepository.delete(id);
                        tableModel.removeRow(selectedRow);
                        PessoaFisicaComboBox.reloadPessoaFisicaComboBox();
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

    private void addRow(PessoaFisica pessoaFisica) {
        Vector<String> row = new Vector<>();
        row.add(pessoaFisica.getIdProprietarioPessoaFisica().toString());
        row.add(pessoaFisica.getName());
        row.add(pessoaFisica.getCpf().toString());
        row.add(pessoaFisica.getRg().toString());
        row.add(pessoaFisica.getDataNascimento().format(formatter));
        if (pessoaFisica.getIdConjuge() != null)
            row.add(pessoaFisica.getIdConjuge().toString());
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
            java.util.List<PessoaFisica> producoes = pessoaFisicaRepository.findAll();
            producoes.forEach(this::addRow);
        } catch (Exception exception) {
            exception.printStackTrace();
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    private boolean openUpdateDialog(PessoaFisica pessoaFisica) {
        PessoaFisicaUpdate dialog = new PessoaFisicaUpdate((Frame) SwingUtilities.getWindowAncestor(panel), pessoaFisica);
        dialog.setVisible(true);
        return dialog.isUpdated();
    }

    private void clearFields() {
        nameField.setText("");
        CPFField.setText("");
        RGField.setText("");
        nascimentoField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
