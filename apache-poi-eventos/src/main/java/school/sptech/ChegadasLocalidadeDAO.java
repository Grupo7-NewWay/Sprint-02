package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChegadasLocalidadeDAO {
    public void criarTabela(){

        String sql = "create table chegada_localidade" +
                "    (id_chegada_localidade int primary key auto_increment," +
                "    qtd_chegada_localidade int not null," +
                "    localidade varchar(255) not null," +
                "    id_chegada int not null," +
                "    constraint fk_chegada_localizacao" +
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

    public void salvar(ChegadasLocalidade cl) {

        String sql = """
            INSERT INTO chegada_localidade
            (qtd_chegadas_localidade, localidade, fk_chegada_localidade)
            VALUES (?, ?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, cl.getQtdChegadaLocalidade());
            ps.setString(2, cl.getLocalidade());
            ps.setObject(3, cl.getQtdChegadaLocalidade());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar chegadas localidade no banco: " + ex.getMessage());
        }
    }
}
