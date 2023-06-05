package dev.hayann.view.telas.produto;

import dev.hayann.model.Produto;
import dev.hayann.repository.ProdutoRepository;
import dev.hayann.view.campos.combobox.ProdutoComboBox;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.dialog.WarningDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class ProdutoWindow {

    private ProdutoRepository produtoRepository = new ProdutoRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField descriptionField;

    public ProdutoWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Descrição");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel descriptionLabel = new JLabel("Descrição:");
        descriptionField = new JTextField();

        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            String description = descriptionField.getText();

            if (description.isEmpty()) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
            } else {
                try {
                    Produto produto = new Produto(description);
                    produtoRepository.persist(produto);
                    addRow(produto);
                    clearFields();
                    ProdutoComboBox.reloadProdutoComboBox();
                } catch (Exception exception) {
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
                    String description = tableModel.getValueAt(selectedRow, 1).toString();
                    Produto produto = new Produto(id, description);
                    if (openUpdateDialog(produto)) {
                        produtoRepository.update(produto);
                        loadData();
                        ProdutoComboBox.reloadProdutoComboBox();
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
                        produtoRepository.delete(id);
                        tableModel.removeRow(selectedRow);
                        ProdutoComboBox.reloadProdutoComboBox();
                    }
                } catch (SQLException ex) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_DELETE);
                }
            }
        });

        JButton recarregarButton = new JButton("Recarregar");
        recarregarButton.addActionListener(e -> loadData());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(recarregarButton);

        formPanel.add(buttonPanel);

        panel.add(formPanel, BorderLayout.SOUTH);
        loadData();
    }

    private void addRow(Produto produto) {
        Vector<String> row = new Vector<>();
        row.add(produto.getId().toString());
        row.add(produto.getDescricao());
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
            java.util.List<Produto> produtos = produtoRepository.findAll();
            produtos.forEach(this::addRow);
        } catch (Exception exception) {
            exception.printStackTrace();
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    private boolean openUpdateDialog(Produto produto) {
        ProdutoUpdate dialog = new ProdutoUpdate((Frame) SwingUtilities.getWindowAncestor(panel), produto);
        dialog.setVisible(true);
        return dialog.isUpdated();
    }

    private void clearFields() {
        descriptionField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
