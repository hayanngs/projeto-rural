package dev.hayann.view.telas.questao2;

import dev.hayann.dto.Questao2DTO;
import dev.hayann.repository.ProprietarioPropriedadeRepository;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class Questao2Window {

    private ProprietarioPropriedadeRepository proprietarioPropriedadeRepository = new ProprietarioPropriedadeRepository();
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    public Questao2Window() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome Pessoa Física");
        tableModel.addColumn("Razão Social");
        tableModel.addColumn("Nome Propriedade");
        tableModel.addColumn("Área Proprieade");
        tableModel.addColumn("Valor Aquisição");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
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

    private void addRow(Questao2DTO questao2DTO) {
        Vector<String> row = new Vector<>();
        row.add(questao2DTO.nomePf);
        row.add(questao2DTO.razaoSocial);
        row.add(questao2DTO.nomePropriedade);
        row.add(questao2DTO.areaPropriedade.toString());
        row.add(questao2DTO.valorAquisicao.toString());
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
            java.util.List<Questao2DTO> producoesAnuais = proprietarioPropriedadeRepository.findQuestao2();
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
