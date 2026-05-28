package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChegadasDAO {

    public void criarTabela(){

        String sql = "create table chegada" +
                "    (idChegada int primary key auto_increment," +
                "    ano varchar(255) not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de chegadas no banco: " + ex.getMessage());
        }
    }

    public void salvar(Chegadas c) {

        String sql = """
            INSERT INTO chegada
            (ano)
            VALUES (?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, c.getAno());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar chegadas no banco: " + ex.getMessage());
        }
    }

}
