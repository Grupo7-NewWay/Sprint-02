package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GastoDAO {
    public void criarTabela(){

        String sql = "create table gasto" +
                "    (idGasto int primary key auto_increment," +
                "    tipo varchar(255) not null," +
                "    porcentagem int not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de gasto no banco: " + ex.getMessage());
        }
    }

    public void salvar(Gasto g) {

        String sql = """
            INSERT INTO gasto
            (tipo, porcentagem)
            VALUES (?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, g.getTipo());
            ps.setObject(2, g.getValor());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar gasto no banco: " + ex.getMessage());
        }
    }
}
