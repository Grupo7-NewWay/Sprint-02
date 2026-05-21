package school.sptech;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Chegadas> extrairChegadas(String caminhoArquivo) {

        List<Chegadas> chegadasExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        ChegadasDAO ChegadasDAO = new ChegadasDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String paisOrigem = getString(row, 1);
                String viaAcesso = getString(row, 3);
                Integer qtdChegadas = getInteger(row, 13);
                Integer qtdChegadasMes = getInteger(row, 18);
                Integer fk_chegada_localizacao = getInteger(row, 18);

                if (paisOrigem == null || paisOrigem.isBlank()) {
                    warns.add("País de origem inválido");
                }

                if (viaAcesso == null || viaAcesso.isBlank()) {
                    warns.add("Via de acesso inválido");
                }

                if (qtdChegadas == null || qtdChegadas <= 0) {
                    warns.add("Quantidade de chegadas inválido");
                }

                if (qtdChegadasMes == null || qtdChegadasMes <= 0) {
                    warns.add("Quantidade de chegadas mensal inválido");
                }

                LocalDate dataChegada = getData(row.getCell(9), linha, "data chegadas", warns);

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Chegadas chegadas = new Chegadas(
                        paisOrigem,
                        viaAcesso,
                        qtdChegadas,
                        dataChegada,
                        qtdChegadasMes,
                        fk_chegada_localizacao
                );

                chegadasExtraidos.add(chegadas);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    ChegadasDAO.salvar(chegadas);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    ChegadasDAO.salvar(chegadas);
                }
            }

            System.out.println("Leitura finalizada");

            return chegadasExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return chegadasExtraidos;
        }
    }

    public List<Fonte> extrairFonte(String caminhoArquivo) {

        List<Fonte> fonteExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        FonteDAO FonteDAO = new FonteDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String tipo = getString(row, 1);
                Integer porcentagem = getInteger(row, 3);

                if (tipo == null || tipo.isBlank()) {
                    warns.add("Tipo de fonte inválido");
                }

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de fonte inválido");
                }

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Fonte fonte = new Fonte(
                        tipo,
                        porcentagem
                );

                fonteExtraidos.add(fonte);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    FonteDAO.salvar(fonte);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    FonteDAO.salvar(fonte);
                }
            }

            System.out.println("Leitura finalizada");

            return fonteExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return fonteExtraidos;
        }
    }

    public List<Gasto> extrairGasto(String caminhoArquivo) {

        List<Gasto> gastoExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        GastoDAO GastoDAO = new GastoDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String tipo = getString(row, 1);
                Integer porcentagem = getInteger(row, 3);

                if (tipo == null || tipo.isBlank()) {
                    warns.add("Tipo de gasto inválido");
                }

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de gasto inválido");
                }

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Gasto gasto = new Gasto(
                        tipo,
                        porcentagem
                );

                gastoExtraidos.add(gasto);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    GastoDAO.salvar(gasto);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    GastoDAO.salvar(gasto);
                }
            }

            System.out.println("Leitura finalizada");

            return gastoExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return gastoExtraidos;
        }
    }

    public List<Grupo> extrairGrupo(String caminhoArquivo) {

        List<Grupo> grupoExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        GrupoDAO grupoDAO = new GrupoDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String tipo = getString(row, 1);
                Integer porcentagem = getInteger(row, 3);

                if (tipo == null || tipo.isBlank()) {
                    warns.add("Tipo de grupo inválido");
                }

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de grupo inválido");
                }

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Grupo grupo = new Grupo(
                        tipo,
                        porcentagem
                );

                grupoExtraidos.add(grupo);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    grupoDAO.salvar(grupo);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    grupoDAO.salvar(grupo);
                }
            }

            System.out.println("Leitura finalizada");

            return grupoExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return grupoExtraidos;
        }
    }

    public List<GrupoIdade> extrairGrupoIdade(String caminhoArquivo) {

        List<GrupoIdade> grupoIdadeExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        GrupoIdadeDAO grupoIdadeDAO = new GrupoIdadeDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String tipo = getString(row, 1);
                Integer porcentagem = getInteger(row, 3);

                if (tipo == null || tipo.isBlank()) {
                    warns.add("Tipo de grupo idade inválido");
                }

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de grupo idade inválido");
                }

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                GrupoIdade grupoIdade = new GrupoIdade(
                        tipo,
                        porcentagem
                );

                grupoIdadeExtraidos.add(grupoIdade);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    grupoIdadeDAO.salvar(grupoIdade);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    grupoIdadeDAO.salvar(grupoIdade);
                }
            }

            System.out.println("Leitura finalizada");

            return grupoIdadeExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return grupoIdadeExtraidos;
        }
    }

    public List<Hospedagem> extrairHospedagem(String caminhoArquivo) {

        List<Hospedagem> hospedagemExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        HospedagemDAO hospedagemDAO = new HospedagemDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String tipo = getString(row, 1);
                Integer porcentagem = getInteger(row, 3);

                if (tipo == null || tipo.isBlank()) {
                    warns.add("Tipo de hospedagem inválido");
                }

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de hospedagem inválida");
                }

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Hospedagem hospedagem = new Hospedagem(
                        tipo,
                        porcentagem
                );

                hospedagemExtraidos.add(hospedagem);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    hospedagemDAO.salvar(hospedagem);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    hospedagemDAO.salvar(hospedagem);
                }
            }

            System.out.println("Leitura finalizada");

            return hospedagemExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return hospedagemExtraidos;
        }
    }

    public List<Lazer> extrairLazer(String caminhoArquivo) {

        List<Lazer> lazerExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        LazerDAO lazerDAO = new LazerDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String tipoLazer = getString(row, 1);
                Integer porcentagem = getInteger(row, 3);
                Integer fk_lazer_motivo = getInteger(row, 3);

                if (tipoLazer == null || tipoLazer.isBlank()) {
                    warns.add("Tipo de lazer inválido");
                }

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de lazer inválida");
                }

                if (fk_lazer_motivo == null || fk_lazer_motivo <= 0) {
                    warns.add("Fk de lazer inválida");
                }

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Lazer lazer = new Lazer(
                        tipoLazer,
                        porcentagem,
                        fk_lazer_motivo
                );

                lazerExtraidos.add(lazer);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    lazerDAO.salvar(lazer);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    lazerDAO.salvar(lazer);
                }
            }

            System.out.println("Leitura finalizada");

            return lazerExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return lazerExtraidos;
        }
    }

    public List<Localizacao> extrairLocalizacao(String caminhoArquivo) {

        List<Localizacao> localizacaoExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String uf = getString(row, 1);
                String cidade = getString(row, 3);

                if (uf == null || uf.isBlank()) {
                    warns.add("Tipo de UF inválido");
                }

                if (cidade == null || cidade.isBlank()) {
                    warns.add("Tipo de cidade inválido");
                }


                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Localizacao localizacao = new Localizacao(
                        uf,
                        cidade
                );

                localizacaoExtraidos.add(localizacao);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    localizacaoDAO.salvar(localizacao);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    localizacaoDAO.salvar(localizacao);
                }
            }

            System.out.println("Leitura finalizada");

            return localizacaoExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return localizacaoExtraidos;
        }
    }

    public List<Motivo> extrairMotivo(String caminhoArquivo) {

        List<Motivo> motivoExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        MotivoDAO motivoDAO = new MotivoDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                Integer porcentagem = getInteger(row, 3);
                String tipo = getString(row, 1);

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de motivo inválida");
                }

                if (tipo == null || tipo.isBlank()) {
                    warns.add("Tipo de motivo inválido");
                }


                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Motivo motivo = new Motivo(
                        porcentagem,
                        tipo
                );

                motivoExtraidos.add(motivo);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    motivoDAO.salvar(motivo);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    motivoDAO.salvar(motivo);
                }
            }

            System.out.println("Leitura finalizada");

            return motivoExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return motivoExtraidos;
        }
    }

    public List<Pacotes> extrairPacotes(String caminhoArquivo) {

        List<Pacotes> pacotesExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        PacotesDAO pacotesDAO = new PacotesDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String nomePacote = getString(row, 1);
                Integer qtdDisponivel = getInteger(row, 3);
                Integer fk_pacote_perfil = getInteger(row, 3);
                Integer fk_pacote_localizacao = getInteger(row, 3);
                Integer fk_pacote_evento = getInteger(row, 3);

                if (nomePacote == null || nomePacote.isBlank()) {
                    warns.add("Nome do pacote inválido");
                }

                if (qtdDisponivel == null || qtdDisponivel <= 0) {
                    warns.add("Quantidade de pacote inválida");
                }

                if (fk_pacote_perfil == null || fk_pacote_perfil <= 0) {
                    warns.add("Fk_pacote_perfil inválida");
                }

                if (fk_pacote_localizacao == null || fk_pacote_localizacao <= 0) {
                    warns.add("Fk_pacote_localizacao inválida");
                }

                if (fk_pacote_evento == null || fk_pacote_evento <= 0) {
                    warns.add("Fk_pacote_evento inválida");
                }


                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                LocalDate dataCadastro = getData(row.getCell(9), linha, "data cadastro", warns);
                LocalDate dataAtualizacao = getData(row.getCell(9), linha, "data atualizacao", warns);

                Pacotes pacotes = new Pacotes(
                        nomePacote,
                        qtdDisponivel,
                        fk_pacote_perfil,
                        fk_pacote_localizacao,
                        fk_pacote_evento,
                        dataCadastro,
                        dataAtualizacao
                );

                pacotesExtraidos.add(pacotes);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    pacotesDAO.salvar(pacotes);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    pacotesDAO.salvar(pacotes);
                }
            }

            System.out.println("Leitura finalizada");

            return pacotesExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return pacotesExtraidos;
        }
    }

    public List<Permanencia> extrairPermanencia(String caminhoArquivo) {

        List<Permanencia> permanenciaExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        PermanenciaDAO permanenciaDAO = new PermanenciaDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String tipo = getString(row, 1);
                Integer porcentagem = getInteger(row, 3);

                if (tipo == null || tipo.isBlank()) {
                    warns.add("Tipo de permanência inválido");
                }

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de permanência inválida");
                }

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                Permanencia permanencia = new Permanencia(
                        tipo,
                        porcentagem
                );

                permanenciaExtraidos.add(permanencia);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    permanenciaDAO.salvar(permanencia);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    permanenciaDAO.salvar(permanencia);
                }
            }

            System.out.println("Leitura finalizada");

            return permanenciaExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return permanenciaExtraidos;
        }
    }

    public List<ServicoAgencia> extrairServicoAgencia(String caminhoArquivo) {

        List<ServicoAgencia> servicoAgenciaExtraidos = new ArrayList<>();
        LogDao logDao = new LogDao();
        ServicoAgenciaDAO servicoAgenciaDAO = new ServicoAgenciaDAO();

        System.out.println("Arquivo existe? " + new java.io.File(caminhoArquivo).exists());

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int linha = row.getRowNum();

                List<String> warns = new ArrayList<>();
                boolean erroCritico = false;

                String tipo = getString(row, 1);
                Integer porcentagem = getInteger(row, 3);

                if (tipo == null || tipo.isBlank()) {
                    warns.add("Tipo de serviço agência inválido");
                }

                if (porcentagem == null || porcentagem <= 0) {
                    warns.add("Porcentagem de serviço agência inválida");
                }

                if (erroCritico) {
                    logDao.salvar(
                            "ERROR",
                            "Linha " + linha + " ignorada (erro crítico: campo vazio)"
                    );
                    continue;
                }

                ServicoAgencia servicoAgencia = new ServicoAgencia(
                        tipo,
                        porcentagem
                );

                servicoAgenciaExtraidos.add(servicoAgencia);

                if (!warns.isEmpty()) {
                    logDao.salvar(
                            "WARN",
                            "Linha " + linha + " com avisos: " + String.join(" | ", warns)
                    );
                    servicoAgenciaDAO.salvar(servicoAgencia);
                } else {
                    logDao.salvar(
                            "INFO",
                            "Linha " + linha + " processada com sucesso"
                    );
                    servicoAgenciaDAO.salvar(servicoAgencia);
                }
            }

            System.out.println("Leitura finalizada");

            return servicoAgenciaExtraidos;

        } catch (Exception e) {
            logDao.salvar("ERROR", "Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
            return servicoAgenciaExtraidos;
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