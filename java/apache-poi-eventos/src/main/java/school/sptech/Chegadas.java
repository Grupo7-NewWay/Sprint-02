package school.sptech;

import java.time.LocalDate;

public class Chegadas {
    private Integer ano;

    public Chegadas(Integer ano) {
        this.ano = ano;
    }

    public Integer getAno() {
        return ano;
    }

    @Override
    public String toString() {
        return "Chegadas{" +
                "ano=" + ano +
                '}';
    }
}
