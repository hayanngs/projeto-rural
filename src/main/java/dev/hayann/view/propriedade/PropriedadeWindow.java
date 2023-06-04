package dev.hayann.view.propriedade;

import dev.hayann.model.Propriedade;
import dev.hayann.repository.PropriedadeRepository;
import dev.hayann.view.campos.NumberTextField;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.dialog.WarningDialog;
import dev.hayann.view.messages.GenericMessages;
import dev.hayann.view.propriedade.PropriedadeUpdate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class PropriedadeWindow {

    private PropriedadeRepository propriedadeRepository = new PropriedadeRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField areaField = NumberTextField.getNumberTextField();
    private JTextField distanciaField = NumberTextField.getNumberTextField();
    private JTextField valorField = NumberTextField.getNumberTextField();

    public PropriedadeWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Área");
        tableModel.addColumn("Distância do Município");
        tableModel.addColumn("Valor da Aquisição");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Nome:");
        nameField = new JTextField();
        JLabel areaLabel = new JLabel("Área:");
        JLabel distanciaLabel = new JLabel("Distância do Município:");
        JLabel valorLabel = new JLabel("Valor da Aquisição:");

        JPanel namePanel = new JPanel(new GridLayout(1, 2, -10, 10));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        formPanel.add(namePanel);

        JPanel areaPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        areaPanel.add(areaLabel);
        areaPanel.add(areaField);
        formPanel.add(areaPanel);

        JPanel distanciaPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        distanciaPanel.add(distanciaLabel);
        distanciaPanel.add(distanciaField);
        formPanel.add(distanciaPanel);

        JPanel valorPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        valorPanel.add(valorLabel);
        valorPanel.add(valorField);
        formPanel.add(valorPanel);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String area = areaField.getText();
            String distancia = distanciaField.getText();
            String valor = valorField.getText();

            if (name.isEmpty() || area.isEmpty() || distancia.isEmpty() || valor.isEmpty()) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
            } else {
                try {
                    Propriedade propriedade = new Propriedade(name, Double.parseDouble(area), Double.parseDouble(distancia), Double.parseDouble(valor));
                    propriedadeRepository.persist(propriedade);
                    addRow(propriedade);
                    clearFields();
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
                    String name = tableModel.getValueAt(selectedRow, 1).toString();
                    Double area = Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString());
                    Double distancia = Double.parseDouble(tableModel.getValueAt(selectedRow, 3).toString());
                    Double valor = Double.parseDouble(tableModel.getValueAt(selectedRow, 4).toString());
                    Propriedade propriedade = new Propriedade(id, name, area, distancia, valor);
                    if (openUpdateDialog(propriedade)) {
                        propriedadeRepository.update(propriedade);
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
                        propriedadeRepository.delete(id);
                        tableModel.removeRow(selectedRow);
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

    private void addRow(Propriedade propriedade) {
        Vector<String> row = new Vector<>();
        row.add(propriedade.getId().toString());
        row.add(propriedade.getName());
        row.add(propriedade.getAreaPropriedade().toString());
        row.add(propriedade.getDistanciaMunicipio().toString());
        row.add(propriedade.getValorAquisicao().toString());
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
            java.util.List<Propriedade> propriedades = propriedadeRepository.findAll();
            propriedades.forEach(this::addRow);
        } catch (Exception exception) {
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    private boolean openUpdateDialog(Propriedade propriedade) {
        PropriedadeUpdate dialog = new PropriedadeUpdate((Frame) SwingUtilities.getWindowAncestor(panel), propriedade);
        dialog.setVisible(true);
        return dialog.isUpdated();
    }

    private void clearFields() {
        nameField.setText("");
        areaField.setText("");
        distanciaField.setText("");
        valorField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
