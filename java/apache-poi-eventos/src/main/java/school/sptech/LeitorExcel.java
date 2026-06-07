package school.sptech;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeitorExcel {
    private final DataFormatter formatter = new DataFormatter();

    private static final Map<String, String> IBGE_ESTADOS = new HashMap<>();
    static {
        IBGE_ESTADOS.put("11", "Rondônia");
        IBGE_ESTADOS.put("12", "Acre");
        IBGE_ESTADOS.put("13", "Amazonas");
        IBGE_ESTADOS.put("14", "Roraima");
        IBGE_ESTADOS.put("15", "Pará");
        IBGE_ESTADOS.put("16", "Amapá");
        IBGE_ESTADOS.put("17", "Tocantins");
        IBGE_ESTADOS.put("21", "Maranhão");
        IBGE_ESTADOS.put("22", "Piauí");
        IBGE_ESTADOS.put("23", "Ceará");
        IBGE_ESTADOS.put("24", "Rio Grande do Norte");
        IBGE_ESTADOS.put("25", "Paraíba");
        IBGE_ESTADOS.put("26", "Pernambuco");
        IBGE_ESTADOS.put("27", "Alagoas");
        IBGE_ESTADOS.put("28", "Sergipe");
        IBGE_ESTADOS.put("29", "Bahia");
        IBGE_ESTADOS.put("31", "Minas Gerais");
        IBGE_ESTADOS.put("32", "Espírito Santo");
        IBGE_ESTADOS.put("33", "Rio de Janeiro");
        IBGE_ESTADOS.put("35", "São Paulo");
        IBGE_ESTADOS.put("41", "Paraná");
        IBGE_ESTADOS.put("42", "Santa Catarina");
        IBGE_ESTADOS.put("43", "Rio Grande do Sul");
        IBGE_ESTADOS.put("50", "Mato Grosso do Sul");
        IBGE_ESTADOS.put("51", "Mato Grosso");
        IBGE_ESTADOS.put("52", "Goiás");
        IBGE_ESTADOS.put("53", "Distrito Federal");
    }

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
                String estado = getEstadoPorGeocodigo(row, 32);
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

                if (estado == null || estado.isBlank()) {
                    warns.add("Estado não identificado (coluna 2 vazia) — evento não contabilizado no mapa");
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
                        estado,
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
        MotivoDAO motivoDAO = new MotivoDAO();

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
                String tipo = getString(row, 1);
                Double valorGasto = getDouble(row, 3);

                if (valorGasto != null && tipo != null && !tipo.isBlank()) {
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

            for (int i = 9; i <= 11; i++) {

                Row row = sheet.getRow(i);
                String tipo = getString(row, 1);
                Integer porcentagem = getInteger(row, 11);

                if (tipo != null && porcentagem != null) {
                    Motivo motivo = new Motivo(porcentagem, tipo);
                    motivoDAO.salvar(motivo);
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

            // Anos estão na linha 9, colunas 3 (2019) e 5 (2020)
            int[] colunasDados = {3, 5};

            for (int cIdx : colunasDados) {
                Row rowAno = sheet.getRow(9);
                Integer ano = getInteger(rowAno, cIdx);

                System.out.println("DEBUG ano lido: " + ano + " | cIdx=" + cIdx);

                if (ano != null) {
                    int idChegada = chegadasDAO.salvar(new Chegadas(ano));

                    // Estados: linhas 12-28 — coluna 0 = nome do estado
                    for (int j = 12; j <= 28; j++) {
                        Row row = sheet.getRow(j);
                        String localidade = getString(row, 0);
                        Integer qtd = getInteger(row, cIdx);
                        System.out.println("DEBUG estado j=" + j + " localidade=" + localidade + " qtd=" + qtd);

                        if (qtd != null && localidade != null && !localidade.isBlank()) {
                            chegadasLocalidadeDAO.salvar(
                                    new ChegadasLocalidade(ano, qtd, localidade, idChegada)
                            );
                        }
                    }

                    // Meses: linhas 46-57 — coluna 0 = nome do mês
                    for (int j = 46; j <= 57; j++) {
                        Row row = sheet.getRow(j);
                        String mes = getString(row, 0);
                        Integer qtdMes = getInteger(row, cIdx);
                        System.out.println("DEBUG mes j=" + j + " mes=" + mes + " qtdMes=" + qtdMes);

                        if (qtdMes != null && mes != null && !mes.isBlank()) {
                            chegadasMesDAO.salvar(
                                    new ChegadasMes(ano, qtdMes, mes, idChegada)
                            );
                        }
                    }
                }
            }

            logDao.salvar("INFO", "Chegadas de turistas processadas");

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao processar chegadas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getEstadoPorGeocodigo(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell == null) return null;
            long codigo = (long) cell.getNumericCellValue();
            String codigoStr = String.valueOf(codigo);
            if (codigoStr.length() < 2) return null;
            return IBGE_ESTADOS.get(codigoStr.substring(0, 2));
        } catch (Exception e) {
            return null;
        }
    }

    private String getString(Row row, int index) {
        Cell cell = row.getCell(index);
        return cell == null ? null : formatter.formatCellValue(cell).trim();
    }

    private Integer getInteger(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell == null) return null;
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            }
            String val = formatter.formatCellValue(cell).replaceAll("[^\\d-]", "").trim();
            return val.isEmpty() ? null : Integer.parseInt(val);
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