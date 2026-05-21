package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GrupoDAO {

    public void criarTabela(){

        String sql = "create table grupo" +
                "    (idGrupo int primary key auto_increment," +
                "    tipo varchar(255) not null," +
                "    porcentagem int not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de grupo no banco: " + ex.getMessage());
        }
    }

    public void salvar(Grupo gr) {

        String sql = """
            INSERT INTO grupo
            (tipo, porcentagem)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, gr.getTipo());
            ps.setObject(2, gr.getPorcentagem());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar grupo no banco: " + ex.getMessage());
        }
    }

}
