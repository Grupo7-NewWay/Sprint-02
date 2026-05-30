var database = require("../database/config");

function publicarEvento(

    nome,
    localidade,
    data,
    fkAgencia

) {

    var instrucaoSql = `

        INSERT INTO evento (

            nomeEvento,
            localidade,
            dataEvento,
            fkAgencia

        )

        VALUES (

            '${nome}',
            '${localidade}',
            '${data}',
            '${fkAgencia}'

        );

    `;

    console.log(instrucaoSql);

    return database.executar(instrucaoSql);

}

function carregarEventos() {

    var instrucaoSql = `
        SELECT
            idEvento AS id,
            nomeEvento AS nome,
            localidade AS localidade,
            dataEvento AS data
        FROM evento;
    `;

    console.log(instrucaoSql);

    return database.executar(instrucaoSql);

}

function atualizarEvento(id, nome, localidade, data) {

    var instrucaoSql = `
        UPDATE evento
        SET
            nomeEvento = '${nome}',
            localidade = '${localidade}',
            dataEvento = '${data}'
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