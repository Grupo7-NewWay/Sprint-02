package notificacoes.newway;

public class main {

    public static void main(String[] args) throws InterruptedException {

        // Sobe a API em background para o frontend conseguir cadastrar usuários
        Thread serverThread = new Thread(() -> {
            try {
                ApiServer.iniciar();
            } catch (Exception e) {
                System.err.println("Erro ao iniciar servidor: " + e.getMessage());
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();

        SlackNotifier.sistemaLigado();
        System.out.println("Sistema rodando...");

        for (int i = 1; i <= 3; i++) {
            System.out.println("Ciclo " + i + " iniciado...");

            Thread.sleep(10000);

            // Simula leitura de um arquivo e notifica os usuários cadastrados
            SlackNotifier.arquivoLido("dados_ciclo_" + i + ".xlsx");

            if (i == 2) {
                SlackNotifier.alertaDados("Ciclo " + i + ": arquivo Excel com dados inconsistentes!");
            }

            if (i == 3) {
                SlackNotifier.notificarErro("Ciclo " + i, new Exception("Timeout ao acessar o bucket"));
            }

            System.out.println("Ciclo " + i + " finalizado.");
        }

        SlackNotifier.sistemaDesligado();

        System.out.println("Simulação concluída. API continua rodando em http://localhost:8080 — pressione Ctrl+C para encerrar.");
        Thread.currentThread().join();
    }
}