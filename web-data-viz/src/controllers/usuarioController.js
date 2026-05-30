var usuarioModel = require("../models/usuarioModel");
//var aquarioModel = require("../models/aquarioModel");

function autenticar(req, res) {
    var email = req.body.emailServer;
    var senha = req.body.senhaServer;

    if (email == undefined) {
        res.status(400).send("Seu email está undefined!");
    } else if (senha == undefined) {
        res.status(400).send("Sua senha está undefined!");
    } else {

        usuarioModel.autenticar(email, senha)
            .then(
                function (resultadoAutenticar) {
                    console.log(`\nResultados encontrados: ${resultadoAutenticar.length}`);
                    console.log(`Resultados: ${JSON.stringify(resultadoAutenticar)}`); // transforma JSON em String

                    if (resultadoAutenticar.length == 1) {
                        console.log(resultadoAutenticar);

                        res.json({
                            id: resultadoAutenticar[0].idUsuario,
                            email: resultadoAutenticar[0].email,
                            nome: resultadoAutenticar[0].nome,
                            idAgencia: resultadoAutenticar[0].fkAgencia,
                        });

                    } else if (resultadoAutenticar.length == 0) {
                        res.status(403).send("Email e/ou senha inválido(s)");
                    } else {
                        res.status(403).send("Mais de um usuário com o mesmo login e senha!");
                    }
                }
            ).catch(
                function (erro) {
                    console.log(erro);
                    console.log("\nHouve um erro ao realizar o login! Erro: ", erro.sqlMessage);
                    res.status(500).json(erro.sqlMessage);
                }
            );
    }

}

function cadastrar(req, res) {

    var nome = req.body.nomeServer;
    var email = req.body.emailServer;
    var senha = req.body.senhaServer;
    var token = req.body.tokenServer;
    var telefone = req.body.telefoneServer;

    if (nome == undefined) {
        res.status(400).send("O nome está undefined!");

    } else if (email == undefined) {
        res.status(400).send("O email está undefined!");

    } else if (senha == undefined) {
        res.status(400).send("A senha está undefined!");

    } else if (token == undefined) {
        res.status(400).send("O token está undefined!");

    } else {

        usuarioModel.buscarAgenciaPorToken(token)

            .then(function(resultadoToken) {

                if (resultadoToken.length == 0) {

                    res.status(400).json({
                        mensagem: "Token inválido!"
                    });

                } else {

                    var idAgencia = resultadoToken[0].idAgencia;

                    usuarioModel.cadastrar(
                        nome,
                        email,
                        senha,
                        telefone,
                        idAgencia
                    )

                    .then(function(resultadoCadastro) {

                        res.status(200).json(resultadoCadastro);

                    })

                    .catch(function(erro) {

                        console.log(erro);

                        res.status(500).json({
                            mensagem: "Erro ao cadastrar usuário"
                        });

                    });

                }

            })

            .catch(function(erro) {

                console.log(erro);

                res.status(500).json({
                    mensagem: "Erro ao validar token"
                });

            });

    }

}

function buscarPerfil(req, res) {
    var id = parseInt(req.params.id);
    if (!id || isNaN(id)) return res.status(400).json({ mensagem: "ID inválido." });

    usuarioModel.buscarPerfil(id)
        .then(function (resultado) {
            if (resultado.length === 0) return res.status(404).json({ mensagem: "Usuário não encontrado." });
            res.status(200).json(resultado[0]);
        })
        .catch(function (erro) {
            console.log(erro);
            res.status(500).json({ mensagem: "Erro ao buscar perfil.", detalhe: erro.sqlMessage });
        });
}

function atualizarPerfil(req, res) {
    var id = parseInt(req.params.id);
    if (!id || isNaN(id)) return res.status(400).json({ mensagem: "ID inválido." });

    var { nome, email, telefone, dataNascimento, cep, logradouro, numero, complemento, bairro, cidade, estado } = req.body;

    usuarioModel.atualizarPerfil(id, nome, email, telefone, dataNascimento)
        .then(function () {
            if (cep && logradouro && cidade) {
                return usuarioModel.upsertEndereco(id, cep, logradouro, numero || '', complemento || '', bairro || '', cidade, estado || '');
            }
        })
        .then(function () {
            res.status(200).json({ mensagem: "Perfil atualizado com sucesso." });
        })
        .catch(function (erro) {
            console.log(erro);
            res.status(500).json({ mensagem: "Erro ao atualizar perfil.", detalhe: erro.sqlMessage });
        });
}

function excluir(req, res) {
    var id = parseInt(req.params.id);

    if (!id || isNaN(id)) {
        return res.status(400).json({ mensagem: "ID inválido." });
    }

    usuarioModel.excluir(id)
        .then(function (resultado) {
            if (resultado.affectedRows === 0) {
                return res.status(404).json({ mensagem: "Usuário não encontrado." });
            }
            res.status(200).json({ mensagem: "Conta excluída com sucesso." });
        })
        .catch(function (erro) {
            console.log(erro);
            res.status(500).json({ mensagem: "Erro ao excluir conta.", detalhe: erro.sqlMessage });
        });
}

module.exports = {
    autenticar,
    cadastrar,
    buscarPerfil,
    atualizarPerfil,
    excluir
}