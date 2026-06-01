package school.sptech;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class NotificadorSlack {

    private static final String API = "http://localhost:3000/api/slack/notificar";

    public static void arquivoLido(String nomeArquivo, int totalRegistros, int totalAvisos) {
        String body = "{"
                + "\"tipo\":\"arquivoLido\","
                + "\"arquivo\":\"" + escapar(nomeArquivo) + "\","
                + "\"totalRegistros\":" + totalRegistros + ","
                + "\"totalAvisos\":" + totalAvisos
                + "}";
        enviar(body);
    }

    public static void arquivoLido(String nomeArquivo) {
        String body = "{"
                + "\"tipo\":\"arquivoLido\","
                + "\"arquivo\":\"" + escapar(nomeArquivo) + "\""
                + "}";
        enviar(body);
    }

    public static void notificarErro(String nomeArquivo, String mensagemErro) {
        String body = "{"
                + "\"tipo\":\"erro\","
                + "\"arquivo\":\"" + escapar(nomeArquivo) + "\","
                + "\"erro\":\"" + escapar(mensagemErro) + "\""
                + "}";
        enviar(body);
    }

    public static void processoFinalizado() {
        enviar("{\"tipo\":\"processoFinalizado\"}");
    }

    private static void enviar(String body) {
        try {
            HttpURLConnection conn = (HttpURLConnection) URI.create(API).toURL().openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
            System.out.println("Slack notificado: " + conn.getResponseCode());
        } catch (Exception e) {
            System.err.println("Aviso: não foi possível notificar o Slack — " + e.getMessage());
        }
    }

    private static String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
