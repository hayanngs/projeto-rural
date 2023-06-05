package dev.hayann.view.campos.combobox;

import dev.hayann.model.Propriedade;
import dev.hayann.repository.PropriedadeRepository;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.util.List;

public class PropriedadeComboBox {

    public static JComboBox<Propriedade> propriedadeComboBox = new JComboBox<>();

    public static JComboBox<Propriedade> propriedadeComboBoxUpdate = new JComboBox<>();

    public static JComboBox<Propriedade> propriedadeProprietarioComboBox = new JComboBox<>();

    public static JComboBox<Propriedade> getPropriedadeComboBox() {
        reloadPropriedadeComboBox();
        return propriedadeComboBox;
    }

    public static JComboBox<Propriedade> getPropriedadeComboBoxUpdate() {
        reloadPropriedadeComboBox();
        return propriedadeComboBoxUpdate;
    }

    public static JComboBox<Propriedade> getPropriedadeProprietarioComboBox() {
        reloadPropriedadeComboBox();
        return propriedadeProprietarioComboBox;
    }

    public static void reloadPropriedadeComboBox() {
        propriedadeComboBox.removeAllItems();
        propriedadeComboBoxUpdate.removeAllItems();
        propriedadeProprietarioComboBox.removeAllItems();
        PropriedadeRepository propriedadeRepository = new PropriedadeRepository();
        Propriedade propriedadeMock = new Propriedade(GenericMessages.DEFAULT_OPTION_COMBO_BOX, 0.0, 0.0, 0.0, null);
        propriedadeComboBox.addItem(propriedadeMock);
        propriedadeComboBoxUpdate.addItem(propriedadeMock);
        propriedadeProprietarioComboBox.addItem(propriedadeMock);
        try {
            List<Propriedade> propriedades = propriedadeRepository.findAll();
            for (Propriedade propriedade : propriedades) {
                propriedadeComboBox.addItem(propriedade);
                propriedadeComboBoxUpdate.addItem(propriedade);
                propriedadeProprietarioComboBox.addItem(propriedade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
