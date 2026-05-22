package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MotivoNegocioDAO {

    public void criarTabela() {
        String sql = "create table motivonegocio" +
                "    (idMotivoNegocio int primary key auto_increment," +
                "    tipoMotivoNegocio varchar(255) not null," +
                "    porcentagem int not null," +
                "    idMotivo int not null," +
                "    constraint fk_negocio_motivo" +
                "        foreign key (idMotivo)" +
                "            references motivo(idMotivo)" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de negocio no banco: " + ex.getMessage());
        }
    }


    public void salvar(MotivoNegocio ml) {

        String sql = """
            INSERT INTO negocio
            (tipoMotivoNegocio, porcentagem, fk_negocio_motivo)
            VALUES (?, ?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ml.getTipoMotivoNegocio());
            ps.setObject(2, ml.getPorcentagem());
            ps.setObject(3, ml.getFk_negocio_motivo());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar negocio no banco: " + ex.getMessage());
        }
    }

}
