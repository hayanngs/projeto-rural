package dev.hayann;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MunicipioWindow {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField;
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

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel nameLabel = new JLabel("Nome:");
        nameField = new JTextField();
        JLabel ufLabel = new JLabel("UF:");
        ufField = new JTextField();

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(ufLabel);
        formPanel.add(ufField);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String uf = ufField.getText();

                if (id.isEmpty() || name.isEmpty() || uf.isEmpty()) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Por favor, preencha todos os campos.");
                } else {
                    Vector<String> row = new Vector<>();
                    row.add(id);
                    row.add(name);
                    row.add(uf);
                    tableModel.addRow(row);
                    clearFields();
                }
            }
        });

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String id = idField.getText();
                    String name = nameField.getText();
                    String uf = ufField.getText();
                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(uf, selectedRow, 2);
                    clearFields();
                }
            }
        });

        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    clearFields();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        formPanel.add(buttonPanel);

        panel.add(formPanel, BorderLayout.SOUTH);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ufField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
