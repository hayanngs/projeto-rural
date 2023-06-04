package dev.hayann.view.campos.combobox;

import dev.hayann.model.Municipio;
import dev.hayann.repository.MunicipioRepository;

import javax.swing.*;
import java.util.List;

public class MunicipioComboBox {

    public static JComboBox<Municipio> municipioComboBox = new JComboBox<>();

    public static JComboBox<Municipio> getMunicipioComboBox() {
        reloadMunicipioComboBox();
        return municipioComboBox;
    }

    public static void reloadMunicipioComboBox() {
        municipioComboBox.removeAllItems();
        MunicipioRepository municipioRepository = new MunicipioRepository();
        try {
            List<Municipio> municipios = municipioRepository.findAll();
            for (Municipio municipio : municipios) {
                municipioComboBox.addItem(municipio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
