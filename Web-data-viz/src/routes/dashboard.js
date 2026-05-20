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

router.get("/permanencia-media", function (req, res) {
    dashboardController.buscarPermanenciaMedia(req, res);
});

router.get("/gasto-medio", function (req, res) {
    dashboardController.buscarGastoMedio(req, res);
});

module.exports = router;
