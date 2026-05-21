package school.sptech;

public class GrupoIdade {
    private String grupoIdade;
    private Integer porcentagem;

    public GrupoIdade(String grupoIdade, Integer porcentagem) {
        this.grupoIdade = grupoIdade;
        this.porcentagem = porcentagem;
    }

    public String getGrupoIdade() {
        return grupoIdade;
    }

    public Integer getPorcentagem() {
        return porcentagem;
    }

    @Override
    public String toString() {
        return "GrupoIdade{" +
                "grupoIdade='" + grupoIdade + '\'' +
                ", porcentagem=" + porcentagem +
                '}';
    }
}
