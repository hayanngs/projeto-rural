package dev.hayann.view.campos.combobox;

import dev.hayann.model.Proprietario;
import dev.hayann.repository.ProprietarioRepository;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.util.List;

public class ProprietarioComboBox {

    public static JComboBox<Proprietario> proprietarioComboBox = new JComboBox<>();

    public static JComboBox<Proprietario> proprietarioComboBoxUpdate = new JComboBox<>();

    public static JComboBox<Proprietario> getProprietarioComboBox() {
        reloadProprietarioComboBox();
        return proprietarioComboBox;
    }

    public static JComboBox<Proprietario> getProprietarioComboBoxUpdate() {
        reloadProprietarioComboBox();
        return proprietarioComboBoxUpdate;
    }

    public static void reloadProprietarioComboBox() {
        proprietarioComboBox.removeAllItems();
        proprietarioComboBoxUpdate.removeAllItems();
        ProprietarioRepository proprietarioRepository = new ProprietarioRepository();
        Proprietario proprietarioMock = new Proprietario(GenericMessages.DEFAULT_OPTION_COMBO_BOX, 0L, 0L, 0L);
        proprietarioComboBox.addItem(proprietarioMock);
        proprietarioComboBoxUpdate.addItem(proprietarioMock);
        try {
            List<Proprietario> proprietarios = proprietarioRepository.findAll();
            for (Proprietario proprietario : proprietarios) {
                proprietarioComboBox.addItem(proprietario);
                proprietarioComboBoxUpdate.addItem(proprietario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
