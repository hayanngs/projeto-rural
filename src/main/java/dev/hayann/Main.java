package dev.hayann;

import dev.hayann.model.Municipio;
import dev.hayann.repository.MunicipioRepository;

import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws SQLException {
		MunicipioRepository municipioRepository = new MunicipioRepository();
		List<Municipio> municipios = municipioRepository.findAll();
		System.out.println(municipios);
	}
}
