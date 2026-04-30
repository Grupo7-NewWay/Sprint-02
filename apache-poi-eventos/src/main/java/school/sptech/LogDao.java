package school.sptech;

import java.sql.*;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class LogDao {

        public void criarTabela(){

            String sql = "CREATE TABLE IF NOT EXISTS logs" +
                    "(idLogs int primary key auto_increment," +
                    "tipo varchar(255) not null," +
                    "dateTimeLog datetime," +
                    "descricao varchar(255) not null," +
                    "idAgencia int not null," +
                    "constraint fk_logs_agencia" +
                    "foreign key (idAgencia)" +
                    "references agencia(idAgencia)" +
                    ")";

            try (Connection con = ConexaoBD.conectar();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                System.out.println("Tabela criada com sucesso!");

                ps.execute();

            } catch (SQLException ex) {
                System.out.println("ERRO ao criar a tabelas de evento no banco: " + ex.getMessage());
            }
        }

        public void salvar(String tipo, String descricao) {

            //System.out.println("[" + tipo + "] " + descricao);
            System.out.println("INSERT INTO logs VALUES ("
                    + tipo + ", "
                    + LocalDateTime.now() + ", "
                    + descricao + ", 1)");

            String sqlLogs = """
            INSERT INTO logs (tipo, dateTimeLog, descricao, idAgencia)
            VALUES (?, ?, ?, ?)
        """;

            try (Connection con = ConexaoBD.conectar();
                 PreparedStatement ps = con.prepareStatement(sqlLogs)) {

                ps.setString(1, tipo);
                ps.setObject(2, LocalDateTime.now());
                ps.setString(3, descricao);
                ps.setInt(4, 1);

                ps.executeUpdate();

            } catch (Exception e) {
                System.out.println("Erro ao salvar log no banco" + e.getMessage());
            }
        }
}
