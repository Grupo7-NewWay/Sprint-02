package school.sptech;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MetodoConsole {

    String getDataHora() {
        LocalDateTime dtHora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dtHoraFormatada = dtHora.format(formatter);

        return dtHoraFormatada;
    }

    void logCadastro() throws InterruptedException {

        System.out.println("Inserindo informações do usuário no banco de dados..." + " " + getDataHora());
        System.out.println("-".repeat(75));
        Thread.sleep(2000);
        System.out.println("Redirecionando para a tela de Login..." + " " + getDataHora());
        System.out.println("-".repeat(75));
        Thread.sleep(2000);
    }

    void logLogin() throws InterruptedException {

        System.out.println("Validando login e senha..." + " " + getDataHora());
        System.out.println("-".repeat(75));
        Thread.sleep(500);
        System.out.println("Credenciais validadas!" + " " + getDataHora());
        System.out.println("-".repeat(75));
        Thread.sleep(1500);
        System.out.println("Redirecionamento para o painel de controle..." + " " + getDataHora());
        System.out.println("-".repeat(75));
        Thread.sleep(1500);
        System.out.println("Buscando dados dos gráficos" + " " + getDataHora());
        System.out.println("-".repeat(75));
        Thread.sleep(1500);
        System.out.println("Exibindo KPI's da dashboard" + " " + getDataHora());
        System.out.println("-".repeat(75));
    }

    void logOut() throws InterruptedException {
        System.out.println("Usuário clica no botão sair..." + " " + getDataHora());
        System.out.println("-".repeat(75));
        Thread.sleep(3000);
        System.out.println("Deslogando da página exclusiva..." + " " + getDataHora());
        System.out.println("-".repeat(75));
    }
}
