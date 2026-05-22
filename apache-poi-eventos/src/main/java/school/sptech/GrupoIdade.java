package school.sptech;

public class GrupoIdade extends Grupo{
    private String grupoIdade;
    private Integer porcentagem;

    public GrupoIdade(String tipo, Integer porcentagem, String grupoIdade, Integer porcentagem1) {
        super(tipo, porcentagem);
        this.grupoIdade = grupoIdade;
        this.porcentagem = porcentagem1;
    }

    public GrupoIdade(String tipo, Integer porcentagem) {
        super();
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
