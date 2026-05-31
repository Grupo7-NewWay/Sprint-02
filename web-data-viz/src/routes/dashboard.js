var express = require("express");
var router = express.Router();

var dashboardController = require("../controllers/dashboardController");

router.get("/top-regiao", function (req, res) {
    dashboardController.buscarTopRegiao(req, res);
});

router.get("/eventos-por-tipo", function (req, res) {
    dashboardController.buscarEventosPorTipo(req, res);
});

router.get("/eventos-proximos", function (req, res) {
    dashboardController.buscarEventosProximos(req, res);
});

router.get("/duracao-media-evento", function (req, res) {
    dashboardController.buscarDuracaoMediaEvento(req, res);
});

router.get("/media-publico-evento", function (req, res) {
    dashboardController.buscarMediaPublicoEvento(req, res);
});

router.get("/motivacao-principal", function (req, res) {
    dashboardController.buscarMotivacaoPrincipal(req, res);
});

router.get("/total-mensal-visitas", function (req, res) {
    dashboardController.buscarTotalMensalVisitas(req, res);
});

router.get("/historico-visitas", function (req, res) {
    dashboardController.buscarHistoricoVisitas(req, res);
});

router.get("/visitas-por-estado", function (req, res) {
    dashboardController.buscarVisitasPorEstado(req, res);
});

module.exports = router;
