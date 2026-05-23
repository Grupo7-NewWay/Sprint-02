var usuarioModel = require("../models/usuarioModel");
//var aquarioModel = require("../models/aquarioModel");

function autenticar(req, res) {
    var email = req.body.emailServer;
    var senha = req.body.senhaServer;

    if (email == undefined) {
        res.status(400).send("Seu email está undefined!");
    } else if (senha == undefined) {
        res.status(400).send("Sua senha está indefinida!");
    } else {

        usuarioModel.autenticar(email, senha)
            .then(
                function (resultadoAutenticar) {
                    console.log(`\nResultados encontrados: ${resultadoAutenticar.length}`);
                    console.log(`Resultados: ${JSON.stringify(resultadoAutenticar)}`); // transforma JSON em String

                    if (resultadoAutenticar.length == 1) {
                        console.log(resultadoAutenticar);

                        res.json({
                            id: resultadoAutenticar[0].idAgencia,
                            email: resultadoAutenticar[0].email,
                            nome: resultadoAutenticar[0].nomeAgencia
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
    // Crie uma variável que vá recuperar os valores do arquivo cadastro.html
    var nome = req.body.nomeServer;
    var email = req.body.emailServer;
    var senha = req.body.senhaServer;
    var cnpj = req.body.cnpjServer;
    var telefone = req.body.telefoneServer;

    // Faça as validações dos valores
    if (nome == undefined) {
        res.status(400).send("Seu nome está undefined!");
    } else if (email == undefined) {
        res.status(400).send("Seu email está undefined!");
    } else if (senha == undefined) {
        res.status(400).send("Sua senha está undefined!");
    } else if (cnpj == undefined) {
        res.status(400).send("Seu cnpj está undefined!");
    } else if (telefone == undefined) {
        res.status(400).send("Seu telefone está undefined!");
    } else {

        // Passe os valores como parâmetro e vá para o arquivo usuarioModel.js
        usuarioModel.cadastrar(nome, email, senha, cnpj, telefone)
            .then(
                function (resultado) {
                    res.json(resultado);
                }
            ).catch(
                function (erro) {
                    console.log(erro);
                    if (erro.code === "ER_DUP_ENTRY") {

                        if (erro.sqlMessage.includes("cnpj")) {
                            return res.status(400).json({ mensagem: "CNPJ já cadastrado" });
                        }

                        if (erro.sqlMessage.includes("telefone")) {
                            return res.status(400).json({ mensagem: "Telefone já cadastrado" });
                        }

                        if (erro.sqlMessage.includes("email")) {
                            return res.status(400).json({ mensagem: "Email já cadastrado" });
                        }

                        return res.status(400).json({ mensagem: "Dados duplicados" });
                    }

                    res.status(500).json({ mensagem: "Erro interno no servidor" });
                }
            );
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

    var { nomeAgencia, email, telefone, dataNascimento, cep, logradouro, numero, complemento, bairro, cidade, estado } = req.body;

    usuarioModel.atualizarAgencia(id, nomeAgencia, email, telefone, dataNascimento)
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