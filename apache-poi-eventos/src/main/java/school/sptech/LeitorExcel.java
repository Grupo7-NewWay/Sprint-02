package school.sptech;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LeitorExcel {

    public List<Eventos> extrairEventos(String caminhoArquivo) {

        List<Eventos> eventosExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        EventosDao eventosDao = new EventosDao();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String municipio = getString(row, 1);
                String nomeEvento = getString(row, 3);
                String tipoEvento = getString(row, 13);
                Integer publico = getInteger(row, 18);

                if (municipio == null || municipio.isBlank()) {
                    warns.add("Município inválido");
                }

                if (nomeEvento == null || nomeEvento.isBlank()) {
                    erroCritico = true;
                }

                if (tipoEvento == null || tipoEvento.isBlank()) {
                    warns.add("Tipo de evento inválido");
                }

                if (publico == null || publico <= 0) {
                    warns.add("Público inválido");
                }

                LocalDate dtInicial = getData(row.getCell(9), linha, "data inicial", warns);
                LocalDate dtTermino = getData(row.getCell(10), linha, "data término", warns);

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: nome do evento vazio)"
                    );
                    continue;
                }

                Eventos evento = new Eventos(
                        municipio,
                        nomeEvento,
                        dtInicial,
                        dtTermino,
                        tipoEvento,
                        publico
                );

                eventosExtraidos.add(evento);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    eventosDao.salvar(evento);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    eventosDao.salvar(evento);
                }
            }

            System.out.println("Leitura finalizada");

            return eventosExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return eventosExtraidos;
        }
    }

    private String getString(Row row, int index) {
        Cell cell = row.getCell(index);
        return cell == null ? null : cell.toString().trim();
    }

    private Integer getInteger(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            return cell == null ? null : (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate getData(Cell cell, int linha, String campo, List<String> warns) {

        if (cell == null) {
            warns.add(campo + " vazia");
            return null;
        }

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getLocalDateTimeCellValue().toLocalDate();
            }

            String dataTexto = cell.toString()
                    .replace("jan.", "01")
                    .replace("fev.", "02")
                    .replace("mar.", "03")
                    .replace("abr.", "04")
                    .replace("mai.", "05")
                    .replace("jun.", "06")
                    .replace("jul.", "07")
                    .replace("ago.", "08")
                    .replace("set.", "09")
                    .replace("out.", "10")
                    .replace("nov.", "11")
                    .replace("dez.", "12");

            return LocalDate.parse(dataTexto, DateTimeFormatter.ofPattern("d-MM-yyyy"));

        } catch (Exception e) {
            warns.add(campo + " inválida (" + cell + ")");
            return null;
        }
    }
}