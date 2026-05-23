var database = require("../database/config")

function autenticar(email, senha) {
    console.log("ACESSEI O USUARIO MODEL \n \n\t\t >> Se aqui der erro de 'Error: connect ECONNREFUSED',\n \t\t >> verifique suas credenciais de acesso ao banco\n \t\t >> e se o servidor de seu BD está rodando corretamente. \n\n function entrar(): ", email, senha)
    var instrucaoSql = `
        SELECT idAgencia, nomeAgencia, email FROM agencia WHERE email = '${email}' AND senha = '${senha}';
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

// Coloque os mesmos parâmetros aqui. Vá para a var instrucaoSql
function cadastrar(nomeAgencia, email, senha, cnpj, telefone) {
    console.log("ACESSEI O USUARIO MODEL \n \n\t\t >> Se aqui der erro de 'Error: connect ECONNREFUSED',\n \t\t >> verifique suas credenciais de acesso ao banco\n \t\t >> e se o servidor de seu BD está rodando corretamente. \n\n function cadastrar():", nomeAgencia, email, senha, cnpj, telefone);
    
    // Insira exatamente a query do banco aqui, lembrando da nomenclatura exata nos valores
    //  e na ordem de inserção dos dados.
    var instrucaoSql = `
        INSERT INTO agencia (nomeAgencia, cnpj, telefone, email, senha) VALUES ('${nomeAgencia}',  '${cnpj}', '${telefone}', '${email}', '${senha}');
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarPerfil(idAgencia) {
    var instrucaoSql = `
        SELECT
            a.idAgencia,
            a.nomeAgencia,
            a.email,
            a.telefone,
            a.dataNascimento,
            e.cep,
            e.logradouro,
            e.numero,
            e.complemento,
            e.bairro,
            e.cidade,
            e.estado
        FROM agencia a
        LEFT JOIN endereco e ON e.idAgencia = a.idAgencia
        WHERE a.idAgencia = ${parseInt(idAgencia)}
        LIMIT 1
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function atualizarAgencia(idAgencia, nomeAgencia, email, telefone, dataNascimento) {
    var id = parseInt(idAgencia);
    var nascFormatado = dataNascimento ? `'${dataNascimento}'` : 'NULL';

    var instrucaoSql = `
        UPDATE agencia
        SET nomeAgencia = '${nomeAgencia}',
            email       = '${email}',
            telefone    = '${telefone}',
            dataNascimento = ${nascFormatado}
        WHERE idAgencia = ${id}
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);

    return database.executar(instrucaoSql).catch(function (erro) {
        if (erro.sqlMessage && erro.sqlMessage.includes('dataNascimento')) {
            var semNasc = `
                UPDATE agencia
                SET nomeAgencia = '${nomeAgencia}',
                    email       = '${email}',
                    telefone    = '${telefone}'
                WHERE idAgencia = ${id}
            `;
            console.log("Coluna dataNascimento ausente, retentando sem ela.");
            return database.executar(semNasc);
        }
        throw erro;
    });
}

function upsertEndereco(idAgencia, cep, logradouro, numero, complemento, bairro, cidade, estado) {
    var id = parseInt(idAgencia);
    var cepLimpo = cep.replace(/\D/g, '').slice(0, 8);

    var updateSql = `
        UPDATE endereco
        SET cep         = '${cepLimpo}',
            logradouro  = '${logradouro}',
            numero      = '${numero}',
            complemento = '${complemento}',
            bairro      = '${bairro}',
            cidade      = '${cidade}',
            estado      = '${estado}'
        WHERE idAgencia = ${id}
    `;
    console.log("Executando a instrução SQL: \n" + updateSql);

    return database.executar(updateSql).then(function (resultado) {
        if (resultado.affectedRows === 0) {
            var insertSql = `
                INSERT INTO endereco (idAgencia, cep, logradouro, numero, complemento, bairro, cidade, estado)
                VALUES (${id}, '${cepLimpo}', '${logradouro}', '${numero}', '${complemento}', '${bairro}', '${cidade}', '${estado}')
            `;
            console.log("Nenhuma linha atualizada, inserindo novo endereco.");
            return database.executar(insertSql);
        }
    });
}

function excluir(idAgencia) {
    var id = parseInt(idAgencia);

    return database.executar(`DELETE FROM vendas          WHERE idAgencia = ${id}`)
        .then(function () { return database.executar(`DELETE FROM pacotes_agencias WHERE idAgencia = ${id}`); })
        .then(function () { return database.executar(`DELETE FROM logs             WHERE idAgencia = ${id}`); })
        .then(function () { return database.executar(`DELETE FROM endereco         WHERE idAgencia = ${id}`); })
        .then(function () { return database.executar(`DELETE FROM agencia          WHERE idAgencia = ${id}`); });
}

module.exports = {
    autenticar,
    cadastrar,
    buscarPerfil,
    atualizarAgencia,
    upsertEndereco,
    excluir
};