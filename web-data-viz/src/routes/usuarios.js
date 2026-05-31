var express = require("express");
var router = express.Router();

var usuarioController = require("../controllers/usuarioController");

//Recebendo os dados do html e direcionando para a função cadastrar de usuarioController.js
router.post("/cadastrar", function (req, res) {
    usuarioController.cadastrar(req, res);
})

router.post("/autenticar", function (req, res) {
    usuarioController.autenticar(req, res);
});

router.get("/perfil/:id", function (req, res) {
    usuarioController.buscarPerfil(req, res);
});

router.put("/perfil/:id", function (req, res) {
    usuarioController.atualizarPerfil(req, res);
});

router.delete("/excluir/:id", function (req, res) {
    usuarioController.excluir(req, res);
});

module.exports = router;