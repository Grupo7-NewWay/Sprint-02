var https = require("https");
var fs    = require("fs");
var path  = require("path");

var WEBHOOK_URL  = process.env.SLACK_WEBHOOK_URL;
var ARQUIVO      = path.join(__dirname, "../../usuarios.json");

// ── Persistência de usuários ──────────────────────────────────────────────────

function listar() {
    try {
        if (!fs.existsSync(ARQUIVO)) return [];
        var conteudo = fs.readFileSync(ARQUIVO, "utf8");
        var dados = JSON.parse(conteudo);
        return Array.isArray(dados.usuarios) ? dados.usuarios : [];
    } catch (e) {
        return [];
    }
}

function salvar(usuario) {
    var lista = listar();
    lista = lista.filter(function (u) { return u.slackId !== usuario.slackId; });
    lista.push(usuario);
    fs.writeFileSync(ARQUIVO, JSON.stringify({ usuarios: lista }, null, 2), "utf8");
}

function remover(slackId) {
    var lista = listar().filter(function (u) { return u.slackId !== slackId; });
    fs.writeFileSync(ARQUIVO, JSON.stringify({ usuarios: lista }, null, 2), "utf8");
}

// ── Envio para o Slack ────────────────────────────────────────────────────────

function buildMencoes() {
    return listar().map(function (u) { return "<@" + u.slackId + ">"; }).join(" ");
}

function agora() {
    return new Date().toLocaleString("pt-BR", { timeZone: "America/Sao_Paulo" });
}

function enviar(texto) {
    if (!WEBHOOK_URL) {
        console.warn("SLACK_WEBHOOK_URL não configurada — notificação ignorada.");
        return Promise.resolve();
    }
    var mencoes = buildMencoes();
    var mensagem = mencoes ? mencoes + " " + texto : texto;
    var body = JSON.stringify({ text: mensagem });

    var url = new URL(WEBHOOK_URL);
    var options = {
        hostname: url.hostname,
        path:     url.pathname,
        method:   "POST",
        headers:  { "Content-Type": "application/json", "Content-Length": Buffer.byteLength(body) }
    };

    return new Promise(function (resolve) {
        var req = https.request(options, function (res) {
            console.log("Slack status:", res.statusCode);
            resolve();
        });
        req.on("error", function (e) {
            console.error("Erro ao enviar para Slack:", e.message);
            resolve();
        });
        req.write(body);
        req.end();
    });
}

// ── Eventos do sistema ────────────────────────────────────────────────────────

function sistemaLigado()             { return enviar(":white_check_mark: *Sistema LIGADO* — " + agora()); }
function sistemaDesligado()          { return enviar(":octagonal_sign: *Sistema DESLIGADO* — " + agora()); }
function arquivoLido(nomeArquivo)    { return enviar(":page_facing_up: *Arquivo lido:* `" + nomeArquivo + "` — " + agora()); }
function notificarErro(titulo, msg)  { return enviar(":x: *ERRO: " + titulo + "* — `" + msg + "` — " + agora()); }
function alertaDados(descricao)      { return enviar(":warning: *Alerta de Dados:* " + descricao + " — " + agora()); }

function usuarioCadastrado(nome, slackId) {
    if (!WEBHOOK_URL) {
        console.warn("SLACK_WEBHOOK_URL não configurada — notificação ignorada.");
        return Promise.resolve();
    }
    var texto = ":bell: <@" + slackId + "> Olá, *" + nome + "*! Você foi adicionado às notificações do sistema NewWay. — " + agora();
    var body = JSON.stringify({ text: texto });
    var url = new URL(WEBHOOK_URL);
    var options = {
        hostname: url.hostname,
        path:     url.pathname,
        method:   "POST",
        headers:  { "Content-Type": "application/json", "Content-Length": Buffer.byteLength(body) }
    };
    return new Promise(function (resolve) {
        var req = https.request(options, function (res) {
            console.log("Slack status:", res.statusCode);
            resolve();
        });
        req.on("error", function (e) {
            console.error("Erro ao enviar para Slack:", e.message);
            resolve();
        });
        req.write(body);
        req.end();
    });
}

module.exports = { listar, salvar, remover, sistemaLigado, sistemaDesligado, arquivoLido, notificarErro, alertaDados, usuarioCadastrado };
