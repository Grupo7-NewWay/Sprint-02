var database = require("../database/config")

function autenticar(email, senha) {
    console.log("ACESSEI O USUARIO MODEL \n \n\t\t >> Se aqui der erro de 'Error: connect ECONNREFUSED',\n \t\t >> verifique suas credenciais de acesso ao banco\n \t\t >> e se o servidor de seu BD está rodando corretamente. \n\n function entrar(): ", email, senha)
    var instrucaoSql = `
        SELECT idUsuario, nome, email, fkAgencia FROM usuario WHERE email = '${email}' AND senhaHash = '${senha}';
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarAgenciaPorToken(token) {

    var instrucaoSql = `
        SELECT idAgencia
        FROM agencia
        WHERE codigoAcesso = '${token}';
    `;

    console.log("Executando SQL: \n" + instrucaoSql);

    return database.executar(instrucaoSql);
}

// Coloque os mesmos parâmetros aqui. Vá para a var instrucaoSql
function cadastrar(nome, email, senha, telefone, idAgencia) {
    console.log("ACESSEI O USUARIO MODEL \n \n\t\t >> Se aqui der erro de 'Error: connect ECONNREFUSED',\n \t\t >> verifique suas credenciais de acesso ao banco\n \t\t >> e se o servidor de seu BD está rodando corretamente. \n\n function cadastrar():", nome, email, senha, telefone, idAgencia);
    
    // Insira exatamente a query do banco aqui, lembrando da nomenclatura exata nos valores
    //  e na ordem de inserção dos dados.
    var instrucaoSql = `
        INSERT INTO usuario (nome, fkAgencia, telefone, email, senhaHash) VALUES ('${nome}',  '${idAgencia}', '${telefone}', '${email}', '${senha}');
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function buscarPerfil(id) {
    var instrucaoSql = `
        SELECT
            u.idUsuario,
            u.nome,
            u.email,
            u.telefone,
            u.dtNascimento,
            e.cep,
            e.logradouro,
            e.numero,
            e.complemento,
            e.bairro,
            e.cidade,
            e.estado
        FROM usuario u
        LEFT JOIN endereco e ON e.fkUsuario = u.idUsuario
        WHERE u.idUsuario = ${parseInt(id)}
        LIMIT 1
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);
    return database.executar(instrucaoSql);
}

function atualizarPerfil(id, nome, email, telefone, dataNascimento) {
    var id = parseInt(id);
    var nascFormatado = dataNascimento ? `'${dataNascimento}'` : 'NULL';
    
    var instrucaoSql = `
        UPDATE usuario
        SET nome = '${nome}',
            email = '${email}',
            telefone = '${telefone}',
            dtNascimento = ${nascFormatado}
        WHERE idUsuario = ${id}
    `;
    console.log("Executando a instrução SQL: \n" + instrucaoSql);

    return database.executar(instrucaoSql).catch(function (erro) {
        if (erro.sqlMessage && erro.sqlMessage.includes('dataNascimento')) {
            var semNasc = `
                UPDATE usuario
                SET nome = '${nome}',
                    email = '${email}',
                    telefone = '${telefone}'
                WHERE idUsuario = ${id}
            `;
            console.log("Coluna dataNascimento ausente, retentando sem ela.");
            return database.executar(semNasc);
        }
        throw erro;
    });
}

function upsertEndereco(id, cep, logradouro, numero, complemento, bairro, cidade, estado) {
    var id = parseInt(id);
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
        WHERE fkUsuario = ${id}
    `;
    console.log("Executando a instrução SQL: \n" + updateSql);

    return database.executar(updateSql).then(function (resultado) {
        if (resultado.affectedRows === 0) {
            var insertSql = `
                INSERT INTO endereco (fkUsuario, cep, logradouro, numero, complemento, bairro, cidade, estado)
                VALUES (${id}, '${cepLimpo}', '${logradouro}', '${numero}', '${complemento}', '${bairro}', '${cidade}', '${estado}')
            `;
            console.log("Nenhuma linha atualizada, inserindo novo endereco.");
            return database.executar(insertSql);
        }
    });
}

function excluir(id) {

    id = parseInt(id);

    var deleteEndereco = `
        DELETE FROM endereco
        WHERE fkUsuario = ${id};
    `;

    var deleteUsuario = `
        DELETE FROM usuario
        WHERE idUsuario = ${id};
    `;

    console.log("Executando SQL endereço:\n" + deleteEndereco);
    console.log("Executando SQL usuário:\n" + deleteUsuario);

    return database.executar(deleteEndereco)
        .then(() => {
            return database.executar(deleteUsuario);
        });

}

module.exports = {
    autenticar,
    buscarAgenciaPorToken,
    cadastrar,
    buscarPerfil,
    atualizarPerfil,
    upsertEndereco,
    excluir
};