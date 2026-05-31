package school.sptech;

import java.sql.*;

public class EventosDao {

    public void criarTabela() {

        String sql = "CREATE TABLE IF NOT EXISTS eventos" +
                "(idEvento int primary key auto_increment," +
                "nomeEvento varchar(255) not null," +
                "municipio varchar(255) not null," +
                "dtInicial date not null," +
                "dtTermino date not null," +
                "tipoEvento varchar(255) not null," +
                "publicoEsperado int not null" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Tabela criada com sucesso!");

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabelas de evento no banco: " + ex.getMessage());
        }

    }

    public void salvar(Eventos e) {

        if (e.getDtInicial() == null || e.getDtTermino() == null) {
            System.out.println("Data de início e término do evento não definidas");
            return;
        }

        String sql = """
            INSERT INTO eventos
            (nomeEvento, municipio, dtInicial, dtTermino, tipoEvento, publicoEsperado)
            VALUES (?, ?, ?, ?, ?, ?)
        """

                ;


        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNomeEvento());
            ps.setString(2, e.getMunicipio());
            ps.setObject(3, e.getDtInicial());
            ps.setObject(4, e.getDtTermino());
            ps.setString(5, e.getTipoEvento());
            ps.setObject(6, e.getPublicoEsperado());

            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar evento no banco: " + ex.getMessage());
        }
    }
}
