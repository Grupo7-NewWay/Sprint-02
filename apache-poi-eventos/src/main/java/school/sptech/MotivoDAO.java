package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MotivoDAO {

    public void criarTabela(){

        String sql = "create table motivo" +
                "    (idMotivo int primary key auto_increment," +
                "    porcentagem int not null," +
                "    tipo varchar(255) not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de motivo no banco: " + ex.getMessage());
        }
    }

    public void salvar(Motivo m) {

        String sql = """
            INSERT INTO motivo
            (porcentagem, tipo)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, m.getPorcentagem());
            ps.setString(2, m.getTipo());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar motivo no banco: " + ex.getMessage());
        }
    }
}
