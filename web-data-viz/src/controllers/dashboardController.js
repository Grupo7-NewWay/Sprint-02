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
        if (resultado.length > 0 && resultado[0].gastoMedio !== null && resultado[0].gastoMedio > 0) {
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
    console.log("Recuperando histórico de visitas");

    var mesesPt = ["Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"];

    dashboardModel.buscarHistoricoVisitas().then(function (resultado) {
        if (!resultado || resultado.length === 0) {
            return res.status(204).send("Nenhum resultado encontrado!");
        }

        var labels = resultado.map(function (r) { return mesesPt[r.numeroMes - 1]; });
        var data   = resultado.map(function (r) { return r.totalVisitas; });

        res.status(200).json({ labels: labels, data: data });
    }).catch(function (erro) {
        console.log(erro);
        console.log("Houve um erro ao buscar histórico de visitas.", erro.sqlMessage);
        res.status(500).json(erro.sqlMessage);
    });
}

var ESTADOS_RAW = {
    // Nomes completos
    "Acre": "BRAC", "Alagoas": "BRAL", "Amapá": "BRAP", "Amazonas": "BRAM",
    "Bahia": "BRBA", "Ceará": "BRCE", "Distrito Federal": "BRDF",
    "Espírito Santo": "BRES", "Goiás": "BRGO", "Maranhão": "BRMA",
    "Mato Grosso": "BRMT", "Mato Grosso do Sul": "BRMS", "Minas Gerais": "BRMG",
    "Pará": "BRPA", "Paraíba": "BRPB", "Paraná": "BRPR", "Pernambuco": "BRPE",
    "Piauí": "BRPI", "Rio de Janeiro": "BRRJ", "Rio Grande do Norte": "BRRN",
    "Rio Grande do Sul": "BRRS", "Rondônia": "BRRO", "Roraima": "BRRR",
    "Santa Catarina": "BRSC", "São Paulo": "BRSP", "Sergipe": "BRSE",
    "Tocantins": "BRTO",
    // Siglas — caso o Excel use abreviação
    "AC": "BRAC", "AL": "BRAL", "AP": "BRAP", "AM": "BRAM",
    "BA": "BRBA", "CE": "BRCE", "DF": "BRDF", "ES": "BRES",
    "GO": "BRGO", "MA": "BRMA", "MT": "BRMT", "MS": "BRMS",
    "MG": "BRMG", "PA": "BRPA", "PB": "BRPB", "PR": "BRPR",
    "PE": "BRPE", "PI": "BRPI", "RJ": "BRRJ", "RN": "BRRN",
    "RS": "BRRS", "RO": "BRRO", "RR": "BRRR", "SC": "BRSC",
    "SP": "BRSP", "SE": "BRSE", "TO": "BRTO"
};

function normalizarTexto(str) {
    return str.trim()
        .normalize("NFD")
        .replace(/[̀-ͯ]/g, "")
        .toLowerCase();
}

var ESTADO_LOOKUP = {};
Object.keys(ESTADOS_RAW).forEach(function (k) {
    ESTADO_LOOKUP[normalizarTexto(k)] = ESTADOS_RAW[k];
});

function buscarVisitasPorEstado(req, res) {
    console.log("Recuperando visitas por estado");

    dashboardModel.buscarVisitasPorEstado().then(function (resultado) {
        console.log("[mapa] linhas retornadas do banco:", resultado.length);
        if (resultado.length > 0) {
            var mapa = {};
            resultado.forEach(function (r) {
                if (!r.estado) return;
                var chave = normalizarTexto(r.estado);
                var id = ESTADO_LOOKUP[chave];
                if (id) {
                    mapa[id] = Number(r.totalVisitas) || 0;
                } else {
                    console.warn("[mapa] estado não reconhecido — valor no banco: '" + r.estado + "' | normalizado: '" + chave + "'");
                }
            });
            console.log("[mapa] estados mapeados com sucesso:", Object.keys(mapa).length, Object.keys(mapa));
            if (Object.keys(mapa).length === 0) {
                return res.status(204).send("Nenhum estado reconhecido!");
            }
            res.status(200).json(mapa);
        } else {
            console.warn("[mapa] tabela 'eventos' sem registros com estado preenchido — retornando 204");
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
