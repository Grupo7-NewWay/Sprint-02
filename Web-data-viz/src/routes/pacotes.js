var express = require("express");
var router = express.Router();

var pacotesController = require("../controllers/pacotesController");

router.post("/publicarPacote", function (req, res) {
pacotesController.publicarPacote(req, res);
})

router.get("/carregarPacotes", function (req, res) {
    pacotesController.carregarPacotes(req, res);
})

module.exports = router;