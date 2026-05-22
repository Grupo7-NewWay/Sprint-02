var express = require("express");
var router = express.Router();

var pacoteController = require("../controllers/pacoteController");

router.get("/carregarPacotes", function (req, res) {
    pacoteController.listarTodos(req, res);
});

router.post("/publicarPacote", function (req, res) {
    pacoteController.cadastrar(req, res);
});

router.get("/:idPacote", function (req, res) {
    pacoteController.buscarPorId(req, res);
});

module.exports = router;
