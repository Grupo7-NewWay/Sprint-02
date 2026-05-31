var database = require("../database/config");

function listarTodos() {
    var instrucaoSql = `SELECT * FROM pacote ORDER BY idPacote`;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarPorId(idPacote) {
    var instrucaoSql = `SELECT * FROM pacote WHERE idPacote = ${idPacote}`;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function cadastrar(nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio) {
    var instrucaoSql = `INSERT INTO pacote (nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio)
                        VALUES ('${nomePacote}', '${categoria}', '${destino}', ${duracao}, ${preco}, '${descricao}', ${vagas}, '${dataInicio}')`;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

module.exports = {
    listarTodos,
    buscarPorId,
    cadastrar
};
