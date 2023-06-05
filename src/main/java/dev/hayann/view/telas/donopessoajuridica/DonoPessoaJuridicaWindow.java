package dev.hayann.view.telas.donopessoajuridica;

import dev.hayann.model.DonoPessoaJuridica;
import dev.hayann.model.PessoaFisica;
import dev.hayann.model.PessoaJuridica;
import dev.hayann.repository.DonoPessoaJuridicaRepository;
import dev.hayann.repository.ProdutoRepository;
import dev.hayann.view.campos.combobox.PessoaFisicaComboBox;
import dev.hayann.view.campos.combobox.PessoaJuridicaComboBox;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.dialog.WarningDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Vector;

public class DonoPessoaJuridicaWindow {

    private DonoPessoaJuridicaRepository donoPessoaJuridicaRepository = new DonoPessoaJuridicaRepository();

    private JPanel panel;

    private JTable table;

    private DefaultTableModel tableModel;

    private JComboBox pessoaFisicaComboBox = PessoaFisicaComboBox.getPessoaFisicaProprietarioComboBox();

    private JComboBox pessoaJuridicaComboBox = PessoaJuridicaComboBox.getPessoaJuridicaProprietarioComboBox();


    public DonoPessoaJuridicaWindow() {
        panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Pessoa Física");
        tableModel.addColumn("Pessoa Jurídica");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel pessoaFisicaLabel = new JLabel("Pessoa Física:");
        JLabel pessoaJuridicaLabel = new JLabel("Pessoa Jurídica:");

        JPanel pessoaFisicaPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        pessoaFisicaPanel.add(pessoaFisicaLabel);
        pessoaFisicaPanel.add(pessoaFisicaComboBox);
        formPanel.add(pessoaFisicaPanel);

        JPanel pessoaJuridicaPanel = new JPanel(new GridLayout(1, 2, -10, 10));
        pessoaJuridicaPanel.add(pessoaJuridicaLabel);
        pessoaJuridicaPanel.add(pessoaJuridicaComboBox);
        formPanel.add(pessoaJuridicaPanel);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> {
            if (pessoaFisicaComboBox.getSelectedItem() == null || pessoaFisicaComboBox.getSelectedIndex() == 0
                    || pessoaJuridicaComboBox.getSelectedItem() == null || pessoaJuridicaComboBox.getSelectedIndex() == 0) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.PROPRIETARIO_EMPTY_COMBO_BOX_ERROR);
            } else {
                String pessoaFisicaId = ((PessoaFisica) Objects.requireNonNull(pessoaFisicaComboBox.getSelectedItem())).getIdProprietarioPessoaFisica().toString();
                String pessoaJuridicaId = ((PessoaJuridica) Objects.requireNonNull(pessoaJuridicaComboBox.getSelectedItem())).getId().toString();

                if (pessoaFisicaId.isEmpty() || pessoaJuridicaId.isEmpty()) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INCOMPLETE_FIELD);
                } else {
                    try {
                        DonoPessoaJuridica donoPessoaJuridica = new DonoPessoaJuridica(
                                Integer.parseInt(pessoaFisicaId),
                                Integer.parseInt(pessoaJuridicaId)
                        );
                        donoPessoaJuridicaRepository.persist(donoPessoaJuridica);
                        addRow(donoPessoaJuridica);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_INSERT);
                    }
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
                        Integer pessoaFisicaId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                        Integer pessoaJuridicaId = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
                        donoPessoaJuridicaRepository.delete(pessoaFisicaId, pessoaJuridicaId);
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

    private void addRow(DonoPessoaJuridica pessoaJuridica) {
        Vector<String> row = new Vector<>();
        row.add(pessoaJuridica.getIdProprietarioPessoaJuridica().toString());
        row.add(pessoaJuridica.getIdProprietarioPessoaFisica().toString());
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
            java.util.List<DonoPessoaJuridica> producoes = donoPessoaJuridicaRepository.findAll();
            producoes.forEach(this::addRow);
        } catch (Exception exception) {
            exception.printStackTrace();
            new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(panel), GenericMessages.ERROR_SELECT);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
