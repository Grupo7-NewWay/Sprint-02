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
            String sql = "INSERT INTO logs (tipo, dateTimeLog, descricao, idAgencia) VALUES (?, ?, ?, ?)";

            try (Connection con = ConexaoBD.conectar();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, tipo);
                ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(3, descricao);
                ps.setInt(4, 1);

                ps.executeUpdate();

                System.out.println("Log salvo com sucesso!");

            } catch (SQLException ex) {
                System.out.println("ERRO ao salvar log no banco: " + ex.getMessage());
            }
        }
}
