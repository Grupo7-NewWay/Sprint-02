package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LocalizacaoDAO {

    public void criarTabela(){

        String sql = "create table localizacao" +
                "    (idLocalizacao int primary key auto_increment," +
                "    uf varchar(2) not null," +
                "    cidade varchar(255) not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de localizacao no banco: " + ex.getMessage());
        }
    }

    public void salvar(Localizacao lo) {

        String sql = """
            INSERT INTO localizacao
            (uf, cidade)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lo.getUf());
            ps.setString(2, lo.getCidade());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar permanencia no banco: " + ex.getMessage());
        }
    }

}
