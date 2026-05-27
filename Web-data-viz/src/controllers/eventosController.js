var eventosModel = require("../models/eventosModel");

function normalizarData(data) {
    if (!data) return data;
    if (data.includes('T')) return data.substring(0, 10);
    if (data.includes('/')) {
        var partes = data.split('/');
        return `${partes[2]}-${partes[1].padStart(2, '0')}-${partes[0].padStart(2, '0')}`;
    }
    return data;
}

function publicarEvento(req, res) {

    console.log(req.body);

    var nome = req.body.nomeServer;

    var localidade = req.body.localidadeServer;

    var data = normalizarData(req.body.dataServer);

    if (nome == undefined) {

        res.status(400).send("O nome está undefined!");

    } else if (localidade == undefined) {

        res.status(400).send("A localidade está undefined!");

    } else if (data == undefined) {

        res.status(400).send("A data está undefined!");

    } else {

        eventosModel.publicarEvento(

            nome,
            localidade,
            data

        )

        .then(

            function (resultado) {

                res.json(resultado);

            }

        )

        .catch(

            function (erro) {

                console.log(erro);

                res.status(500).json(erro.sqlMessage);

            }

        );

    }

}

function carregarEventos(req, res) {

    eventosModel.carregarEventos()

    .then(

        function (resultado) {

            res.status(200).json(resultado);

        }

    )

    .catch(

        function (erro) {

            console.log(erro);

            res.status(500).json(erro.sqlMessage);

        }

    );

}

function atualizarEvento(req, res) {

    var id = req.params.id;

    var nome = req.body.nomeServer;
    var localidade = req.body.localidadeServer;
    var data = normalizarData(req.body.dataServer);

    if (nome == undefined) {
        res.status(400).send("Nome undefined");
    } else if (localidade == undefined) {
        res.status(400).send("Localidade undefined");
    } else if (data == undefined) {
        res.status(400).send("Data undefined");
    } else {

        eventosModel.atualizarEvento(id, nome, localidade, data)
            .then(function (resultado) {
                res.json(resultado);
            })
            .catch(function (erro) {
                console.log(erro);
                res.status(500).json(erro.sqlMessage);
            });
    }
}

function deletarEvento(req, res) {

    var id = req.params.id;

    console.log("ID RECEBIDO NO CONTROLLER:");
    console.log(id);

    eventosModel.deletarEvento(id)
        .then(function (resultado) {
            res.json(resultado);
        })
        .catch(function (erro) {
            console.log(erro);
            res.status(500).json(erro.sqlMessage);
        });

}

module.exports = {

    publicarEvento,
    carregarEventos,
    atualizarEvento,
    deletarEvento

};