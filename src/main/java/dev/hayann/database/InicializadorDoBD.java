//package dev.hayann.database;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class InicializadorDoBD {
//
//    public void inicializarBancoDeDados() {
//
//
//
//
//
//
//
//        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
//            String sqlFilePath = "caminho/do/arquivo.sql";
//            String sqlScript = lerArquivoSQL(sqlFilePath);
//
//            Statement stmt = conn.createStatement();
//            stmt.executeUpdate(sqlScript);
//
//            System.out.println("Tabela criada a partir do arquivo '" + sqlFilePath + "'.");
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static List<String> obterListaDeScripts() {
//        String pastaResources = "src/main/resources/tabelas";
//        String padraoArquivoSQL = "glob:*.sql";
//
//        try {
//            Path diretorioResources = Paths.get(pastaResources);
//
//            List<Path> arquivosSQL = Files.walk(diretorioResources).toList();
//
//            for (Path arquivo : arquivosSQL) {
//                System.out.println("Arquivo SQL encontrado: " + arquivo.getFileName());
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String lerArquivoSQL(String filePath) {
//        try {
//            StringBuilder script = new StringBuilder();
//            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    script.append(line).append("\n");
//                }
//            }
//            return script.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}
