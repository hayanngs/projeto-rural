package dev.hayann.view.telas.proprietariopropriedade;

import dev.hayann.model.Propriedade;
import dev.hayann.model.ProprietarioPropriedade;
import dev.hayann.model.Proprietario;
import dev.hayann.repository.ProprietarioPropriedadeRepository;
import dev.hayann.view.campos.combobox.PropriedadeComboBox;
import dev.hayann.view.campos.combobox.ProprietarioComboBox;
import dev.hayann.view.campos.textfield.DateTextField;
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

public class ProprietarioPropriedadeWindow {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ProprietarioPropriedadeRepository proprietarioPropriedadeRepository = new ProprietarioPropriedadeRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox proprietarioComboBox = ProprietarioComboBox.getProprietarioPropriedadeComboBox();
    private JComboBox propriedadeComboBox = PropriedadeComboBox.getPropriedadeProprietarioComboBox();
    private JTextField dataAquisicaoField = DateTextField.getDateTextField();

    public ProprietarioPropriedadeWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Proprietário");
        tableModel.addColumn("Propriedade");
        tableModel.addColumn("Data de Aquisição");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel proprietarioLabel = new JLabel("Proprietário:");
        JLabel propriedadeLabel = new JLabel("Propriedade:");
        JLabel dataAquisicaoLabel = new JLabel("Data de Aquisição:");

        JPanel proprietarioPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        proprietarioPanel.add(proprietarioLabel);
        proprietarioPanel.add(proprietarioComboBox);
        formPanel.add(proprietarioPanel);

        JPanel propriedadePanel = new JPanel(new GridLayout(1, 2, -10, 10));
        propriedadePanel.add(propriedadeLabel);
        propriedadePanel.add(propriedadeComboBox);
        formPanel.add(propriedadePanel);

        JPanel dataAquisicaoPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        dataAquisicaoPanel.add(dataAquisicaoLabel);
        dataAquisicaoPanel.add(dataAquisicaoField);
        formPanel.add(dataAquisicaoPanel);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            if (proprietarioComboBox.getSelectedItem() == null || proprietarioComboBox.getSelectedIndex() == 0
                    || propriedadeComboBox.getSelectedItem() == null || propriedadeComboBox.getSelectedIndex() == 0) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.PROPRIETARIO_EMPTY_COMBO_BOX_ERROR);
            } else {
                String idProprietario = ((Proprietario) Objects.requireNonNull(proprietarioComboBox.getSelectedItem())).getId().toString();
                String idPropriedade = ((Propriedade) Objects.requireNonNull(propriedadeComboBox.getSelectedItem())).getId().toString();
                String dataAquisicao = dataAquisicaoField.getText();

                if (idProprietario.isEmpty() || idPropriedade.isEmpty() || dataAquisicao.isEmpty()) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
                } else {
                    try {
                        ProprietarioPropriedade proprietarioPropriedade = new ProprietarioPropriedade(
                                Integer.parseInt(idProprietario),
                                Integer.parseInt(idPropriedade),
                                LocalDate.parse(dataAquisicao, formatter)
                        );
                        proprietarioPropriedadeRepository.persist(proprietarioPropriedade);
                        addRow(proprietarioPropriedade);
                        clearFields();
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
                    String idPropriedade = tableModel.getValueAt(selectedRow, 1).toString();
                    String dataAquisicao = tableModel.getValueAt(selectedRow, 2).toString();
                    ProprietarioPropriedade proprietarioPropriedade = new ProprietarioPropriedade(
                            Integer.parseInt(idProprietario),
                            Integer.parseInt(idPropriedade),
                            LocalDate.parse(dataAquisicao, formatter)
                    );
                    if (openUpdateDialog(proprietarioPropriedade)) {
                        proprietarioPropriedadeRepository.update(proprietarioPropriedade);
                        loadData();
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
                        Integer idProprietario = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                        Integer idPropriedade = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
                        proprietarioPropriedadeRepository.delete(idProprietario, idPropriedade);
                        tableModel.removeRow(selectedRow);
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

    private void addRow(ProprietarioPropriedade proprietarioPropriedade) {
        Vector<String> row = new Vector<>();
        row.add(proprietarioPropriedade.getIdProprietario().toString());
        row.add(proprietarioPropriedade.getIdPropriedade().toString());
        row.add(proprietarioPropriedade.getDateAcquisition().format(formatter));
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
            java.util.List<ProprietarioPropriedade> producoes = proprietarioPropriedadeRepository.findAll();
            producoes.forEach(this::addRow);
        } catch (Exception exception) {
            exception.printStackTrace();
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    private boolean openUpdateDialog(ProprietarioPropriedade proprietarioPropriedade) {
        ProprietarioPropriedadeUpdate dialog = new ProprietarioPropriedadeUpdate((Frame) SwingUtilities.getWindowAncestor(panel), proprietarioPropriedade);
        dialog.setVisible(true);
        return dialog.isUpdated();
    }

    private void clearFields() {
        dataAquisicaoField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
