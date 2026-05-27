package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChegadasDAO {

    public void criarTabela(){

        String sql = "create table chegada" +
                "    (idChegada int primary key auto_increment," +
                "    paisOrigem varchar(255) not null," +
                "    viaAcesso varchar(255) not null," +
                "    qtdChegadas int not null," +
                "    dataChegada date not null," +
                "    qtdChegadaMes int not null," +
                "    idLocalizacao int not null," +
                "    constraint fk_chegada_localizacao" +
                "        foreign key (idLocalizacao)" +
                "            references localizacao(idLocalizacao)" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de chegadas no banco: " + ex.getMessage());
        }
    }

    public void salvar(Chegadas c) {

        String sql = """
            INSERT INTO chegada
            (paisOrigem, viaAcesso, qtdChegadas, dataChegada, qtdChegadasMes, fk_chegada_localizacao)
            VALUES (?, ?, ?, ?, ?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getPaisOrigem());
            ps.setObject(2, c.getViaAcesso());
            ps.setObject(3, c.getQtdChegadas());
            ps.setObject(4, c.getDataChegada());
            ps.setObject(5, c.getQtdChegadaMes());
            ps.setObject(6, c.getFk_chegada_localizacao());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar chegadas no banco: " + ex.getMessage());
        }
    }

}
