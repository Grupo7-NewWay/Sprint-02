package school.sptech;

public class Localizacao {
    private String uf;
    private String cidade;

    public Localizacao(String uf, String cidade) {
        this.uf = uf;
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getCidade() {
        return cidade;
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "uf='" + uf + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}
