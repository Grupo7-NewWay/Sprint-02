package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HospedagemDAO {

    public void criarTabela(){

        String sql = "create table hospedagem" +
                "    (idHospedagem int primary key auto_increment," +
                "    tipo varchar(255) not null," +
                "    porcentagem int not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de hospedagem no banco: " + ex.getMessage());
        }
    }

    public void salvar(Hospedagem h) {

        String sql = """
            INSERT INTO hospedagem
            (tipo, porcentagem)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, h.getTipo());
            ps.setObject(2, h.getPorcentagem());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar hospedagem no banco: " + ex.getMessage());
        }
    }

}
