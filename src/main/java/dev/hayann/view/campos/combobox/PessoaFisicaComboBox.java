package dev.hayann.view.campos.combobox;

import dev.hayann.model.PessoaFisica;
import dev.hayann.repository.PessoaFisicaRepository;

import javax.swing.*;
import java.util.List;

public class PessoaFisicaComboBox {

    public static JComboBox<PessoaFisica> pessoaFisicaComboBox = new JComboBox<>();

    public static JComboBox<PessoaFisica> pessoaFisicaComboBoxUpdate = new JComboBox<>();

    public static JComboBox<PessoaFisica> getPessoaFisicaComboBox() {
        reloadPessoaFisicaComboBox();
        return pessoaFisicaComboBox;
    }

    public static JComboBox<PessoaFisica> getPessoaFisicaComboBoxUpdate() {
        reloadPessoaFisicaComboBox();
        return pessoaFisicaComboBoxUpdate;
    }

    public static void reloadPessoaFisicaComboBox() {
        pessoaFisicaComboBox.removeAllItems();
        pessoaFisicaComboBoxUpdate.removeAllItems();
        PessoaFisicaRepository pessoaFisicaRepository = new PessoaFisicaRepository();
        try {
            List<PessoaFisica> pessoaFisicas = pessoaFisicaRepository.findAll();
            for (PessoaFisica pessoaFisica : pessoaFisicas) {
                pessoaFisicaComboBox.addItem(pessoaFisica);
                pessoaFisicaComboBoxUpdate.addItem(pessoaFisica);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
