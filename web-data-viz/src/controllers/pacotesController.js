var pacotesModel = require("../models/pacotesModel");

function normalizarData(data) {
    if (!data) return data;
    if (data.includes('T')) return data.substring(0, 10);
    if (data.includes('/')) {
        var partes = data.split('/');
        return `${partes[2]}-${partes[1].padStart(2, '0')}-${partes[0].padStart(2, '0')}`;
    }
    return data;
}

function publicarPacote(req, res) {
    var nomePacote = req.body.nomePacoteServer;
    var categoria = req.body.categoriaServer;
    var destino = req.body.destinoServer;
    var duracao = req.body.duracaoServer;
    var preco = req.body.precoServer;
    var descricao = req.body.descricaoServer;
    var vagas = req.body.vagasServer;
    var dataInicio = normalizarData(req.body.dataInicioServer);
    var fkAgencia = req.body.fkAgenciaServer;

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
    } else if (fkAgencia == undefined) {
        res.status(400).send("A data de início está undefined!");
    } else {

        pacotesModel.publicarPacote(nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio, fkAgencia)
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
        res.status(200).json(resultado);
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar as ultimas medidas.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });

}

function atualizarPacote(req, res) {

    var idPacote = req.params.idPacote;

    var nomePacote = req.body.nomePacote;
    var categoria = req.body.categoria;
    var destino = req.body.destino;
    var duracao = req.body.duracao;
    var preco = req.body.preco;
    var descricao = req.body.descricao;
    var vagas = req.body.vagas;
    var dataInicio = normalizarData(req.body.dataInicio);

    if (idPacote == undefined) {
        res.status(400).send("O id do pacote está undefined!");

    } else if (nomePacote == undefined) {
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

        pacotesModel.atualizarPacote(

            idPacote,
            nomePacote,
            categoria,
            destino,
            duracao,
            preco,
            descricao,
            vagas,
            dataInicio

        )

        .then(

            function (resultado) {

                res.json(resultado);

            }

        )

        .catch(

            function (erro) {

                console.log(erro);

                res.status(500).json({
                    mensagem: "Erro interno no servidor"
                });

            }

        );

    }

}

function deletarPacote(req, res) {

    var idPacote = req.params.idPacote;

    if (idPacote == undefined) {

        res.status(400).send("O id do pacote está undefined!");

    } else {

        pacotesModel.deletarPacote(idPacote)

        .then(

            function (resultado) {

                res.json(resultado);

            }

        )

        .catch(

            function (erro) {

                console.log(erro);

                res.status(500).json({
                    mensagem: "Erro interno no servidor"
                });

            }

        );

    }

}


module.exports = {
    publicarPacote,
    carregarPacotes,
    atualizarPacote,
    deletarPacote
}