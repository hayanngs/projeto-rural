package dev.hayann.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InicializadorDoBD {

    public static void inicializarBancoDeDados() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();

            List<String> scriptList = obterListaDeScripts();

            if (scriptList.isEmpty()) {
                /* TODO: RENDERIZAR ERRO */
            } else {
                for (String script : scriptList) {
                    connection.createStatement().executeUpdate(script);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<String> obterListaDeScripts() {
        String pastaResources = "src/main/resources";
        String padraoArquivoSQL = "regex:.*.sql";
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(padraoArquivoSQL);

        List<String> scriptList = new ArrayList<>();

        try {
            Path diretorioResources = Paths.get(pastaResources);

            List<Path> arquivosSQL = Files.walk(diretorioResources).filter(matcher::matches).toList();

            for (Path arquivo : arquivosSQL) {
                System.out.println("Arquivo SQL encontrado: " + arquivo.getFileName());
                String script = lerArquivoSQL(arquivo.toString());
                scriptList.add(script);
            }
            return scriptList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scriptList;
    }

    private static String lerArquivoSQL(String filePath) {
        try {
            StringBuilder script = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    script.append(line).append("\n");
                }
            }
            return script.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
