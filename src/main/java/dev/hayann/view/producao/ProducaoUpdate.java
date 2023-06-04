package dev.hayann.view.producao;

import dev.hayann.model.Producao;
import dev.hayann.model.Produto;
import dev.hayann.model.Propriedade;
import dev.hayann.view.campos.DateTextField;
import dev.hayann.view.campos.NumberTextField;
import dev.hayann.view.campos.ProdutoComboBox;
import dev.hayann.view.campos.PropriedadeComboBox;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProducaoUpdate extends JDialog {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterFromTable = DateTimeFormatter.ofPattern("yyyy-dd-MM");
    private JTextField txtId;
    private JComboBox propriedadeComboBox = PropriedadeComboBox.getPropriedadeComboBoxUpdate();
    private JComboBox produtoComboBox = ProdutoComboBox.getProdutoComboBoxUpdate();
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

        propriedadeComboBox.setBorder(BorderFactory.createEtchedBorder());

        produtoComboBox.setBorder(BorderFactory.createEtchedBorder());

        dataInicioProvField.setText(producao.getDataInicioColheitaProv().toString());
        dataInicioProvField.setBackground(Color.WHITE);
        dataInicioProvField.setBorder(BorderFactory.createEtchedBorder());

        dataFimProvField.setText(producao.getDataFimColheitaProv().toString());
        dataFimProvField.setBackground(Color.WHITE);
        dataFimProvField.setBorder(BorderFactory.createEtchedBorder());

        colheitaProvField.setText(producao.getQtdProvColhida().toString());
        colheitaProvField.setBackground(Color.WHITE);
        colheitaProvField.setBorder(BorderFactory.createEtchedBorder());

        dataInicioRealField.setText(producao.getDataInicioColheitaReal().format(formatter));
        dataInicioRealField.setBackground(Color.WHITE);
        dataInicioRealField.setBorder(BorderFactory.createEtchedBorder());

        dataFimRealField.setText(producao.getDataFimColheitaReal().toString());
        dataFimRealField.setBackground(Color.WHITE);
        dataFimRealField.setBorder(BorderFactory.createEtchedBorder());

        colheitaRealField.setText(producao.getQtdRealColhida().toString());
        colheitaRealField.setBackground(Color.WHITE);
        colheitaRealField.setBorder(BorderFactory.createEtchedBorder());

        panel.add(lblId);
        panel.add(txtId);
        panel.add(propriedadeLabel);
        panel.add(propriedadeComboBox);
        panel.add(produtoLabel);
        panel.add(produtoComboBox);
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
            producao.setPropriedade((Propriedade) propriedadeComboBox.getSelectedItem());
            producao.setProduto((Produto) produtoComboBox.getSelectedItem());
            producao.setDataInicioColheitaProv(LocalDate.parse(dataInicioProvField.getText()));
            producao.setDataFimColheitaProv(LocalDate.parse(dataFimProvField.getText()));
            producao.setQtdProvColhida(Double.parseDouble(colheitaProvField.getText()));
            producao.setDataInicioColheitaReal(LocalDate.parse(dataInicioRealField.getText()));
            producao.setDataFimColheitaReal(LocalDate.parse(dataFimRealField.getText()));
            producao.setQtdRealColhida(Double.parseDouble(colheitaRealField.getText()));
            salvo = true;
            dispose();
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
