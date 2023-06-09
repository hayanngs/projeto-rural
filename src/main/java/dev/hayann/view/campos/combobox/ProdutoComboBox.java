package dev.hayann.view.campos.combobox;

import dev.hayann.model.Produto;
import dev.hayann.repository.ProdutoRepository;
import dev.hayann.view.messages.GenericMessages;

import javax.swing.*;
import java.util.List;

public class ProdutoComboBox {

    public static JComboBox<Produto> produtoComboBox = new JComboBox<>();

    public static JComboBox<Produto> produtoComboBoxUpdate = new JComboBox<>();

    public static JComboBox<Produto> getProdutoComboBox() {
        reloadProdutoComboBox();
        return produtoComboBox;
    }

    public static JComboBox<Produto> getProdutoComboBoxUpdate() {
        reloadProdutoComboBox();
        return produtoComboBoxUpdate;
    }

    public static void reloadProdutoComboBox() {
        produtoComboBox.removeAllItems();
        produtoComboBoxUpdate.removeAllItems();
        ProdutoRepository produtoRepository = new ProdutoRepository();
        Produto produtoMock = new Produto(GenericMessages.DEFAULT_OPTION_COMBO_BOX);
        produtoComboBox.addItem(produtoMock);
        produtoComboBoxUpdate.addItem(produtoMock);
        try {
            List<Produto> produtos = produtoRepository.findAll();
            for (Produto produto : produtos) {
                produtoComboBox.addItem(produto);
                produtoComboBoxUpdate.addItem(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
