package dev.hayann.view.municipio;

import dev.hayann.model.Municipio;
import dev.hayann.repository.MunicipioRepository;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.dialog.WarningDialog;
import dev.hayann.view.messages.GenericMessages;
import dev.hayann.view.messages.MunicipioMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class MunicipioWindow {

    private MunicipioRepository municipioRepository = new MunicipioRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField ufField;

    public MunicipioWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("UF");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Nome:");
        nameField = new JTextField();
        JLabel ufLabel = new JLabel("UF:");
        ufField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(ufLabel);
        formPanel.add(ufField);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String uf = ufField.getText();

            if (name.isEmpty() || uf.isEmpty()) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
            } else {
                try {
                    Municipio municipio = new Municipio(name, uf);
                    municipioRepository.persist(municipio);
                    addRow(municipio);
                    clearFields();
                } catch (Exception exception) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), MunicipioMessages.ERROR_INSERT);
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
                    String uf = tableModel.getValueAt(selectedRow, 2).toString();
                    Municipio municipio = new Municipio(id, name, uf);
                    if (openUpdateDialog(municipio)) {
                        municipioRepository.update(municipio);
                        loadData();
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
                        municipioRepository.delete(id);
                        tableModel.removeRow(selectedRow);
                    }
                } catch (SQLException ex) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_DELETE);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        formPanel.add(buttonPanel);

        panel.add(formPanel, BorderLayout.SOUTH);
        loadData();
    }

    private void addRow(Municipio municipio) {
        Vector<String> row = new Vector<>();
        row.add(municipio.getId().toString());
        row.add(municipio.getName());
        row.add(municipio.getUf());
        tableModel.addRow(row);
    }

    private void cleanTable() {
        int totalRows = tableModel.getRowCount();
        for (int i = totalRows - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void loadData() {
        cleanTable();
        java.util.List<Municipio> municipios = municipioRepository.findAll();
        municipios.forEach(this::addRow);
    }

    private boolean openUpdateDialog(Municipio municipio) {
        MunicipioUpdate dialog = new MunicipioUpdate((Frame) SwingUtilities.getWindowAncestor(panel), municipio);
        dialog.setVisible(true);
        return dialog.isUpdated();
    }

    private void clearFields() {
        nameField.setText("");
        ufField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
