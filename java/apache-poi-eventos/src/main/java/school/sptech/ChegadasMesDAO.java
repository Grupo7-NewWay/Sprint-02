package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChegadasMesDAO {
    public void criarTabela(){

        String sql = "create table chegada_mes" +
                "    (idChegadaMes int primary key auto_increment," +
                "    qtdChegadaMes int not null," +
                "    mes varchar(255) not null," +
                "    fkChegada int not null," +
                "    constraint fk_chegada_mes_chegada" +
                "        foreign key (fkChegada)" +
                "            references chegada(idChegada)" +
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
            (qtdChegadaMes, mes, fkChegada)
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
