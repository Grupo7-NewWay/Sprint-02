package school.sptech;

public class Hospedagem {
    private String tipo;
    private Integer porcentagem;

    public Hospedagem(String tipo, Integer porcentagem) {
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
        return "Hospedagem{" +
                "tipo='" + tipo + '\'' +
                ", porcentagem=" + porcentagem +
                '}';
    }
}
