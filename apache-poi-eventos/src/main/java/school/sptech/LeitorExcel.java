package school.sptech;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.getDouble;

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

                LocalDate dtInicial =
                        getData(row.getCell(9));

                LocalDate dtTermino =
                        getData(row.getCell(10));

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


    public void extrairDemandaTuristica() {

        LogDao logDao = new LogDao();

        ChegadasDAO chegadasDAO =
                new ChegadasDAO();

        GastoDAO gastoDAO =
                new GastoDAO();

        PermanenciaDAO permanenciaDAO =
                new PermanenciaDAO();

        LocalizacaoDAO localizacaoDAO =
                new LocalizacaoDAO();

        PacotesDAO pacotesDAO =
                new PacotesDAO();

        try (

                InputStream arquivo = lerDoS3(
                        AmbienteConfig.BUCKET,
                        AmbienteConfig.DEMANDA
                );

                Workbook workbook =
                        new XSSFWorkbook(arquivo)

        ) {

            Sheet sheet = workbook.getSheet(
                    "DEMANDA-SINTESE BRASIL_4.1"
            );

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                String paisOrigem =
                        getString(row, 1);

                String viaAcesso =
                        getString(row, 3);

                LocalDate dataChegada =
                        getData(row.getCell(9));

                Integer qtdChegadas =
                        getInteger(row, 13);

                Integer qtdChegadasMes =
                        getInteger(row, 18);

                Chegadas chegada = new Chegadas(
                        paisOrigem,
                        viaAcesso,
                        qtdChegadas,
                        dataChegada,
                        qtdChegadasMes,
                        1
                );

                chegadasDAO.salvar(chegada);


                Double valorGasto =
                        getDouble(row, 20);

                if (valorGasto != null) {

                    Gasto gasto = new Gasto(
                            "Turista",
                            valorGasto
                    );

                    gastoDAO.salvar(gasto);
                }


                Integer qtdDias =
                        getInteger(row, 21);

                if (qtdDias != null) {

                    Permanencia permanencia =
                            new Permanencia(
                                    "Média",
                                    qtdDias
                            );

                    permanenciaDAO.salvar(
                            permanencia
                    );
                }


                String uf =
                        getString(row, 22);

                String cidade =
                        getString(row, 23);

                if (uf != null && cidade != null) {

                    Localizacao localizacao =
                            new Localizacao(
                                    uf,
                                    cidade
                            );

                    localizacaoDAO.salvar(
                            localizacao
                    );
                }


                Integer qtdPacotes =
                        getInteger(row, 24);

                if (qtdPacotes != null) {

                    Pacotes pacotes =
                            new Pacotes(
                                    "Pacote Turístico",
                                    qtdPacotes,
                                    1,
                                    1,
                                    1,
                                    LocalDate.now(),
                                    LocalDate.now()
                            );

                    pacotesDAO.salvar(
                            pacotes
                    );
                }
            }

            logDao.salvar(
                    "INFO",
                    "Demanda turística processada"
            );

        } catch (Exception e) {

            logDao.salvar(
                    "ERROR",
                    "Erro ao processar demanda: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    private String getString(Row row, int index) {

        Cell cell = row.getCell(index);

        return cell == null
                ? null
                : formatter.formatCellValue(cell)
                .trim();
    }

    private Integer getInteger(Row row, int index) {

        try {

            Cell cell = row.getCell(index);

            return cell == null
                    ? null
                    : (int) cell.getNumericCellValue();

        } catch (Exception e) {

            return null;
        }
    }

    private Double getDouble(Row row, int index) {

        try {

            Cell cell = row.getCell(index);

            return cell == null
                    ? null
                    : cell.getNumericCellValue();

        } catch (Exception e) {

            return null;
        }
    }

    private LocalDate getData(Cell cell) {

        try {

            if (cell == null) return null;

            return cell.getLocalDateTimeCellValue()
                    .toLocalDate();

        } catch (Exception e) {

            return null;
        }
    }


    private InputStream lerDoS3(
            String bucket,
            String chave
    ) {

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