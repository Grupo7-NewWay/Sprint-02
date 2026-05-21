package school.sptech;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pacotes {
        private String nomePacote;
        private Integer qtdDisponivel;
        private Integer fk_pacote_perfil;
        private Integer fk_pacote_localizacao;
        private Integer fk_pacote_evento;
        private LocalDate dataCadastro;
        private LocalDate dataAtualizacao;

    public Pacotes(String nomePacote, Integer qtdDisponivel, Integer fk_pacote_perfil, Integer fk_pacote_localizacao, Integer fk_pacote_evento, LocalDate dataCadastro, LocalDate dataAtualizacao) {
        this.nomePacote = nomePacote;
        this.qtdDisponivel = qtdDisponivel;
        this.fk_pacote_perfil = fk_pacote_perfil;
        this.fk_pacote_localizacao = fk_pacote_localizacao;
        this.fk_pacote_evento = fk_pacote_evento;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getNomePacote() {
        return nomePacote;
    }

    public Integer getQtdDisponivel() {
        return qtdDisponivel;
    }

    public Integer getFk_pacote_perfil() {
        return fk_pacote_perfil;
    }

    public Integer getFk_pacote_localizacao() {
        return fk_pacote_localizacao;
    }

    public Integer getFk_pacote_evento() {
        return fk_pacote_evento;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    @Override
    public String toString() {
        return "Pacotes{" +
                "nomePacote='" + nomePacote + '\'' +
                ", qtdDisponivel=" + qtdDisponivel +
                ", fk_pacote_perfil=" + fk_pacote_perfil +
                ", fk_pacote_localizacao=" + fk_pacote_localizacao +
                ", fk_pacote_evento=" + fk_pacote_evento +
                ", dataCadastro=" + dataCadastro +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}
