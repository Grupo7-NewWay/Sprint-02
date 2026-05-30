package school.sptech;

import java.time.LocalDate;

public class Eventos {


    private String nomeEvento;
    private String municipio;
    private LocalDate dtInicial;
    private LocalDate dtTermino;
    private String tipoEvento;
    private Integer publicoEsperado;

    public Eventos(String nomeEvento, String municipio, LocalDate dtInicial, LocalDate dtTermino, String tipoEvento, Integer publicoEsperado) {
        this.nomeEvento = nomeEvento;
        this.municipio = municipio;
        this.dtInicial = dtInicial;
        this.dtTermino = dtTermino;
        this.tipoEvento = tipoEvento;
        this.publicoEsperado = publicoEsperado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public LocalDate getDtInicial() {
        return dtInicial;
    }

    public void setDtInicial(LocalDate dtInicial) {
        this.dtInicial = dtInicial;
    }

    public LocalDate getDtTermino() {
        return dtTermino;
    }

    public void setDtTermino(LocalDate dtTermino) {
        this.dtTermino = dtTermino;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Integer getPublicoEsperado() {
        return publicoEsperado;
    }

    public void setPublicoEsperado(Integer publicoEsperado) {
        this.publicoEsperado = publicoEsperado;
    }

    @Override
    public String toString() {
        return "Eventos{" +
                "municipio='" + municipio + '\'' +
                ", nomeEvento='" + nomeEvento + '\'' +
                ", dtInicial=" + dtInicial +
                ", dtTermino=" + dtTermino +
                ", tipoEvento='" + tipoEvento + '\'' +
                ", publicoEsperado=" + publicoEsperado +
                '}';
    }
}
