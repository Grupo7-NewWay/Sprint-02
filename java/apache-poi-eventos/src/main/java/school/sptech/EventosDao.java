package school.sptech;

import java.sql.*;

public class EventosDao {

    public void criarTabela() {

        String sql = "CREATE TABLE IF NOT EXISTS eventos" +
                "(idEvento int primary key auto_increment," +
                "nomeEvento varchar(255) not null," +
                "municipio varchar(255)," +
                "estado varchar(100)," +
                "dtInicial date," +
                "dtTermino date," +
                "tipoEvento varchar(255)," +
                "publicoEsperado int" +
                ")";

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("Tabela criada com sucesso!");
            ps.execute();
        } catch (SQLException ex) {
            System.out.println("ERRO ao criar a tabela de eventos no banco: " + ex.getMessage());
        }
    }

    public void salvar(Eventos e) {

        if (e.getDtInicial() == null || e.getDtTermino() == null) {
            System.out.println("Data de início e término do evento não definidas");
            return;
        }

        String sql = """
            INSERT INTO eventos
            (nomeEvento, municipio, estado, dtInicial, dtTermino, tipoEvento, publicoEsperado)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = ConexaoBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNomeEvento());
            ps.setString(2, e.getlocalidade());
            ps.setString(3, e.getEstado());
            ps.setObject(4, e.getDtInicial());
            ps.setObject(5, e.getDtTermino());
            ps.setString(6, e.getTipoEvento());
            ps.setObject(7, e.getPublicoEsperado());

            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("ERRO ao salvar evento no banco: " + ex.getMessage());
        }
    }
}