package dev.hayann.view.campos.combobox;

import dev.hayann.model.Municipio;
import dev.hayann.repository.MunicipioRepository;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.util.List;

public class MunicipioComboBox {

    public static JComboBox<Municipio> municipioComboBox = new JComboBox<>();

    public static JComboBox<Municipio> municipioComboBoxUpdate = new JComboBox<>();

    public static JComboBox<Municipio> getMunicipioComboBox() {
        reloadMunicipioComboBox();
        return municipioComboBox;
    }

    public static JComboBox<Municipio> getMunicipioComboBoxUpdate() {
        reloadMunicipioComboBox();
        return municipioComboBoxUpdate;
    }

    public static void reloadMunicipioComboBox() {
        municipioComboBox.removeAllItems();
        municipioComboBoxUpdate.removeAllItems();
        MunicipioRepository municipioRepository = new MunicipioRepository();
        Municipio municipioMock = new Municipio(GenericMessages.DEFAULT_OPTION_COMBO_BOX, "");
        municipioComboBox.addItem(municipioMock);
        municipioComboBoxUpdate.addItem(municipioMock);
        try {
            List<Municipio> municipios = municipioRepository.findAll();
            for (Municipio municipio : municipios) {
                municipioComboBox.addItem(municipio);
                municipioComboBoxUpdate.addItem(municipio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
