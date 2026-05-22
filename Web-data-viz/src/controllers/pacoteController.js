var pacoteModel = require("../models/pacoteModel");

function listarTodos(req, res) {
    pacoteModel.listarTodos().then(function (resultado) {
        if (resultado.length > 0) {
            res.status(200).json(resultado);
        } else {
            res.status(204).send("Nenhum pacote encontrado!");
        }
    }).catch(function (erro) {
        console.log("Houve um erro ao listar pacotes.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarPorId(req, res) {
    var idPacote = req.params.idPacote;
    pacoteModel.buscarPorId(idPacote).then(function (resultado) {
        if (resultado.length > 0) {
            res.status(200).json(resultado[0]);
        } else {
            res.status(204).send("Pacote não encontrado!");
        }
    }).catch(function (erro) {
        console.log("Houve um erro ao buscar o pacote.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function cadastrar(req, res) {
    var { nomePacoteServer: nomePacote, categoriaServer: categoria, destinoServer: destino, duracaoServer: duracao, precoServer: preco, descricaoServer: descricao, vagasServer: vagas, dataInicioServer: dataInicio } = req.body;
    pacoteModel.cadastrar(nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio).then(function (resultado) {
        res.status(201).json({ mensagem: "Pacote cadastrado com sucesso!", id: resultado.insertId });
    }).catch(function (erro) {
        console.log("Houve um erro ao cadastrar o pacote.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

module.exports = {
    listarTodos,
    buscarPorId,
    cadastrar
};
