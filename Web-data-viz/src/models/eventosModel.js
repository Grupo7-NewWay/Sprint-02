var database = require("../database/config");

function publicarEvento(

    nome,
    localidade,
    data

) {

    var instrucaoSql = `

        INSERT INTO evento (

            nome,
            localidade,
            data

        )

        VALUES (

            '${nome}',
            '${localidade}',
            '${data}'

        );

    `;

    console.log(instrucaoSql);

    return database.executar(instrucaoSql);

}

function carregarEventos() {

    var instrucaoSql = `
        SELECT
            idEvento AS id,
            nome,
            localidade,
            data
        FROM evento;
    `;

    console.log(instrucaoSql);

    return database.executar(instrucaoSql);

}

function atualizarEvento(id, nome, localidade, data) {

    var instrucaoSql = `
        UPDATE evento
        SET
            nome = '${nome}',
            localidade = '${localidade}',
            data = '${data}'
        WHERE idEvento = ${id};
    `;

    console.log("Executando SQL: \n" + instrucaoSql);

    return database.executar(instrucaoSql);
}

function deletarEvento(id) {

    var instrucaoSql = `
        DELETE FROM evento
        WHERE idEvento = ${id};
    `;

    console.log("Executando SQL:");
    console.log(instrucaoSql);

    return database.executar(instrucaoSql);
}

module.exports = {

    publicarEvento,
    carregarEventos,
    atualizarEvento,
    deletarEvento

};