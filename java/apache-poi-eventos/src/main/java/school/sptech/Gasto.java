package school.sptech;

public class Gasto {
    private String tipo;
    private Double valor;

    public Gasto(String tipo, Double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "tipo='" + tipo + '\'' +
                ", valor=" + valor +
                '}';
    }
}
