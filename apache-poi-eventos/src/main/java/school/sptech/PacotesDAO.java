package school.sptech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacotesDAO {

    public void criarTabela(){
        String sql ="CREATE TABLE pacote" +
                "    (idPacote int primary key auto_increment," +
                "    nomePacote varchar(255) not null," +
                "    qtdDisponivel int not null default 0," +
                "    idPerfil int not null" +
                "    constraint fk_pacote_perfil" +
                "        foreign key (idPerfil)" +
                "            references perfil(idPerfil)," +
                "    idLocalizacao int not null," +
                "    constraint fk_pacote_localizacao" +
                "        foreign key (idLocalizacao)" +
                "            references localizacao(idLocalizacao)," +
                "    idEvento int not null," +
                "    constraint fk_pacote_evento" +
                "        foreign key (idEvento)" +
                "            references evento(idEvento)," +
                "    dataCadastro datetime default current_timestamp," +
                "    dataAtualizacao datetime default current_timestamp on update current_timestamp" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de evento no banco: " + ex.getMessage());
        }
    }

    public void salvar(Pacotes p) {

        String sql = """
            INSERT INTO pacote
            (nomePacote, qtdDisponivel,fk_pacote_perfil, fk_pacote_localizacao, fk_pacote_Evento, dataCadastro, dataAtualizacao)
            VALUES (?, ?, ?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNomePacote());
            ps.setObject(2, p.getQtdDisponivel());
            ps.setObject(3, p.getFk_pacote_perfil());
            ps.setObject(4, p.getFk_pacote_localizacao());
            ps.setObject(5, p.getFk_pacote_evento());
            ps.setObject(6, p.getDataCadastro());
            ps.setObject(7, p.getDataAtualizacao());
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar pacote no banco: " + ex.getMessage());
        }
    }
}
