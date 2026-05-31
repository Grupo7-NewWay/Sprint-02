package school.sptech;

public class MotivoLazer {
    private Tipo tipo;
    private Integer porcentagem;
    private Integer fk_lazer_motivo;

    public MotivoLazer(Tipo tipo, Integer porcentagem, Integer fk_lazer_motivo) {
        this.tipo = tipo;
        this.porcentagem = porcentagem;
        this.fk_lazer_motivo = fk_lazer_motivo;
    }

    public Tipo getTipo() {
        return tipo;
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
                "tipoLazer='" + tipo + '\'' +
                ", porcentagem=" + porcentagem +
                ", fk_lazer_motivo=" + fk_lazer_motivo +
                '}';
    }
}
