package school.sptech;

public class ChegadasLocalidade extends Chegadas {
    private Integer qtdChegadaLocalidade;
    private String localidade;
    private Integer fk_chegada_localizacao;


    public ChegadasLocalidade(Integer ano, Integer qtdChegadaLocalidade, String localidade, Integer fk_chegada_localizacao) {
        super(ano);
        this.qtdChegadaLocalidade = qtdChegadaLocalidade;
        this.localidade = localidade;
        this.fk_chegada_localizacao = fk_chegada_localizacao;
    }

    public Integer getQtdChegadaLocalidade() {
        return qtdChegadaLocalidade;
    }

    public String getLocalidade() {
        return localidade;
    }

    public Integer getFk_chegada_localizacao() {
        return fk_chegada_localizacao;
    }

    @Override
    public String toString() {
        return "ChegadaLocalidade{" +
                "qtdChegadaLocalidade=" + qtdChegadaLocalidade +
                ", localidade='" + localidade + '\'' +
                ", fk_chegada_localizacao=" + fk_chegada_localizacao +
                "} " + super.toString();
    }
}
