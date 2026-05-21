package school.sptech;

public class ServicoAgencia {
    private String tipo;
    private Integer porcentagem;

    public ServicoAgencia(String tipo, Integer porcentagem) {
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
        return "ServicoAgencia{" +
                "tipo='" + tipo + '\'' +
                ", porcentagem=" + porcentagem +
                '}';
    }
}
