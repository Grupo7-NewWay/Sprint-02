package school.sptech;

import java.time.LocalDateTime;

public class Logs {
    private Integer idLogs;
    private String tipo;
    private LocalDateTime dateTimeLog;
    private String descricao;
    private Integer AgenciaId;

    public Logs(Integer idLogs, String tipo, LocalDateTime dateTimeLog, String descricao, Integer agenciaId) {
        this.idLogs = idLogs;
        this.tipo = tipo;
        this.dateTimeLog = dateTimeLog;
        this.descricao = descricao;
        AgenciaId = agenciaId;
    }

    public Integer getIdLogs() {
        return idLogs;
    }

    public void setIdLogs(Integer idLogs) {
        this.idLogs = idLogs;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDateTimeLog() {
        return dateTimeLog;
    }

    public void setDateTimeLog(LocalDateTime dateTimeLog) {
        this.dateTimeLog = dateTimeLog;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAgenciaId() {
        return AgenciaId;
    }

    public void setAgenciaId(Integer agenciaId) {
        AgenciaId = agenciaId;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "idLogs=" + idLogs +
                ", tipo='" + tipo + '\'' +
                ", dateTimeLog=" + dateTimeLog +
                ", descricao='" + descricao + '\'' +
                ", AgenciaId=" + AgenciaId +
                '}';
    }
}
