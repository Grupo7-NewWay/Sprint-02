var database = require("../database/config");

function publicarPacote(nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio) {
    console.log("ACESSEI O PACOTES MODEL \n \n\t\t >> Se aqui der erro de 'Error: connect ECONNREFUSED',\n \t\t >> verifique suas credenciais de acesso ao banco\n \t\t >> e se o servidor de seu BD está rodando corretamente. \n\n function cadastrar():", nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio);
    
    var instrucaoSql = `
        INSERT INTO pacote (nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio) VALUES ('${nomePacote}',  '${categoria}', '${destino}', '${duracao}', '${preco}', '${descricao}', '${vagas}', '${dataInicio}');
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

module.exports = {
  publicarPacote
}