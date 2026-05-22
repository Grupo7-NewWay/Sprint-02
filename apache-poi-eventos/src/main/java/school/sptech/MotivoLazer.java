package school.sptech;

public class MotivoLazer {
    private String tipoMotivoLazer;
    private Integer porcentagem;
    private Integer fk_lazer_motivo;

    public MotivoLazer(String tipoMotivoLazer, Integer porcentagem, Integer fk_lazer_motivo) {
        this.tipoMotivoLazer = tipoMotivoLazer;
        this.porcentagem = porcentagem;
        this.fk_lazer_motivo = fk_lazer_motivo;
    }

    public String getTipoMotivoLazer() {
        return tipoMotivoLazer;
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
                "tipoLazer='" + tipoMotivoLazer + '\'' +
                ", porcentagem=" + porcentagem +
                ", fk_lazer_motivo=" + fk_lazer_motivo +
                '}';
    }
}
