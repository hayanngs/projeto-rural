package dev.hayann.view.telas.propriedade;

import dev.hayann.model.Municipio;
import dev.hayann.model.Propriedade;
import dev.hayann.repository.MunicipioRepository;
import dev.hayann.view.campos.combobox.MunicipioComboBox;
import dev.hayann.view.campos.textfield.NumberTextField;
import dev.hayann.view.dialog.ErrorDialog;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.awt.*;

public class PropriedadeUpdate extends JDialog{

    private MunicipioRepository municipioRepository = new MunicipioRepository();

    private JTextField txtId;

    private JTextField txtName;

    private JTextField txtArea = NumberTextField.getNumberTextField();

    private JTextField txtDistancia = NumberTextField.getNumberTextField();

    private JTextField txtValor = NumberTextField.getNumberTextField();

    private JComboBox municipioComboBoxUpdate = MunicipioComboBox.getMunicipioComboBoxUpdate();

    boolean salvo = false;

    public PropriedadeUpdate(Frame parent, Propriedade propriedade) {
        super(parent, "Atualizar Cadastro", true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(propriedade.getId().toString());
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        txtId.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblName = new JLabel("Nome:");
        txtName = new JTextField(propriedade.getName());
        txtName.setBackground(Color.WHITE);
        txtName.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblArea = new JLabel("Área da propriedade:");
        txtArea.setText(propriedade.getAreaPropriedade().toString());
        txtArea.setBackground(Color.WHITE);
        txtArea.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblDistancia = new JLabel("Distância do Município:");
        txtDistancia.setText(propriedade.getDistanciaMunicipio().toString());
        txtDistancia.setBackground(Color.WHITE);
        txtDistancia.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblValor = new JLabel("Valor da Aquisição:");
        txtValor.setText(propriedade.getValorAquisicao().toString());
        txtValor.setBackground(Color.WHITE);
        txtValor.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblMunicipio = new JLabel("Município:");
        municipioComboBoxUpdate.setBackground(Color.WHITE);
        municipioComboBoxUpdate.setBorder(BorderFactory.createEtchedBorder());

        panel.add(lblId);
        panel.add(txtId);
        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblArea);
        panel.add(txtArea);
        panel.add(lblDistancia);
        panel.add(txtDistancia);
        panel.add(lblValor);
        panel.add(txtValor);
        panel.add(lblMunicipio);
        panel.add(municipioComboBoxUpdate);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            if (municipioComboBoxUpdate.getSelectedItem() == null || municipioComboBoxUpdate.getSelectedIndex() == 0) {
                new ErrorDialog((JFrame) SwingUtilities.getWindowAncestor(parent), GenericMessages.MUNICIPIO_EMPTY_COMBO_BOX_ERROR);
            } else {
                propriedade.setName(txtName.getText());
                propriedade.setAreaPropriedade(Double.parseDouble(txtArea.getText()));
                propriedade.setDistanciaMunicipio(Double.parseDouble(txtDistancia.getText()));
                propriedade.setValorAquisicao(Double.parseDouble(txtValor.getText()));
                propriedade.setMunicipio((Municipio) municipioComboBoxUpdate.getSelectedItem());
                salvo = true;
                dispose();
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
