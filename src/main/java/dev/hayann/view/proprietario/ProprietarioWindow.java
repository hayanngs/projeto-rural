package dev.hayann.view.proprietario;

import dev.hayann.model.Proprietario;
import dev.hayann.repository.ProprietarioRepository;
import dev.hayann.view.campos.combobox.ProprietarioComboBox;
import dev.hayann.view.campos.textfield.NumberTextField;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.dialog.WarningDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class ProprietarioWindow {

    private ProprietarioRepository proprietarioRepository = new ProprietarioRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField telefone1Field = NumberTextField.getNumberTextField();
    private JTextField telefone2Field = NumberTextField.getNumberTextField();
    private JTextField telefone3Field = NumberTextField.getNumberTextField();

    public ProprietarioWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Telefone 1");
        tableModel.addColumn("Telefone 2");
        tableModel.addColumn("Telefone 3");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Nome:");
        nameField = new JTextField();
        JLabel telefone1Label = new JLabel("Telefone 1:");
        JLabel telefone2Label = new JLabel("Telefone 2:");
        JLabel telefone3Label = new JLabel("Telefone 3:");

        JPanel namePanel = new JPanel(new GridLayout(1, 2, -10, 10));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        formPanel.add(namePanel);

        JPanel telefone1Panel = new JPanel(new GridLayout(1, 2, -10, 10));
        telefone1Panel.add(telefone1Label);
        telefone1Panel.add(telefone1Field);
        formPanel.add(telefone1Panel);

        JPanel telefone2Panel = new JPanel(new GridLayout(1, 2, -10, 10));
        telefone2Panel.add(telefone2Label);
        telefone2Panel.add(telefone2Field);
        formPanel.add(telefone2Panel);

        JPanel telefone3Panel = new JPanel(new GridLayout(1, 2, -10, 10));
        telefone3Panel.add(telefone3Label);
        telefone3Panel.add(telefone3Field);
        formPanel.add(telefone3Panel);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String telefone1 = telefone1Field.getText();
            String telefone2 = telefone2Field.getText();
            String telefone3 = telefone3Field.getText();

            if (name.isEmpty() || (telefone1.isEmpty() && telefone2.isEmpty() && telefone3.isEmpty())) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
            } else {
                try {
                    Proprietario proprietario = new Proprietario(
                            name,
                            telefone1.isEmpty() ? 0 : Long.parseLong(telefone1),
                            telefone2.isEmpty() ? 0 : Long.parseLong(telefone2),
                            telefone3.isEmpty() ? 0 : Long.parseLong(telefone3)
                    );
                    proprietarioRepository.persist(proprietario);
                    addRow(proprietario);
                    clearFields();
                    ProprietarioComboBox.reloadProprietarioComboBox();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INSERT);
                }
            }
        });

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    Integer id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    String name = tableModel.getValueAt(selectedRow, 1).toString();
                    Long telefone1 = Long.parseLong(tableModel.getValueAt(selectedRow, 2).toString());
                    Long telefone2 = Long.parseLong(tableModel.getValueAt(selectedRow, 3).toString());
                    Long telefone3 = Long.parseLong(tableModel.getValueAt(selectedRow, 4).toString());
                    Proprietario proprietario = new Proprietario(id, name, telefone1, telefone2, telefone3);
                    if (openUpdateDialog(proprietario)) {
                        proprietarioRepository.update(proprietario);
                        loadData();
                        ProprietarioComboBox.reloadProprietarioComboBox();
                    }
                } catch (Exception exception) {
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
                        proprietarioRepository.delete(id);
                        tableModel.removeRow(selectedRow);
                        ProprietarioComboBox.reloadProprietarioComboBox();
                    }
                } catch (SQLException ex) {
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

    private void addRow(Proprietario proprietario) {
        Vector<String> row = new Vector<>();
        row.add(proprietario.getId().toString());
        row.add(proprietario.getName());
        row.add(proprietario.getTelefone1().toString());
        row.add(proprietario.getTelefone2().toString());
        row.add(proprietario.getTelefone3().toString());
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
            java.util.List<Proprietario> proprietarios = proprietarioRepository.findAll();
            proprietarios.forEach(this::addRow);
        } catch (Exception exception) {
            exception.printStackTrace();
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    private boolean openUpdateDialog(Proprietario proprietario) {
        ProprietarioUpdate dialog = new ProprietarioUpdate((Frame) SwingUtilities.getWindowAncestor(panel), proprietario);
        dialog.setVisible(true);
        return dialog.isUpdated();
    }

    private void clearFields() {
        nameField.setText("");
        telefone1Field.setText("");
        telefone2Field.setText("");
        telefone3Field.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
