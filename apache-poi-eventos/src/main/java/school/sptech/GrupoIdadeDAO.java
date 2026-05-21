package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GrupoIdadeDAO {

    public void criarTabela(){

        String sql = "create table grupo_idade" +
                "    (idGrupoIdade int primary key auto_increment," +
                "    grupoIdade varchar(255) not null," +
                "    porcentagem int not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de grupo de idade no banco: " + ex.getMessage());
        }
    }

    public void salvar(GrupoIdade grid) {

        String sql = """
            INSERT INTO grupo_idade
            (tipo, porcentagem)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, grid.getGrupoIdade());
            ps.setObject(2, grid.getPorcentagem());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar grupo de idade no banco: " + ex.getMessage());
        }
    }

}
