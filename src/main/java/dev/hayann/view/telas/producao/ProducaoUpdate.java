package dev.hayann.view.telas.producao;

import dev.hayann.model.Producao;
import dev.hayann.model.Produto;
import dev.hayann.model.Propriedade;
import dev.hayann.view.campos.textfield.DateTextField;
import dev.hayann.view.campos.textfield.NumberTextField;
import dev.hayann.view.campos.combobox.ProdutoComboBox;
import dev.hayann.view.campos.combobox.PropriedadeComboBox;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProducaoUpdate extends JDialog {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JTextField txtId;
    private JComboBox propriedadeComboBoxUpdate = PropriedadeComboBox.getPropriedadeComboBoxUpdate();
    private JComboBox produtoComboBoxUpdate = ProdutoComboBox.getProdutoComboBoxUpdate();
    private JTextField dataInicioProvField = DateTextField.getDateTextField();
    private JTextField dataFimProvField = DateTextField.getDateTextField();
    private JTextField colheitaProvField = NumberTextField.getNumberTextField();
    private JTextField dataInicioRealField = DateTextField.getDateTextField();
    private JTextField dataFimRealField = DateTextField.getDateTextField();
    private JTextField colheitaRealField = NumberTextField.getNumberTextField();

    boolean salvo = false;

    public ProducaoUpdate(Frame parent, Producao producao) {
        super(parent, "Atualizar Cadastro", true);
        setLayout(new BorderLayout());
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel("ID:");
        JLabel propriedadeLabel = new JLabel("Propriedade:");
        JLabel produtoLabel = new JLabel("Produto:");
        JLabel dataInicioProvLabel = new JLabel("Data início provável:");
        JLabel dataFimProvLabel = new JLabel("Data fim provável:");
        JLabel colheitaProvLabel = new JLabel("Colheita provável:");
        JLabel dataInicioRealLabel = new JLabel("Data início real:");
        JLabel dataFimRealLabel = new JLabel("Data fim real:");
        JLabel colheitaRealLabel = new JLabel("Colheita real:");

        txtId = new JTextField(producao.getId().toString());
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        txtId.setBorder(BorderFactory.createEtchedBorder());

        propriedadeComboBoxUpdate.setBorder(BorderFactory.createEtchedBorder());

        produtoComboBoxUpdate.setBorder(BorderFactory.createEtchedBorder());

        dataInicioProvField.setText(producao.getDataInicioColheitaProv().format(formatter));
        dataInicioProvField.setBackground(Color.WHITE);
        dataInicioProvField.setBorder(BorderFactory.createEtchedBorder());

        dataFimProvField.setText(producao.getDataFimColheitaProv().format(formatter));
        dataFimProvField.setBackground(Color.WHITE);
        dataFimProvField.setBorder(BorderFactory.createEtchedBorder());

        colheitaProvField.setText(producao.getQtdProvColhida().toString());
        colheitaProvField.setBackground(Color.WHITE);
        colheitaProvField.setBorder(BorderFactory.createEtchedBorder());

        dataInicioRealField.setText(producao.getDataInicioColheitaReal().format(formatter));
        dataInicioRealField.setBackground(Color.WHITE);
        dataInicioRealField.setBorder(BorderFactory.createEtchedBorder());

        dataFimRealField.setText(producao.getDataFimColheitaReal().format(formatter));
        dataFimRealField.setBackground(Color.WHITE);
        dataFimRealField.setBorder(BorderFactory.createEtchedBorder());

        colheitaRealField.setText(producao.getQtdRealColhida().toString());
        colheitaRealField.setBackground(Color.WHITE);
        colheitaRealField.setBorder(BorderFactory.createEtchedBorder());

        panel.add(lblId);
        panel.add(txtId);
        panel.add(propriedadeLabel);
        panel.add(propriedadeComboBoxUpdate);
        panel.add(produtoLabel);
        panel.add(produtoComboBoxUpdate);
        panel.add(dataInicioProvLabel);
        panel.add(dataInicioProvField);
        panel.add(dataFimProvLabel);
        panel.add(dataFimProvField);
        panel.add(colheitaProvLabel);
        panel.add(colheitaProvField);
        panel.add(dataInicioRealLabel);
        panel.add(dataInicioRealField);
        panel.add(dataFimRealLabel);
        panel.add(dataFimRealField);
        panel.add(colheitaRealLabel);
        panel.add(colheitaRealField);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            if (produtoComboBoxUpdate.getSelectedItem() == null || produtoComboBoxUpdate.getSelectedIndex() == 0 ||
                    propriedadeComboBoxUpdate.getSelectedItem() == null || propriedadeComboBoxUpdate.getSelectedIndex() == 0) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(parent), GenericMessages.PRODUCAO_EMPTY_COMBO_BOX_ERROR);
            } else {
                try {
                    producao.setPropriedade((Propriedade) propriedadeComboBoxUpdate.getSelectedItem());
                    producao.setProduto((Produto) produtoComboBoxUpdate.getSelectedItem());
                    producao.setDataInicioColheitaProv(LocalDate.parse(dataInicioProvField.getText(), formatter));
                    producao.setDataFimColheitaProv(LocalDate.parse(dataFimProvField.getText(), formatter));
                    producao.setQtdProvColhida(Double.parseDouble(colheitaProvField.getText()));
                    producao.setDataInicioColheitaReal(LocalDate.parse(dataInicioRealField.getText(), formatter));
                    producao.setDataFimColheitaReal(LocalDate.parse(dataFimRealField.getText(), formatter));
                    producao.setQtdRealColhida(Double.parseDouble(colheitaRealField.getText()));
                    salvo = true;
                    dispose();
                } catch (Exception exception) {
                    new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(parent), GenericMessages.PRODUCAO_INVALID_DATA_ERROR);
                }
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            salvo = false;
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean isUpdated() {
        return salvo;
    }
}
