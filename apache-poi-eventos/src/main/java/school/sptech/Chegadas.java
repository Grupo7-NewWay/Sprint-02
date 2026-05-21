package school.sptech;

import java.time.LocalDate;

public class Chegadas {
    private String paisOrigem;
    private String viaAcesso;
    private Integer qtdChegadas;
    private LocalDate dataChegada;
    private Integer qtdChegadaMes;
    private String fk_chegada_localizacao;

    public Chegadas(String paisOrigem, String viaAcesso, Integer qtdChegadas, LocalDate dataChegada, Integer qtdChegadaMes, String fk_chegada_localizacao) {
        this.paisOrigem = paisOrigem;
        this.viaAcesso = viaAcesso;
        this.qtdChegadas = qtdChegadas;
        this.dataChegada = dataChegada;
        this.qtdChegadaMes = qtdChegadaMes;
        this.fk_chegada_localizacao = fk_chegada_localizacao;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public String getViaAcesso() {
        return viaAcesso;
    }

    public Integer getQtdChegadas() {
        return qtdChegadas;
    }

    public LocalDate getDataChegada() {
        return dataChegada;
    }

    public Integer getQtdChegadaMes() {
        return qtdChegadaMes;
    }

    public String getFk_chegada_localizacao() {
        return fk_chegada_localizacao;
    }

    @Override
    public String toString() {
        return "Chegadas{" +
                "paisOrigem='" + paisOrigem + '\'' +
                ", viaAcesso='" + viaAcesso + '\'' +
                ", qtdChegadas=" + qtdChegadas +
                ", dataChegada=" + dataChegada +
                ", qtdChegadaMes=" + qtdChegadaMes +
                ", fk_chegada_localizacao='" + fk_chegada_localizacao + '\'' +
                '}';
    }
}
