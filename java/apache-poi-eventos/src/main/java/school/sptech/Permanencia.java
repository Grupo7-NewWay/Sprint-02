package school.sptech;

public class Permanencia {
    private String tipo;
    private Integer qtdDias;

    public Permanencia(String tipo, Integer qtdDias) {
        this.tipo = tipo;
        this.qtdDias = qtdDias;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getQtdDias() {
        return qtdDias;
    }

    @Override
    public String toString() {
        return "Permanencia{" +
                "tipo='" + tipo + '\'' +
                ", qtdDias=" + qtdDias +
                '}';
    }
}
