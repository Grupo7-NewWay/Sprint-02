var express = require("express");
var router = express.Router();

var pacotesController = require("../controllers/pacotesController");

router.post("/publicarPacote", function (req, res) {
pacotesController.publicarPacote(req, res);
})

module.exports = router;