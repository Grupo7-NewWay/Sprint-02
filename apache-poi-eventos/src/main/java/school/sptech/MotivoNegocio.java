package school.sptech;

public class MotivoNegocio {
    private String tipoMotivoNegocio;
    private Integer porcentagem;
    private Integer fk_negocio_motivo;

    public MotivoNegocio(String tipoMotivoNegocio, Integer porcentagem, Integer fk_negocio_motivo) {
        this.tipoMotivoNegocio = tipoMotivoNegocio;
        this.porcentagem = porcentagem;
        this.fk_negocio_motivo = fk_negocio_motivo;
    }

    public String getTipoMotivoNegocio() {
        return tipoMotivoNegocio;
    }

    public Integer getPorcentagem() {
        return porcentagem;
    }

    public Integer getFk_negocio_motivo() {
        return fk_negocio_motivo;
    }

    @Override
    public String toString() {
        return "Negocio{" +
                "tipoNegocio='" + tipoMotivoNegocio + '\'' +
                ", porcentagem=" + porcentagem +
                ", fk_negocio_motivo=" + fk_negocio_motivo +
                '}';
    }
}
