package school.sptech;

public class GrupoTuristico extends Grupo{
    private String tipo;
    private Integer porcentagem;

    public GrupoTuristico(String tipo, Integer porcentagem, String tipo1, Integer porcentagem1) {
        super(tipo, porcentagem);
        this.tipo = tipo1;
        this.porcentagem = porcentagem1;
    }

    public GrupoTuristico(String tipo, Integer porcentagem) {
        this.tipo = tipo;
        this.porcentagem = porcentagem;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getPorcentagem() {
        return porcentagem;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "tipo='" + tipo + '\'' +
                ", porcentagem=" + porcentagem +
                '}';
    }
}
