package school.sptech;

import java.sql.*;
import java.time.LocalDateTime;

public class LogDao {

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS logs" +
                "(idLogs int primary key auto_increment," +
                "tipo varchar(255) not null," +
                "dateTimeLog datetime," +
                "descricao varchar(255) not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("Tabela criada com sucesso!");
            ps.execute();
        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabela de logs no banco: " + ex.getMessage());
        }
    }

    public void salvar(String tipo, String descricao) {
        String sql = "INSERT INTO logs (tipo, dateTimeLog, descricao) VALUES (?, ?, ?)";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, descricao);
            ps.executeUpdate();
            System.out.println("Log salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println("ERRO ao salvar log no banco: " + ex.getMessage());
        }
    }
}