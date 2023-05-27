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

    private static final String PASTA_RESOURCES = "src/main/resources";

    private static final String REGEX_SEQUENCES_SQL = "sequences.sql";

    private static final String REGEX_NO_FK_SQL = "no_fk.sql";

    private static final String REGEX_WITH_FK_SQL = "with_fk.sql";

    public static void inicializarBancoDeDados() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();

            List<String> sequenceScriptList = obterListaDeScripts(REGEX_SEQUENCES_SQL);
            List<String> noFkScriptList = obterListaDeScripts(REGEX_NO_FK_SQL);
            List<String> withFkScriptList = obterListaDeScripts(REGEX_WITH_FK_SQL);

            executarListaDeScript(sequenceScriptList, connection);
            executarListaDeScript(noFkScriptList, connection);
            executarListaDeScript(withFkScriptList, connection);

            connectionPool.releaseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executarListaDeScript(List<String> scriptList, Connection connection) {
        if (!scriptList.isEmpty()) {
            for (String script : scriptList) {
                try {
                    connection.createStatement().executeUpdate(script);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<String> obterListaDeScripts(String regex) {
        String padraoArquivoSQL = "regex:" + regex;
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(padraoArquivoSQL);

        List<String> scriptList = new ArrayList<>();

        try {
            Path diretorioResources = Paths.get(PASTA_RESOURCES);

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
