var dashboardModel = require("../models/dashbordModel");

function buscarTopRegiao(req, res) {
    console.log("Recuperando top regiões por eventos");

    dashboardModel.buscarTopRegiao().then(function (resultado) {
        if (resultado.length > 0) {
            res.status(200).json(resultado);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar top regiões.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarEventosPorTipo(req, res) {
    console.log("Recuperando eventos por tipo");

    dashboardModel.buscarEventosPorTipo().then(function (resultado) {
        if (resultado.length > 0) {
            res.status(200).json(resultado);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar eventos por tipo.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarEventosProximos(req, res) {
    console.log("Recuperando próximos eventos");

    dashboardModel.buscarEventosProximos().then(function (resultado) {
        if (resultado.length > 0) {
            res.status(200).json(resultado);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar próximos eventos.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarGastoMedio(req, res) {
    console.log("Recuperando gasto médio do turista");

    dashboardModel.buscarGastoMedio().then(function (resultado) {
        if (resultado.length > 0 && resultado[0].gastoMedio !== null) {
            res.status(200).json(resultado[0]);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar gasto médio.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarPermanenciaMedia(req, res) {
    console.log("Recuperando permanência média");

    dashboardModel.buscarPermanenciaMedia().then(function (resultado) {
        if (resultado.length > 0 && resultado[0].permanenciaMedia !== null) {
            res.status(200).json(resultado[0]);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar permanência média.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarTaxaConversao(req, res) {
    console.log("Recuperando taxa de conversão");

    dashboardModel.buscarTaxaConversao().then(function (resultado) {
        if (resultado.length > 0 && resultado[0].taxaConversao !== null) {
            res.status(200).json(resultado[0]);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar taxa de conversão.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarTotalMensalVisitas(req, res) {
    console.log("Recuperando total mensal de visitas");

    dashboardModel.buscarTotalMensalVisitas().then(function (resultado) {
        if (resultado.length > 0 && resultado[0].totalVisitas !== null) {
            res.status(200).json(resultado[0]);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar total mensal de visitas.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

module.exports = {
    buscarTopRegiao,
    buscarEventosPorTipo,
    buscarEventosProximos,
    buscarPermanenciaMedia,
    buscarGastoMedio,
    buscarTaxaConversao,
    buscarTotalMensalVisitas
}
