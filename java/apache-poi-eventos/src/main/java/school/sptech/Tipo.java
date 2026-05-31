package school.sptech;

public enum Tipo {
    SOLPRAIA(68.8),
    NATUREZA(16.6),
    CULTURA(9.7),
    ESPORTES(1.3),
    INCENTIVO(0.1),
    EVENTOS(3.5),
    OUTROS(2.8);

    private final Double valor;

    Tipo(Double valor){this.valor = valor;}

    public Double getValor(){return valor;}

    @Override
    public String toString() {
        return "Tipo{" +
                "valor=" + valor +
                "} " + super.toString();
    }
}
