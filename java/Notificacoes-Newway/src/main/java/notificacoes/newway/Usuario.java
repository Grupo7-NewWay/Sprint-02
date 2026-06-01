package notificacoes.newway;

public class Usuario {

    private String nome;
    private String slackId;

    public Usuario() {}

    public Usuario(String nome, String slackId) {
        this.nome = nome;
        this.slackId = slackId;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSlackId() { return slackId; }
    public void setSlackId(String slackId) { this.slackId = slackId; }
}