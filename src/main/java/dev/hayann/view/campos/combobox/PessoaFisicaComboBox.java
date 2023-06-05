package dev.hayann.view.campos.combobox;

import dev.hayann.model.PessoaFisica;
import dev.hayann.repository.PessoaFisicaRepository;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class PessoaFisicaComboBox {

    public static JComboBox<PessoaFisica> pessoaFisicaComboBox = new JComboBox<>();

    public static JComboBox<PessoaFisica> pessoaFisicaComboBoxUpdate = new JComboBox<>();

    public static JComboBox<PessoaFisica> pessoaFisicaProprietarioComboBox = new JComboBox<>();

    public static JComboBox<PessoaFisica> getPessoaFisicaComboBox() {
        reloadPessoaFisicaComboBox();
        return pessoaFisicaComboBox;
    }

    public static JComboBox<PessoaFisica> getPessoaFisicaComboBoxUpdate() {
        reloadPessoaFisicaComboBox();
        return pessoaFisicaComboBoxUpdate;
    }

    public static JComboBox<PessoaFisica> getPessoaFisicaProprietarioComboBox() {
        reloadPessoaFisicaComboBox();
        return pessoaFisicaProprietarioComboBox;
    }

    public static void reloadPessoaFisicaComboBox() {
        pessoaFisicaComboBox.removeAllItems();
        pessoaFisicaComboBoxUpdate.removeAllItems();
        pessoaFisicaProprietarioComboBox.removeAllItems();
        PessoaFisicaRepository pessoaFisicaRepository = new PessoaFisicaRepository();
        try {
            List<PessoaFisica> pessoaFisicas = pessoaFisicaRepository.findAll();
            PessoaFisica pessoaFisicaMock = new PessoaFisica(0, 0, 0, GenericMessages.DEFAULT_OPTION_COMBO_BOX, LocalDate.now(), 0);
            pessoaFisicaComboBox.addItem(pessoaFisicaMock);
            pessoaFisicaComboBoxUpdate.addItem(pessoaFisicaMock);
            pessoaFisicaProprietarioComboBox.addItem(pessoaFisicaMock);
            for (PessoaFisica pessoaFisica : pessoaFisicas) {
                pessoaFisicaComboBox.addItem(pessoaFisica);
                pessoaFisicaComboBoxUpdate.addItem(pessoaFisica);
                pessoaFisicaProprietarioComboBox.addItem(pessoaFisica);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
