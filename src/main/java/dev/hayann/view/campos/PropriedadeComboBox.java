package dev.hayann.view.campos;

import dev.hayann.model.Propriedade;
import dev.hayann.repository.PropriedadeRepository;

import javax.swing.*;
import java.util.List;

public class PropriedadeComboBox {

    public static JComboBox<Propriedade> propriedadeComboBox = new JComboBox<>();

    public static JComboBox<Propriedade> propriedadeComboBoxUpdate = new JComboBox<>();

    public static JComboBox<Propriedade> getPropriedadeComboBox() {
        reloadPropriedadeComboBox();
        return propriedadeComboBox;
    }

    public static JComboBox<Propriedade> getPropriedadeComboBoxUpdate() {
        reloadPropriedadeComboBox();
        return propriedadeComboBoxUpdate;
    }

    public static void reloadPropriedadeComboBox() {
        propriedadeComboBox.removeAllItems();
        propriedadeComboBoxUpdate.removeAllItems();
        PropriedadeRepository propriedadeRepository = new PropriedadeRepository();
        try {
            List<Propriedade> propriedades = propriedadeRepository.findAll();
            for (Propriedade propriedade : propriedades) {
                propriedadeComboBox.addItem(propriedade);
                propriedadeComboBoxUpdate.addItem(propriedade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
