package school.sptech;

public class ChegadasMes extends Chegadas{
    private Integer qtdChegadasMes;
    private String mes;
    private Integer fk_chegadas_mes;

    public ChegadasMes(Integer ano, Integer qtdChegadasMes, String mes, Integer fk_chegadas_mes) {
        super(ano);
        this.qtdChegadasMes = qtdChegadasMes;
        this.mes = mes;
        this.fk_chegadas_mes = fk_chegadas_mes;
    }

    public Integer getQtdChegadasMes() {
        return qtdChegadasMes;
    }

    public String getMes() {
        return mes;
    }

    public Integer getFk_chegadas_mes() {
        return fk_chegadas_mes;
    }

    @Override
    public String toString() {
        return "ChegadaMes{" +
                "qtdChegadasMes=" + qtdChegadasMes +
                ", mes='" + mes + '\'' +
                ", fk_chegadas_mes=" + fk_chegadas_mes +
                "} " + super.toString();
    }
}
