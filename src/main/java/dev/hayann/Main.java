package dev.hayann;

import dev.hayann.database.InicializadorDoBD;
import dev.hayann.model.Municipio;
import dev.hayann.model.Produto;
import dev.hayann.model.Propriedade;
import dev.hayann.repository.MunicipioRepository;
import dev.hayann.repository.ProdutoRepository;
import dev.hayann.repository.PropriedadeRepository;

import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws SQLException {
        InicializadorDoBD.inicializarBancoDeDados();

		testePropriedade();
    }

    public static void testePropriedade() {
        PropriedadeRepository propriedadeRepository = new PropriedadeRepository();
        List<Propriedade> produtos = propriedadeRepository.findAll();
        System.out.println(produtos);

        Propriedade propriedade = propriedadeRepository.findById(1);
        System.out.println("Listando por id: " + propriedade);

        propriedadeRepository.persist(new Propriedade("Fazenda do fazendeiro", 100.00, 300.00, 542.31));
        produtos = propriedadeRepository.findAll();
        System.out.println(produtos);

        propriedadeRepository.update(new Propriedade(1, "Feijoada", 0.00, 0.00, 0.00));
        produtos = propriedadeRepository.findAll();
        System.out.println(produtos);
    }

    public static void testeProduto() {
        ProdutoRepository produtoRepository = new ProdutoRepository();
        List<Produto> produtos = produtoRepository.findAll();
        System.out.println(produtos);

        Produto produto = produtoRepository.findById(1);
        System.out.println("Listando por id: " + produto);

        produtoRepository.persist(new Produto("Azeitona"));
        produtos = produtoRepository.findAll();
        System.out.println(produtos);

        produtoRepository.update(new Produto(1, "Feijoada"));
        produtos = produtoRepository.findAll();
        System.out.println(produtos);
    }

    public void testeMunicipio() {
        MunicipioRepository municipioRepository = new MunicipioRepository();
        List<Municipio> municipios = municipioRepository.findAll();
        System.out.println(municipios);

        Municipio municipio = municipioRepository.findById(1);
        System.out.println("Listando por id: " + municipio);

        municipioRepository.persist(new Municipio("aaaaaaa", "GO"));
        municipios = municipioRepository.findAll();
        System.out.println(municipios);

        municipioRepository.update(new Municipio(5, "Mais mais", "GO"));
        municipios = municipioRepository.findAll();
        System.out.println(municipios);
    }
}
