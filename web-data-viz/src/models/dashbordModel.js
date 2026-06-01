var database = require("../database/config");

// Top localidades por volume de chegadas
function buscarTopRegiao() {
    var instrucaoSql = `
        SELECT localidade AS municipio,
               qtdChegadaLocalidade AS totalEventos,
               qtdChegadaLocalidade AS totalPublicoEsperado
        FROM chegada_localidade
        ORDER BY qtdChegadaLocalidade DESC
        LIMIT 10
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Tipos de gasto turístico (percentual)
function buscarEventosPorTipo() {
    var instrucaoSql = `
        SELECT tipo AS tipoEvento, porcentagem AS totalEventos
        FROM gasto
        ORDER BY porcentagem DESC
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Próximos eventos cadastrados pela agência
function buscarEventosProximos() {
    var instrucaoSql = `
        SELECT nomeEvento,
               localidade AS municipio,
               dataEvento AS dtInicial,
               dataEvento AS dtTermino
        FROM evento
        WHERE dataEvento >= CURDATE()
        ORDER BY dataEvento ASC
        LIMIT 10
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Média de dias de permanência do turista
function buscarDuracaoMediaEvento() {
    var instrucaoSql = `
        SELECT ROUND(AVG(qtdDias)) AS duracaoMedia
        FROM permanencia
    `;
    console.log("Executando a instrucao SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Média mensal de chegadas (usado como "público médio")
function buscarMediaPublicoEvento() {
    var instrucaoSql = `
        SELECT ROUND(AVG(qtdChegadaMes)) AS mediaPublico
        FROM chegada_mes
    `;
    console.log("Executando a instrucao SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Principal motivo de viagem (maior porcentagem)
function buscarMotivacaoPrincipal() {
    var instrucaoSql = `
        SELECT tipo AS motivacao
        FROM motivo
        ORDER BY porcentagem DESC
        LIMIT 1
    `;
    console.log("Executando a instrucao SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Total de chegadas no ano mais recente disponível
function buscarTotalMensalVisitas() {
    var instrucaoSql = `
        SELECT SUM(cm.qtdChegadaMes) AS totalVisitas
        FROM chegada_mes cm
        JOIN chegada c ON c.idChegada = cm.fkChegada
        WHERE c.ano = (SELECT MAX(ano) FROM chegada)
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Últimos 6 registros mensais disponíveis no banco
function buscarHistoricoVisitas() {
    var instrucaoSql = `
        SELECT ano, numeroMes, totalVisitas FROM (
            SELECT
                c.ano,
                CASE cm.mes
                    WHEN 'Janeiro'   THEN 1
                    WHEN 'Fevereiro' THEN 2
                    WHEN 'Março'     THEN 3
                    WHEN 'Abril'     THEN 4
                    WHEN 'Maio'      THEN 5
                    WHEN 'Junho'     THEN 6
                    WHEN 'Julho'     THEN 7
                    WHEN 'Agosto'    THEN 8
                    WHEN 'Setembro'  THEN 9
                    WHEN 'Outubro'   THEN 10
                    WHEN 'Novembro'  THEN 11
                    WHEN 'Dezembro'  THEN 12
                END AS numeroMes,
                cm.qtdChegadaMes AS totalVisitas
            FROM chegada_mes cm
            JOIN chegada c ON c.idChegada = cm.fkChegada
            ORDER BY c.ano DESC, numeroMes DESC
            LIMIT 6
        ) AS sub
        ORDER BY ano ASC, numeroMes ASC
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Visitas por UF — usa tabela eventos (populada pelo LeitorExcel)
// Retorna 204 se vazia; o frontend mantém os dados de fallback
function buscarVisitasPorEstado() {
    var instrucaoSql = `
        SELECT uf, SUM(publicoEsperado) AS totalVisitas
        FROM eventos
        GROUP BY uf
        ORDER BY totalVisitas DESC
    `;
    console.log("Executando a instrucao SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

module.exports = {
    buscarTopRegiao,
    buscarEventosPorTipo,
    buscarEventosProximos,
    buscarTotalMensalVisitas,
    buscarHistoricoVisitas,
    buscarVisitasPorEstado,
    buscarDuracaoMediaEvento,
    buscarMediaPublicoEvento,
    buscarMotivacaoPrincipal
};
