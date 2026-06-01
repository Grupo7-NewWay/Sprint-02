package notificacoes.newway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ApiServer {

    private static final int PORTA = 8080;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void iniciar() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORTA), 0);
        server.createContext("/api/usuarios", ApiServer::handleUsuarios);
        server.createContext("/", ApiServer::handleEstatico);
        server.start();
        System.out.println("API rodando em http://localhost:" + PORTA);
    }

    // ── Rota /api/usuarios ─────────────────────────────────────────────────────

    private static void handleUsuarios(HttpExchange ex) throws IOException {
        adicionarCORS(ex);

        if ("OPTIONS".equals(ex.getRequestMethod())) {
            ex.sendResponseHeaders(204, -1);
            return;
        }

        String metodo = ex.getRequestMethod();
        String path   = ex.getRequestURI().getPath();

        if ("GET".equals(metodo)) {
            List<Usuario> usuarios = UserManager.listar();
            responderJson(ex, 200, mapper.writeValueAsString(usuarios));

        } else if ("POST".equals(metodo)) {
            try (InputStream body = ex.getRequestBody()) {
                Usuario usuario = mapper.readValue(body, Usuario.class);
                if (usuario.getNome() == null || usuario.getNome().isBlank()
                        || usuario.getSlackId() == null || usuario.getSlackId().isBlank()) {
                    responderJson(ex, 400, "{\"erro\":\"nome e slackId são obrigatórios\"}");
                    return;
                }
                UserManager.salvar(usuario);
                responderJson(ex, 201, "{\"mensagem\":\"Cadastrado com sucesso!\"}");
            }

        } else if ("DELETE".equals(metodo) && path.startsWith("/api/usuarios/")) {
            String slackId = path.substring("/api/usuarios/".length());
            UserManager.remover(slackId);
            responderJson(ex, 200, "{\"mensagem\":\"Usuário removido.\"}");

        } else {
            responderJson(ex, 405, "{\"erro\":\"Método não permitido\"}");
        }
    }

    // ── Rota estática (serve arquivos da pasta frontend/) ─────────────────────

    private static void handleEstatico(HttpExchange ex) throws IOException {
        String requestPath = ex.getRequestURI().getPath();
        if ("/".equals(requestPath)) requestPath = "/perfil.html";

        Path filePath = Paths.get("frontend" + requestPath);

        if (!Files.exists(filePath)) {
            byte[] body = "404 - Not Found".getBytes(StandardCharsets.UTF_8);
            ex.sendResponseHeaders(404, body.length);
            ex.getResponseBody().write(body);
            ex.getResponseBody().close();
            return;
        }

        String contentType = requestPath.endsWith(".html") ? "text/html; charset=utf-8"
                : requestPath.endsWith(".css")  ? "text/css"
                : requestPath.endsWith(".js")   ? "application/javascript"
                : "application/octet-stream";

        byte[] content = Files.readAllBytes(filePath);
        ex.getResponseHeaders().set("Content-Type", contentType);
        ex.sendResponseHeaders(200, content.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(content);
        }
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private static void adicionarCORS(HttpExchange ex) {
        ex.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        ex.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void responderJson(HttpExchange ex, int status, String body) throws IOException {
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }
}