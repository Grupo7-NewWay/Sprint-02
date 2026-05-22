package school.sptech;

public class Perfil {

    private Motivo motivo;
    private MotivoLazer motivolazer;
    private Hospedagem hospedagem;
    private Gasto gasto;
    private GrupoTuristico grupoTuristico;
    private GrupoIdade grupoIdade;
    private Fonte fonte;
    private ServicoAgencia servicoAgencia;
    private Permanencia permanencia;

    public Perfil(
            Motivo motivo,
            MotivoLazer motivolazer,
            Hospedagem hospedagem,
            Gasto gasto,
            GrupoTuristico grupoTuristico,
            GrupoIdade grupoIdade,
            Fonte fonte,
            ServicoAgencia servicoAgencia,
            Permanencia permanencia
    ) {
        this.motivo = motivo;
        this.motivolazer = motivolazer;
        this.hospedagem = hospedagem;
        this.gasto = gasto;
        this.grupoTuristico = grupoTuristico;
        this.grupoIdade = grupoIdade;
        this.fonte = fonte;
        this.servicoAgencia = servicoAgencia;
        this.permanencia = permanencia;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public MotivoLazer getMotivoLazer() {
        return motivolazer;
    }

    public Hospedagem getHospedagem() {
        return hospedagem;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public GrupoTuristico getGrupoTuristico() {
        return grupoTuristico;
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
                ", motivolazer=" + motivolazer +
                ", hospedagem=" + hospedagem +
                ", gasto=" + gasto +
                ", grupoTuristico=" + grupoTuristico +
                ", grupoIdade=" + grupoIdade +
                ", fonte=" + fonte +
                ", servicoAgencia=" + servicoAgencia +
                ", permanencia=" + permanencia +
                '}';
    }
}
