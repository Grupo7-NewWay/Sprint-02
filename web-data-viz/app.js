var ambiente_processo = 'producao';
// var ambiente_processo = 'desenvolvimento';

var caminho_env = ambiente_processo === 'producao' ? '.env' : '.env.dev';
// Acima, temos o uso do operador ternário para definir o caminho do arquivo .env
// A sintaxe do operador ternário é: condição ? valor_se_verdadeiro : valor_se_falso

require("dotenv").config({ path: caminho_env });

var express = require("express");
var cors = require("cors");
var path = require("path");
var PORTA_APP = process.env.APP_PORT;
var HOST_APP = process.env.APP_HOST;

var app = express();

var indexRouter = require("./src/routes/index");
var usuarioRouter = require("./src/routes/usuarios");
var dashboardRouter = require("./src/routes/dashboard");
var pacotesRouter = require("./src/routes/pacotes");
var eventosRouter = require("./src/routes/eventos");
var slackService = require("./src/services/slackService");

app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, "public")));

app.use(cors());

app.use("/", indexRouter);
app.use("/usuarios", usuarioRouter);
app.use("/dashboard", dashboardRouter);
app.use("/pacotes", pacotesRouter);
app.use("/eventos", eventosRouter);

app.get("/api/usuarios", function (req, res) {
    res.status(200).json(slackService.listar());
});

app.post("/api/usuarios", function (req, res) {
    var { nome, slackId } = req.body;
    if (!nome || !slackId) return res.status(400).json({ erro: "nome e slackId são obrigatórios" });
    slackService.salvar({ nome, slackId });
    slackService.usuarioCadastrado(nome, slackId);
    res.status(201).json({ mensagem: "Cadastrado com sucesso!" });
});

app.delete("/api/usuarios/:slackId", function (req, res) {
    slackService.remover(req.params.slackId);
    res.status(200).json({ mensagem: "Usuário removido." });
});

app.post("/api/slack/notificar", function (req, res) {
    var { tipo, arquivo, totalRegistros, totalAvisos, erro, descricao } = req.body;
    if (tipo === "arquivoLido") {
        var info = arquivo;
        if (totalRegistros != null) info += " — " + totalRegistros + " registros";
        if (totalAvisos    != null && totalAvisos > 0) info += ", " + totalAvisos + " avisos";
        slackService.arquivoLido(info);
    } else if (tipo === "erro") {
        slackService.notificarErro(arquivo || "LeitorExcel", erro || "Erro desconhecido");
    } else if (tipo === "alerta") {
        slackService.alertaDados(descricao || "");
    } else if (tipo === "processoFinalizado") {
        slackService.arquivoLido("Todos os arquivos processados com sucesso");
    }
    res.status(200).json({ ok: true });
});

function encerrar(sinal) {
    slackService.sistemaDesligado().then(function () {
        process.exit(0);
    });
}

process.on("SIGINT",  encerrar);
process.on("SIGTERM", encerrar);

app.listen(PORTA_APP, function () {
    slackService.sistemaLigado();
    console.log(`
    ##   ##  ######   #####             ####       ##     ######     ##              ##  ##    ####    ######  
    ##   ##  ##       ##  ##            ## ##     ####      ##      ####             ##  ##     ##         ##  
    ##   ##  ##       ##  ##            ##  ##   ##  ##     ##     ##  ##            ##  ##     ##        ##   
    ## # ##  ####     #####    ######   ##  ##   ######     ##     ######   ######   ##  ##     ##       ##    
    #######  ##       ##  ##            ##  ##   ##  ##     ##     ##  ##            ##  ##     ##      ##     
    ### ###  ##       ##  ##            ## ##    ##  ##     ##     ##  ##             ####      ##     ##      
    ##   ##  ######   #####             ####     ##  ##     ##     ##  ##              ##      ####    ######  
    \n\n\n                                                                                                 
    Servidor do seu site já está rodando! Acesse o caminho a seguir para visualizar .: http://${HOST_APP}:${PORTA_APP} :. \n\n
    Você está rodando sua aplicação em ambiente de .:${process.env.AMBIENTE_PROCESSO}:. \n\n
    \tSe .:desenvolvimento:. você está se conectando ao banco local. \n
    \tSe .:producao:. você está se conectando ao banco remoto. \n\n
    \t\tPara alterar o ambiente, comente ou descomente as linhas 1 ou 2 no arquivo 'app.js'\n\n`);
});
