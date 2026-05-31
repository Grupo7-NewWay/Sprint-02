package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PermanenciaDAO {

    public void criarTabela(){

        String sql = "create table permanencia" +
                "    (idPermanencia int primary key auto_increment," +
                "    tipo varchar(255) not null," +
                "    qtd_dias int not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de permanencia no banco: " + ex.getMessage());
        }
    }

    public void salvar(Permanencia pe) {

        String sql = """
            INSERT INTO permanencia
            (tipo, qtd_dias)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pe.getTipo());
            ps.setObject(2, pe.getQtdDias());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar permanencia no banco: " + ex.getMessage());
        }
    }
}
