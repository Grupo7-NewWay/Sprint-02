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

function buscarGastoMedio() {
    var instrucaoSql = `
        SELECT ROUND(AVG(v.valor / v.qtdVenda)) as gastoMedio
        FROM vendas v
    `;

    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarPermanenciaMedia() {
    var instrucaoSql = `
        SELECT ROUND(AVG(pac.duracao)) as permanenciaMedia
        FROM vendas v
        JOIN pacote pac ON v.idPacote = pac.idPacote
    `;

    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarTaxaConversao() {
    var instrucaoSql = `
        SELECT ROUND((SUM(v.qtdVenda) / SUM(p.vagas)) * 100, 1) as taxaConversao
        FROM vendas v
        JOIN pacote p ON v.idPacote = p.idPacote
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
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

module.exports = {
    buscarTopRegiao,
    buscarEventosPorTipo,
    buscarEventosProximos,
    buscarPermanenciaMedia,
    buscarGastoMedio,
    buscarTaxaConversao,
    buscarTotalMensalVisitas
}
