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

function buscarDuracaoMediaEvento(req, res) {
    console.log("Recuperando duracao media dos eventos");

    dashboardModel.buscarDuracaoMediaEvento().then(function (resultado) {
        if (resultado.length > 0 && resultado[0].duracaoMedia !== null) {
            res.status(200).json(resultado[0]);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarMediaPublicoEvento(req, res) {
    console.log("Recuperando media de publico por evento");

    dashboardModel.buscarMediaPublicoEvento().then(function (resultado) {
        if (resultado.length > 0 && resultado[0].mediaPublico !== null) {
            res.status(200).json(resultado[0]);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarMotivacaoPrincipal(req, res) {
    console.log("Recuperando motivacao principal de viagem");

    dashboardModel.buscarMotivacaoPrincipal().then(function (resultado) {
        if (resultado.length > 0) {
            res.status(200).json(resultado[0]);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
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

function buscarHistoricoVisitas(req, res) {
    console.log("Recuperando histórico de visitas dos últimos 6 meses");

    var mesesPt = ["Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"];

    var agora = new Date();
    var ultimos6 = [];
    for (var i = 5; i >= 0; i--) {
        var d = new Date(agora.getFullYear(), agora.getMonth() - i, 1);
        ultimos6.push({ ano: d.getFullYear(), mes: d.getMonth() + 1 });
    }

    dashboardModel.buscarHistoricoVisitas().then(function (resultado) {
        var mapa = {};
        resultado.forEach(function (r) {
            mapa[r.ano + "-" + r.numeroMes] = r.totalVisitas;
        });

        var labels = ultimos6.map(function (m) { return mesesPt[m.mes - 1]; });
        var data   = ultimos6.map(function (m) { return mapa[m.ano + "-" + m.mes] || 0; });

        res.status(200).json({ labels: labels, data: data });
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar histórico de visitas.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

function buscarVisitasPorEstado(req, res) {
    console.log("Recuperando visitas por estado");

    dashboardModel.buscarVisitasPorEstado().then(function (resultado) {
        if (resultado.length > 0) {
            var mapa = {};
            resultado.forEach(function (r) {
                mapa["BR" + r.uf] = r.totalVisitas;
            });
            res.status(200).json(mapa);
        } else {
            res.status(204).send("Nenhum resultado encontrado!");
        }
    }).catch(function (erro) {
        console.log(erro);
        res.status(500).json(erro.sqlMessage);
    });
}

module.exports = {
    buscarTopRegiao,
    buscarEventosPorTipo,
    buscarEventosProximos,
    buscarTotalMensalVisitas,
    buscarHistoricoVisitas,
    buscarVisitasPorEstado,
    buscarDuracaoMediaEvento,
    buscarMediaPublicoEvento,
    buscarMotivacaoPrincipal
}
