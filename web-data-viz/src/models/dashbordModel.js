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

// Tipos de gasto turístico por valor
function buscarEventosPorTipo() {
    var instrucaoSql = `
        SELECT tipo AS tipoEvento, valor AS totalEventos
        FROM gasto
        ORDER BY valor DESC
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

// Gasto médio do turista — soma dos valores por categoria extraídos do Excel
function buscarMediaPublicoEvento() {
    var instrucaoSql = `
        SELECT ROUND(SUM(valor)) AS gastoMedio
        FROM gasto
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

// Últimos 6 meses com público de eventos cadastrados
function buscarHistoricoVisitas() {
    var instrucaoSql = `
        SELECT ano, numeroMes, totalVisitas FROM (
            SELECT
                YEAR(dtInicial) AS ano,
                MONTH(dtInicial) AS numeroMes,
                SUM(publicoEsperado) AS totalVisitas
            FROM eventos
            WHERE dtInicial IS NOT NULL
            GROUP BY YEAR(dtInicial), MONTH(dtInicial)
            ORDER BY ano DESC, numeroMes DESC
            LIMIT 6
        ) AS sub
        ORDER BY ano ASC, numeroMes ASC
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Visitas por estado — usa chegada_localidade (chegadas internacionais por UF)
function buscarVisitasPorEstado() {
    var instrucaoSql = `
        SELECT cl.localidade AS estado, cl.qtdChegadaLocalidade AS totalVisitas
        FROM chegada_localidade cl
        JOIN chegada c ON c.idChegada = cl.fkChegada
        WHERE c.ano = (SELECT MAX(ano) FROM chegada)
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
