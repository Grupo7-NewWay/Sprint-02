package school.sptech;

public class Motivo {
    private Integer porcentagem;
    private String tipo;

    public Motivo(Integer porcentagem, String tipo) {
        this.porcentagem = porcentagem;
        this.tipo = tipo;
    }

    public Integer getPorcentagem() {
        return porcentagem;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Motivo{" +
                "porcentagem=" + porcentagem +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
