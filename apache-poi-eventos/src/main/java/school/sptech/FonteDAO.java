package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FonteDAO {

    public void criarTabela(){

        String sql = "create table fonte" +
                "    (idFonte int primary key auto_increment," +
                "    tipo varchar(255) not null," +
                "    porcentagem int not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de fonte no banco: " + ex.getMessage());
        }
    }

    public void salvar(Fonte f) {

        String sql = """
            INSERT INTO fonte
            (tipo, porcentagem)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getTipo());
            ps.setObject(2, f.getPorcentagem());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar fonte no banco: " + ex.getMessage());
        }
    }
}
