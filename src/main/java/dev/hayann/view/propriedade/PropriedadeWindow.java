package dev.hayann.view.propriedade;

import dev.hayann.model.Municipio;
import dev.hayann.model.Propriedade;
import dev.hayann.repository.MunicipioRepository;
import dev.hayann.repository.PropriedadeRepository;
import dev.hayann.view.campos.combobox.MunicipioComboBox;
import dev.hayann.view.campos.combobox.PropriedadeComboBox;
import dev.hayann.view.campos.textfield.NumberTextField;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.dialog.WarningDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class PropriedadeWindow {

    private PropriedadeRepository propriedadeRepository = new PropriedadeRepository();

    private MunicipioRepository municipioRepository = new MunicipioRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField = new JTextField();;
    private JTextField areaField = NumberTextField.getNumberTextField();
    private JTextField distanciaField = NumberTextField.getNumberTextField();
    private JTextField valorField = NumberTextField.getNumberTextField();
    public JComboBox<Municipio> municipioComboBox = MunicipioComboBox.getMunicipioComboBox();

    public PropriedadeWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Município");
        tableModel.addColumn("Área");
        tableModel.addColumn("Distância do Município");
        tableModel.addColumn("Valor da Aquisição");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel namePanel = new JPanel(new GridLayout(1, 2, -10, 10));
        JLabel nameLabel = new JLabel("Nome:");
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        formPanel.add(namePanel);

        JPanel municipioPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        JLabel municipioLabel = new JLabel("Município:");
        municipioPanel.add(municipioLabel);
        municipioPanel.add(municipioComboBox);
        formPanel.add(municipioPanel);

        JPanel areaPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        JLabel areaLabel = new JLabel("Área:");
        areaPanel.add(areaLabel);
        areaPanel.add(areaField);
        formPanel.add(areaPanel);

        JPanel distanciaPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        JLabel distanciaLabel = new JLabel("Distância do Município:");
        distanciaPanel.add(distanciaLabel);
        distanciaPanel.add(distanciaField);
        formPanel.add(distanciaPanel);

        JPanel valorPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        JLabel valorLabel = new JLabel("Valor da Aquisição:");
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
                    Propriedade propriedade = new Propriedade(name, Double.parseDouble(area), Double.parseDouble(distancia), Double.parseDouble(valor), (Municipio) municipioComboBox.getSelectedItem());
                    propriedadeRepository.persist(propriedade);
                    addRow(propriedade);
                    clearFields();
                    PropriedadeComboBox.reloadPropriedadeComboBox();
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
                    Municipio municipio = municipioRepository.findByName(tableModel.getValueAt(selectedRow, 1).toString());
                    Double area = Double.parseDouble(tableModel.getValueAt(selectedRow, 3).toString());
                    Double distancia = Double.parseDouble(tableModel.getValueAt(selectedRow, 4).toString());
                    Double valor = Double.parseDouble(tableModel.getValueAt(selectedRow, 5).toString());
                    Propriedade propriedade = new Propriedade(id, name, area, distancia, valor);
                    if (openUpdateDialog(propriedade)) {
                        propriedadeRepository.update(propriedade);
                        loadData();
                        PropriedadeComboBox.reloadPropriedadeComboBox();
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
                        propriedadeRepository.delete(id);
                        tableModel.removeRow(selectedRow);
                        PropriedadeComboBox.reloadPropriedadeComboBox();
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

    private void addRow(Propriedade propriedade) {
        Vector<String> row = new Vector<>();
        row.add(propriedade.getId().toString());
        row.add(propriedade.getName());
        row.add(propriedade.getMunicipio().getName());
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
            exception.printStackTrace();
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
