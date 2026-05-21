package school.sptech;

public class Fonte {
    private String tipo;
    private Integer porcentagem;

    public Fonte(String tipo, Integer porcentagem) {
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
        return "Fonte{" +
                "tipo='" + tipo + '\'' +
                ", porcentagem=" + porcentagem +
                '}';
    }
}
