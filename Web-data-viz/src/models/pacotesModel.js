var database = require("../database/config");

function publicarPacote(nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio) {
    console.log("ACESSEI O PACOTES MODEL \n \n\t\t >> Se aqui der erro de 'Error: connect ECONNREFUSED',\n \t\t >> verifique suas credenciais de acesso ao banco\n \t\t >> e se o servidor de seu BD está rodando corretamente. \n\n function cadastrar():", nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio);
    var instrucaoSql = `
INSERT INTO pacote (nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio) VALUES ('${nomePacote}', '${categoria}', '${destino}', '${duracao}', '${preco}', '${descricao}', '${vagas}', '${dataInicio}');
`;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function carregarPacotes() {

    var instrucaoSql = `SELECT 
        * FROM pacote`;

    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function atualizarPacote(
    
    idPacote,
    nomePacote,
    categoria,
    destino,
    duracao,
    preco,
    descricao,
    vagas,
    dataInicio

) {

    console.log(

        "ACESSEI O PACOTES MODEL - atualizarPacote()"

    );

    var instrucaoSql = `

        UPDATE pacote
        SET

            nomePacote = '${nomePacote}',
            categoria = '${categoria}',
            destino = '${destino}',
            duracao = '${duracao}',
            preco = '${preco}',
            descricao = '${descricao}',
            vagas = '${vagas}',
            dataInicio = '${dataInicio}'

        WHERE idPacote = ${idPacote};

    `;

    console.log("Executando a instrução SQL: \n" + instrucaoSql);

    return database.executar(instrucaoSql);

}

function deletarPacote(idPacote) {

    console.log("ACESSEI O PACOTES MODEL - deletarPacote()");

    return database.executar(`DELETE FROM vendas WHERE idPacote = ${idPacote}`)
        .then(function () {
            return database.executar(`DELETE FROM pacotes_agencias WHERE idPacote = ${idPacote}`);
        })
        .then(function () {
            var instrucaoSql = `DELETE FROM pacote WHERE idPacote = ${idPacote};`;
            console.log("Executando SQL: \n" + instrucaoSql);
            return database.executar(instrucaoSql);
        });

}

module.exports = {
    publicarPacote,
    carregarPacotes,
    atualizarPacote,
    deletarPacote
}
