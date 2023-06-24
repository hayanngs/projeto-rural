package dev.hayann.view.telas.producaoanual;

import dev.hayann.dto.ProducaoAnualDTO;
import dev.hayann.repository.ProducaoRepository;
import dev.hayann.repository.ProdutoRepository;
import dev.hayann.repository.PropriedadeRepository;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class ProducaoAnualWindow {

    private ProducaoRepository producaoRepository = new ProducaoRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProducaoAnualWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome Propriedade");
        tableModel.addColumn("Nome Produto");
        tableModel.addColumn("Área Proprieade");
        tableModel.addColumn("Quantidade Colhida");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton reloadButton = new JButton("Recarregar");
        reloadButton.addActionListener(e -> loadData());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(reloadButton);

        JPanel buttonContainerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        buttonContainerPanel.add(buttonPanel, gbc);

        formPanel.add(buttonContainerPanel);

        panel.add(formPanel, BorderLayout.SOUTH);
        loadData();
    }

    private void addRow(ProducaoAnualDTO producaoAnualDTO) {
        Vector<String> row = new Vector<>();
        row.add(producaoAnualDTO.nam_propriedade);
        row.add(producaoAnualDTO.desc_produto);
        row.add(producaoAnualDTO.area_propriedade.toString());
        row.add(producaoAnualDTO.qtd_real_colhida.toString());
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
            java.util.List<ProducaoAnualDTO> producoesAnuais = producaoRepository.findProducaoAnual();
            producoesAnuais.forEach(this::addRow);
        } catch (Exception exception) {
            exception.printStackTrace();
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
