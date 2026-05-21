package school.sptech;

public class Perfil {

    private Motivo motivo;
    private Lazer lazer;
    private Hospedagem hospedagem;
    private Gasto gasto;
    private Grupo grupo;
    private GrupoIdade grupoIdade;
    private Fonte fonte;
    private ServicoAgencia servicoAgencia;
    private Permanencia permanencia;

    public Perfil(
            Motivo motivo,
            Lazer lazer,
            Hospedagem hospedagem,
            Gasto gasto,
            Grupo grupo,
            GrupoIdade grupoIdade,
            Fonte fonte,
            ServicoAgencia servicoAgencia,
            Permanencia permanencia
    ) {
        this.motivo = motivo;
        this.lazer = lazer;
        this.hospedagem = hospedagem;
        this.gasto = gasto;
        this.grupo = grupo;
        this.grupoIdade = grupoIdade;
        this.fonte = fonte;
        this.servicoAgencia = servicoAgencia;
        this.permanencia = permanencia;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public Lazer getLazer() {
        return lazer;
    }

    public Hospedagem getHospedagem() {
        return hospedagem;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public GrupoIdade getGrupoIdade() {
        return grupoIdade;
    }

    public Fonte getFonte() {
        return fonte;
    }

    public ServicoAgencia getServicoAgencia() {
        return servicoAgencia;
    }

    public Permanencia getPermanencia() {
        return permanencia;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "motivo=" + motivo +
                ", lazer=" + lazer +
                ", hospedagem=" + hospedagem +
                ", gasto=" + gasto +
                ", grupo=" + grupo +
                ", grupoIdade=" + grupoIdade +
                ", fonte=" + fonte +
                ", servicoAgencia=" + servicoAgencia +
                ", permanencia=" + permanencia +
                '}';
    }
}
