package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChegadasMesDAO {
    public void criarTabela(){

        String sql = "create table chegada_mes" +
                "    (id_chegada_mes int primary key auto_increment," +
                "    qtd_chegada_mes int not null," +
                "    mes varchar(255) not null," +
                "    id_chegada int not null," +
                "    constraint fk_chegada_mes" +
                "        foreign key (id_chegada)" +
                "            references chegada(id_chegada)" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de chegadas no banco: " + ex.getMessage());
        }
    }

    public void salvar(ChegadasMes cm) {

        String sql = """
            INSERT INTO chegada_mes
            (qtd_chegadas_mes, mes, fk_chegada_mes)
            VALUES (?, ?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, cm.getQtdChegadasMes());
            ps.setString(2, cm.getMes());
            ps.setObject(3, cm.getFk_chegadas_mes());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar chegadas por mês no banco: " + ex.getMessage());
        }
    }
}
