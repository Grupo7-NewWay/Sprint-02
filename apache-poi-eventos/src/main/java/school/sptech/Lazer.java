package school.sptech;

public class Lazer {
    private String tipoLazer;
    private Integer porcentagem;
    private Integer fk_lazer_motivo;

    public Lazer(String tipoLazer, Integer porcentagem, Integer fk_lazer_motivo) {
        this.tipoLazer = tipoLazer;
        this.porcentagem = porcentagem;
        this.fk_lazer_motivo = fk_lazer_motivo;
    }

    public String getTipoLazer() {
        return tipoLazer;
    }

    public Integer getPorcentagem() {
        return porcentagem;
    }

    public Integer getFk_lazer_motivo() {
        return fk_lazer_motivo;
    }

    @Override
    public String toString() {
        return "Lazer{" +
                "tipoLazer='" + tipoLazer + '\'' +
                ", porcentagem=" + porcentagem +
                ", fk_lazer_motivo=" + fk_lazer_motivo +
                '}';
    }
}
