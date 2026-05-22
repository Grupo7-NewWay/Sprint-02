package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServicoAgenciaDAO {

    public void criarTabela(){

        String sql = "create table servico_agencia" +
                "    (idServicoAgencia int primary key auto_increment," +
                "    tipo varchar(255) not null," +
                "    porcentagem int not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de serviço agência no banco: " + ex.getMessage());
        }
    }

    public void salvar(ServicoAgencia sa) {

        String sql = """
            INSERT INTO servico_agencia
            (tipo, porcentagem)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sa.getTipo());
            ps.setObject(2, sa.getPorcentagem());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar serviço agência no banco: " + ex.getMessage());
        }
    }
}
