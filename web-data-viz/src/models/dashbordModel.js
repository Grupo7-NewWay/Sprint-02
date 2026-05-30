var database = require("../database/config");

function buscarTopRegiao() {
    var instrucaoSql = `
        SELECT municipio, COUNT(*) as totalEventos, SUM(publicoEsperado) as totalPublicoEsperado
        FROM eventos
        GROUP BY municipio
        ORDER BY totalEventos DESC
        LIMIT 10
    `;

    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarEventosPorTipo() {
    var instrucaoSql = `
        SELECT tipoEvento, COUNT(*) as totalEventos
        FROM eventos
        GROUP BY tipoEvento
        ORDER BY totalEventos DESC
    `;

    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarEventosProximos() {
    var instrucaoSql = `
        SELECT nomeEvento, municipio, dtInicial, dtTermino, tipoEvento, publicoEsperado
        FROM eventos
        WHERE dtInicial >= CURDATE()
        ORDER BY dtInicial ASC
        LIMIT 10
    `;

    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarDuracaoMediaEvento() {
    var instrucaoSql = `
        SELECT ROUND(AVG(DATEDIFF(dtTermino, dtInicial) + 1)) AS duracaoMedia
        FROM eventos
    `;
    console.log("Executando a instrucao SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarMediaPublicoEvento() {
    var instrucaoSql = `
        SELECT ROUND(AVG(publicoEsperado)) AS mediaPublico
        FROM eventos
    `;
    console.log("Executando a instrucao SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

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

function buscarTotalMensalVisitas() {
    var instrucaoSql = `
        SELECT SUM(publicoEsperado) as totalVisitas
        FROM eventos
        WHERE MONTH(dtInicial) = MONTH(CURDATE())
          AND YEAR(dtInicial) = YEAR(CURDATE())
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarHistoricoVisitas() {
    var instrucaoSql = `
        SELECT
            YEAR(dtInicial)       AS ano,
            MONTH(dtInicial)      AS numeroMes,
            SUM(publicoEsperado)  AS totalVisitas
        FROM chegada
        WHERE dtInicial >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
        GROUP BY YEAR(dtInicial), MONTH(dtInicial)
        ORDER BY YEAR(dtInicial), MONTH(dtInicial)
    `;

    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

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
}
