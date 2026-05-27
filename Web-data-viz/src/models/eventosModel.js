var database = require("../database/config");

function publicarEvento(

    nome,
    localidade,
    data

) {

    var instrucaoSql = `

        INSERT INTO eventos (

            nomeEvento,
            municipio,
            uf,
            dtInicial,
            dtTermino,
            tipoEvento,
            publicoEsperado

        )

        VALUES (

            '${nome}',
            '${localidade}',
            '',
            '${data}',
            '${data}',
            '',
            0

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
            municipio AS localidade,
            dtInicial AS data
        FROM eventos;
    `;

    console.log(instrucaoSql);

    return database.executar(instrucaoSql);

}

function atualizarEvento(id, nome, localidade, data) {

    var instrucaoSql = `
        UPDATE eventos
        SET
            nomeEvento = '${nome}',
            municipio = '${localidade}',
            dtInicial = '${data}',
            dtTermino = '${data}'
        WHERE idEvento = ${id};
    `;

    console.log("Executando SQL: \n" + instrucaoSql);

    return database.executar(instrucaoSql);
}

function deletarEvento(id) {

    var instrucaoSql = `
        DELETE FROM eventos
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