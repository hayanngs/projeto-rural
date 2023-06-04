package dev.hayann.view.producao;

import dev.hayann.model.Producao;
import dev.hayann.model.Produto;
import dev.hayann.model.Propriedade;
import dev.hayann.repository.ProducaoRepository;
import dev.hayann.repository.ProdutoRepository;
import dev.hayann.repository.PropriedadeRepository;
import dev.hayann.view.campos.DateTextField;
import dev.hayann.view.campos.NumberTextField;
import dev.hayann.view.campos.ProdutoComboBox;
import dev.hayann.view.campos.PropriedadeComboBox;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.dialog.WarningDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class ProducaoWindow {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ProducaoRepository producaoRepository = new ProducaoRepository();
    private PropriedadeRepository propriedadeRepository = new PropriedadeRepository();
    private ProdutoRepository produtoRepository = new ProdutoRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox propriedadeComboBox = PropriedadeComboBox.getPropriedadeComboBox();
    private JComboBox produtoComboBox = ProdutoComboBox.getProdutoComboBox();
    private JTextField dataInicioProvField = DateTextField.getDateTextField();
    private JTextField dataFimProvField = DateTextField.getDateTextField();
    private JTextField colheitaProvField = NumberTextField.getNumberTextField();
    private JTextField dataInicioRealField = DateTextField.getDateTextField();
    private JTextField dataFimRealField = DateTextField.getDateTextField();
    private JTextField colheitaRealField = NumberTextField.getNumberTextField();

    public ProducaoWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Propriedade");
        tableModel.addColumn("Produto");
        tableModel.addColumn("Data início provável");
        tableModel.addColumn("Data fim provável");
        tableModel.addColumn("Colheita provável");
        tableModel.addColumn("Data início real");
        tableModel.addColumn("Data fim real");
        tableModel.addColumn("Colheita real");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel propriedadeLabel = new JLabel("Propriedade:");
        JLabel produtoLabel = new JLabel("Produto:");
        JLabel dataInicioProvLabel = new JLabel("Data início provável:");
        JLabel dataFimProvLabel = new JLabel("Data fim provável:");
        JLabel colheitaProvLabel = new JLabel("Colheita provável:");
        JLabel dataInicioRealLabel = new JLabel("Data início real:");
        JLabel dataFimRealLabel = new JLabel("Data fim real:");
        JLabel colheitaRealLabel = new JLabel("Colheita real:");

        JPanel propriedadePanel = new JPanel(new GridLayout(1, 2, -10, 10));
        propriedadePanel.add(propriedadeLabel);
        propriedadePanel.add(propriedadeComboBox);
        formPanel.add(propriedadePanel);

        JPanel produtoPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        produtoPanel.add(produtoLabel);
        produtoPanel.add(produtoComboBox);
        formPanel.add(produtoPanel);

        JPanel dataInicioProvPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        dataInicioProvPanel.add(dataInicioProvLabel);
        dataInicioProvPanel.add(dataInicioProvField);
        formPanel.add(dataInicioProvPanel);

        JPanel dataInicioRealPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        dataInicioRealPanel.add(dataInicioRealLabel);
        dataInicioRealPanel.add(dataInicioRealField);
        formPanel.add(dataInicioRealPanel);

        JPanel dataFimProvPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        dataFimProvPanel.add(dataFimProvLabel);
        dataFimProvPanel.add(dataFimProvField);
        formPanel.add(dataFimProvPanel);

        JPanel dataFimRealPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        dataFimRealPanel.add(dataFimRealLabel);
        dataFimRealPanel.add(dataFimRealField);
        formPanel.add(dataFimRealPanel);

        JPanel colheitaProvPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        colheitaProvPanel.add(colheitaProvLabel);
        colheitaProvPanel.add(colheitaProvField);
        formPanel.add(colheitaProvPanel);

        JPanel colheitaRealPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        colheitaRealPanel.add(colheitaRealLabel);
        colheitaRealPanel.add(colheitaRealField);
        formPanel.add(colheitaRealPanel);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            String dataInicioProv = dataInicioProvField.getText();
            String dataFimProv = dataInicioProvField.getText();
            String colheitaProv = colheitaProvField.getText();
            String dataInicioReal = dataInicioProvField.getText();
            String dataFimReal = dataInicioProvField.getText();
            String colheitaReal = colheitaRealField.getText();

            if (dataInicioProv.isEmpty() || (dataFimProv.isEmpty() && colheitaProv.isEmpty())) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
            } else {
                try {
                    Producao producao = new Producao(
                            LocalDate.parse(dataInicioProv, formatter),
                            LocalDate.parse(dataFimProv, formatter),
                            Double.parseDouble(colheitaProv),
                            LocalDate.parse(dataInicioReal, formatter),
                            LocalDate.parse(dataFimReal, formatter),
                            Double.parseDouble(colheitaReal),
                            (Propriedade) propriedadeComboBox.getSelectedItem(),
                            (Produto) produtoComboBox.getSelectedItem()
                    );
                    producaoRepository.persist(producao);
                    addRow(producao);
                    clearFields();
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
                    Propriedade propriedade = propriedadeRepository.findByName(tableModel.getValueAt(selectedRow, 1).toString());
                    Produto produto = produtoRepository.findByDescricao(tableModel.getValueAt(selectedRow, 2).toString());
                    LocalDate dataInicioProv = LocalDate.parse(tableModel.getValueAt(selectedRow, 3).toString(), formatter);
                    LocalDate dataFimProv = LocalDate.parse(tableModel.getValueAt(selectedRow, 4).toString(), formatter);
                    Double colheitaProv = Double.parseDouble(tableModel.getValueAt(selectedRow, 5).toString());
                    LocalDate dataInicioReal = LocalDate.parse(tableModel.getValueAt(selectedRow, 6).toString(), formatter);
                    LocalDate dataFimReal = LocalDate.parse(tableModel.getValueAt(selectedRow, 7).toString(), formatter);
                    Double colheitaReal = Double.parseDouble(tableModel.getValueAt(selectedRow, 8).toString());
                    Producao producao = new Producao(
                            id,
                            dataInicioProv,
                            dataFimProv,
                            colheitaProv,
                            dataInicioReal,
                            dataFimReal,
                            colheitaReal,
                            propriedade,
                            produto
                    );
                    if (openUpdateDialog(producao)) {
                        producaoRepository.update(producao);
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
                        Integer id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                        producaoRepository.delete(id);
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

    private void addRow(Producao producao) {
        Vector<String> row = new Vector<>();
        row.add(producao.getId().toString());
        row.add(producao.getPropriedade().toString());
        row.add(producao.getProduto().toString());
        row.add(producao.getDataInicioColheitaProv().format(formatter));
        row.add(producao.getDataFimColheitaProv().format(formatter));
        row.add(producao.getQtdProvColhida().toString());
        row.add(producao.getDataInicioColheitaReal().format(formatter));
        row.add(producao.getDataFimColheitaReal().format(formatter));
        row.add(producao.getQtdRealColhida().toString());
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
            java.util.List<Producao> producoes = producaoRepository.findAll();
            producoes.forEach(this::addRow);
        } catch (Exception exception) {
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    private boolean openUpdateDialog(Producao producao) {
        ProducaoUpdate dialog = new ProducaoUpdate((Frame) SwingUtilities.getWindowAncestor(panel), producao);
        dialog.setVisible(true);
        return dialog.isUpdated();
    }

    private void clearFields() {
        dataInicioProvField.setText("");
        dataFimProvField.setText("");
        colheitaProvField.setText("");
        dataInicioRealField.setText("");
        dataFimRealField.setText("");
        colheitaRealField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
