var express = require("express");
var router = express.Router();

router.get("/", function (req, res) {
    res.redirect("/dashboard.html");
});

module.exports = router;