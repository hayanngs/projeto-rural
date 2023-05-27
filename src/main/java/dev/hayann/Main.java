package dev.hayann;

import dev.hayann.database.InicializadorDoBD;
import dev.hayann.model.Municipio;
import dev.hayann.repository.MunicipioRepository;

import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws SQLException {
        InicializadorDoBD.inicializarBancoDeDados();

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
