package notificacoes.newway;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SlackNotifier {

    private static final String WEBHOOK_URL = System.getenv("SLACK_WEBHOOK_URL");

    // ── Eventos do sistema ─────────────────────────────────────────────────────

    public static void sistemaLigado() {
        String mencoes = buildMencoes();
        enviar(mencoes + ":white_check_mark: *Sistema LIGADO* — " + agora());
    }

    public static void sistemaDesligado() {
        String mencoes = buildMencoes();
        enviar(mencoes + ":octagonal_sign: *Sistema DESLIGADO* — " + agora());
    }

    public static void arquivoLido(String nomeArquivo) {
        String mencoes = buildMencoes();
        enviar(mencoes + ":page_facing_up: *Arquivo lido:* `" + nomeArquivo + "` — " + agora());
    }

    public static void notificarErro(String titulo, Exception e) {
        String mencoes = buildMencoes();
        enviar(mencoes + ":x: *ERRO: " + titulo + "* — `" + e.getMessage() + "` — " + agora());
    }

    public static void alertaDados(String descricao) {
        String mencoes = buildMencoes();
        enviar(mencoes + ":warning: *Alerta de Dados:* " + descricao + " — " + agora());
    }

    // ── Internos ───────────────────────────────────────────────────────────────

    // Monta "  <@U123> <@U456> " com todos os usuários cadastrados.
    // No Slack, <@USERID> vira @NomeDoUsuario e dispara notificação pessoal.
    private static String buildMencoes() {
        List<Usuario> usuarios = UserManager.listar();
        if (usuarios.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Usuario u : usuarios) {
            sb.append("<@").append(u.getSlackId()).append("> ");
        }
        return sb.toString();
    }

    private static void enviar(String texto) {
        try {
            HttpURLConnection conn = (HttpURLConnection) URI.create(WEBHOOK_URL).toURL().openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String textoEscapado = texto.replace("\\", "\\\\").replace("\"", "\\\"");
            String json = "{\"text\": \"" + textoEscapado + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            System.out.println("Slack status: " + conn.getResponseCode());

        } catch (Exception e) {
            System.err.println("Erro ao enviar para Slack: " + e.getMessage());
        }
    }

    private static String agora() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}