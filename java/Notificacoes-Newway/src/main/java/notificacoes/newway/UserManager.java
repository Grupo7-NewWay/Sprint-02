package notificacoes.newway;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String ARQUIVO = "usuarios.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Usuario> listar() {
        try {
            File file = new File(ARQUIVO);
            if (!file.exists()) return new ArrayList<>();
            Dados dados = mapper.readValue(file, Dados.class);
            return dados.getUsuarios() != null ? dados.getUsuarios() : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao ler usuários: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvar(Usuario usuario) throws IOException {
        List<Usuario> lista = listar();
        lista.removeIf(u -> u.getSlackId().equals(usuario.getSlackId()));
        lista.add(usuario);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO), new Dados(lista));
    }

    public static void remover(String slackId) throws IOException {
        List<Usuario> lista = listar();
        lista.removeIf(u -> u.getSlackId().equals(slackId));
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO), new Dados(lista));
    }

    public static class Dados {
        private List<Usuario> usuarios;
        public Dados() {}
        public Dados(List<Usuario> usuarios) { this.usuarios = usuarios; }
        public List<Usuario> getUsuarios() { return usuarios; }
        public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
    }
}