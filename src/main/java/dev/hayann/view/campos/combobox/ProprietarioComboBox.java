package dev.hayann.view.campos.combobox;

import dev.hayann.model.Proprietario;
import dev.hayann.repository.ProprietarioRepository;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.util.List;

public class ProprietarioComboBox {

    public static JComboBox<Proprietario> proprietarioComboBox = new JComboBox<>();

    public static JComboBox<Proprietario> proprietarioComboBoxPessoaJuridica = new JComboBox<>();

    public static JComboBox<Proprietario> proprietarioPropriedadeComboBox = new JComboBox<>();

    public static JComboBox<Proprietario> getProprietarioComboBox() {
        reloadProprietarioComboBox();
        return proprietarioComboBox;
    }

    public static JComboBox<Proprietario> getProprietarioComboBoxPessoaJuridica() {
        reloadProprietarioComboBox();
        return proprietarioComboBoxPessoaJuridica;
    }

    public static JComboBox<Proprietario> getProprietarioPropriedadeComboBox() {
        reloadProprietarioComboBox();
        return proprietarioPropriedadeComboBox;
    }

    public static void reloadProprietarioComboBox() {
        proprietarioComboBox.removeAllItems();
        proprietarioPropriedadeComboBox.removeAllItems();
        proprietarioComboBoxPessoaJuridica.removeAllItems();
        ProprietarioRepository proprietarioRepository = new ProprietarioRepository();
        Proprietario proprietarioMock = new Proprietario(GenericMessages.DEFAULT_OPTION_COMBO_BOX, 0L, 0L, 0L);
        proprietarioComboBox.addItem(proprietarioMock);
        proprietarioPropriedadeComboBox.addItem(proprietarioMock);
        proprietarioComboBoxPessoaJuridica.addItem(proprietarioMock);
        try {
            List<Proprietario> proprietarios = proprietarioRepository.findAll();
            for (Proprietario proprietario : proprietarios) {
                proprietarioComboBox.addItem(proprietario);
                proprietarioPropriedadeComboBox.addItem(proprietario);
                proprietarioComboBoxPessoaJuridica.addItem(proprietario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
