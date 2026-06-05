package school.sptech;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeitorExcel {
    private final DataFormatter formatter = new DataFormatter();

    public List<Eventos> extrairEventos() {

        List<Eventos> eventosExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        EventosDao eventosDao = new EventosDao();

        try (
                InputStream arquivo = lerDoS3(
                        AmbienteConfig.BUCKET,
                        AmbienteConfig.EVENTOS
                );
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

                LocalDate dtInicial = getData(row.getCell(9));
                LocalDate dtTermino = getData(row.getCell(10));

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: nome do evento vazio)"
                    );
                    continue;
                }

                Eventos evento = new Eventos(
                        nomeEvento,
                        municipio,
                        dtInicial,
                        dtTermino,
                        tipoEvento,
                        publico
                );

                eventosExtraidos.add(evento);

                if (!warns.isEmpty()) {
                    logDao.salvar("WARN", "Linha " + linha + " com avisos: " + String.join(" | ", warns));
                } else {
                    logDao.salvar("INFO", "Linha " + linha + " processada com sucesso");
                }

                eventosDao.salvar(evento);
            }

            System.out.println("Leitura finalizada");
            return eventosExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return eventosExtraidos;
        }
    }

    public void extrairDemandaTuristica() {

        LogDao logDao = new LogDao();
        GastoDAO gastoDAO = new GastoDAO();
        PermanenciaDAO permanenciaDAO = new PermanenciaDAO();

        try (
                InputStream arquivo = lerDoS3(
                        AmbienteConfig.BUCKET,
                        AmbienteConfig.DEMANDA
                );
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheet("DEMANDA-SINTESE BRASIL_4.1");

            for (int i = 38; i <= 40; i++) {

                Row row = sheet.getRow(i);
                String tipo = getString(row, 3);
                Double valorGasto = getDouble(row, 3);

                if (valorGasto != null && tipo != null) {
                    Gasto gasto = new Gasto(tipo, valorGasto);
                    gastoDAO.salvar(gasto);
                }
            }

            for (int i = 43; i <= 45; i++) {

                Row row = sheet.getRow(i);
                String tipo = getString(row, 1);
                Integer qtdDias = getInteger(row, 3);

                if (qtdDias != null) {
                    Permanencia permanencia = new Permanencia(tipo, qtdDias);
                    permanenciaDAO.salvar(permanencia);
                }
            }

            logDao.salvar("INFO", "Demanda turística processada");

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao processar demanda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void extrairChegadas() {

        LogDao logDao = new LogDao();
        ChegadasDAO chegadasDAO = new ChegadasDAO();
        ChegadasLocalidadeDAO chegadasLocalidadeDAO = new ChegadasLocalidadeDAO();
        ChegadasMesDAO chegadasMesDAO = new ChegadasMesDAO();

        try (
                InputStream arquivo = lerDoS3(
                        AmbienteConfig.BUCKET,
                        AmbienteConfig.CHEGADASTURISTAS
                );
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheet("SÍNTESE BRASIL_3.1-3.2");

            for (int i = 3; i <= 5; i++) {
                Row rowAno = sheet.getRow(9);
                Integer ano = getInteger(rowAno, i);

                if (ano != null) {
                    chegadasDAO.salvar(new Chegadas(ano));
                }
            }

            for (int i = 12; i <= 29; i++) {

                Row row = sheet.getRow(i);
                Integer qtdChegadaLocalidade = getInteger(row, 3);
                String localidade = getString(row, 1);

                if (qtdChegadaLocalidade != null && localidade != null) {
                    ChegadasLocalidade chegadasLocalidade = new ChegadasLocalidade(
                            2019,
                            qtdChegadaLocalidade,
                            localidade,
                            1
                    );
                    chegadasLocalidadeDAO.salvar(chegadasLocalidade);
                }
            }

            for (int i = 46; i <= 57; i++) {

                Row row = sheet.getRow(i);
                Integer qtdChegadasMes = getInteger(row, 3);
                String mes = getString(row, 1);

                if (qtdChegadasMes != null && mes != null) {
                    ChegadasMes chegadasMes = new ChegadasMes(
                            2019,
                            qtdChegadasMes,
                            mes,
                            1
                    );
                    chegadasMesDAO.salvar(chegadasMes);
                }
            }

            logDao.salvar("INFO", "Chegadas de turistas processadas");

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao processar chegadas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getString(Row row, int index) {
        Cell cell = row.getCell(index);
        return cell == null ? null : formatter.formatCellValue(cell).trim();
    }

    private Integer getInteger(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            return cell == null ? null : (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    private Double getDouble(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            return cell == null ? null : cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate getData(Cell cell) {
        try {
            if (cell == null) return null;
            return cell.getLocalDateTimeCellValue().toLocalDate();
        } catch (Exception e) {
            return null;
        }
    }

    private InputStream lerDoS3(String bucket, String chave) {
        S3Client s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                .build();
        return s3.getObject(
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(chave)
                        .build()
        );
    }
}