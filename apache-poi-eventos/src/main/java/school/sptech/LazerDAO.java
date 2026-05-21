package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LazerDAO {

    public void criarTabela() {
        String sql = "create table lazer" +
                "    (idLazer int primary key auto_increment," +
                "    tipoLazer varchar(255) not null," +
                "    porcentagem int not null," +
                "    idMotivo int not null," +
                "    constraint fk_lazer_motivo" +
                "        foreign key (idMotivo)" +
                "            references motivo(idMotivo)" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de lazer no banco: " + ex.getMessage());
        }
    }


    public void salvar(Lazer l) {

        String sql = """
            INSERT INTO lazer
            (tipoLazer, porcentagem, fk_lazer_motivo)
            VALUES (?, ?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getTipoLazer());
            ps.setObject(2, l.getPorcentagem());
            ps.setObject(3, l.getFk_lazer_motivo());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar lazer no banco: " + ex.getMessage());
        }
    }

}
