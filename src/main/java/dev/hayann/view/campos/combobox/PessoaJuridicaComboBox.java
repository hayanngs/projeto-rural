package dev.hayann.view.campos.combobox;

import dev.hayann.model.PessoaJuridica;
import dev.hayann.repository.PessoaJuridicaRepository;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class PessoaJuridicaComboBox {
    
    public static JComboBox<PessoaJuridica> pessoaJuridicaProprietarioComboBox = new JComboBox<>();

    public static JComboBox<PessoaJuridica> getPessoaJuridicaProprietarioComboBox() {
        reloadPessoaJuridicaComboBox();
        return pessoaJuridicaProprietarioComboBox;
    }

    public static void reloadPessoaJuridicaComboBox() {
        pessoaJuridicaProprietarioComboBox.removeAllItems();
        PessoaJuridicaRepository pessoaJuridicaRepository = new PessoaJuridicaRepository();
        try {
            List<PessoaJuridica> pessoaJuridicas = pessoaJuridicaRepository.findAll();
            PessoaJuridica pessoaJuridicaMock = new PessoaJuridica(0, 0L, GenericMessages.DEFAULT_OPTION_COMBO_BOX, LocalDate.now());
            pessoaJuridicaProprietarioComboBox.addItem(pessoaJuridicaMock);
            for (PessoaJuridica pessoaJuridica : pessoaJuridicas) {
                pessoaJuridicaProprietarioComboBox.addItem(pessoaJuridica);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
