var express = require("express");

var router = express.Router();

var eventosController = require("../controllers/eventosController");

router.post("/publicarEvento", function (req, res) {
    eventosController.publicarEvento(req, res);
});

router.get("/carregarEventos", function (req, res) {
    eventosController.carregarEventos(req, res);
});

router.put("/atualizarEvento/:id", function (req, res) {
    eventosController.atualizarEvento(req, res);
});

router.delete("/deletarEvento/:id", function (req, res) {
    eventosController.deletarEvento(req, res);
});

module.exports = router;