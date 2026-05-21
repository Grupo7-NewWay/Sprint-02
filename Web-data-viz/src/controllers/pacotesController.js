var pacotesModel = require("../models/pacotesModel");

function publicarPacote(req, res) {
    var nomePacote = req.body.nomePacoteServer;
    var categoria = req.body.categoriaServer;
    var destino = req.body.destinoServer;
    var duracao = req.body.duracaoServer;
    var preco = req.body.precoServer;
    var descricao = req.body.descricaoServer;
    var vagas = req.body.vagasServer;
    var dataInicio = req.body.dataInicioServer;

    if (nomePacote == undefined) {
        res.status(400).send("O nome de pacote está undefined!");
    } else if (categoria == undefined) {
        res.status(400).send("A categoria está undefined!");
    } else if (destino == undefined) {
        res.status(400).send("O destino está undefined!");
    } else if (duracao == undefined) {
        res.status(400).send("A duração está undefined!");
    } else if (preco == undefined) {
        res.status(400).send("O preço está undefined!");
    } else if (descricao == undefined) {
        res.status(400).send("A descrição está undefined!");
    } else if (vagas == undefined) {
        res.status(400).send("As vagas estão undefined!");
    } else if (dataInicio == undefined) {
        res.status(400).send("A data de início está undefined!");
    } else {

        pacotesModel.publicarPacote(nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio)
            .then(
                function (resultado) {
                    res.json(resultado);
                }
            ).catch(
                function (erro) {
                    console.log(erro);

                    res.status(500).json({ mensagem: "Erro interno no servidor" });
                }
            );
    }
}

function carregarPacotes(req, res) {

    pacotesModel.carregarPacotes().then(function (resultado) {
        if (resultado.length > 0) {
            res.status(200).json(resultado);
        } else {
            res.status(204).send("Nenhum resultado encontrado!")
        }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar as ultimas medidas.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });

}

    module.exports = {
        publicarPacote,
        carregarPacotes
    }