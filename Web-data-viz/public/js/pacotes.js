const listaPacotes = document.getElementById("listaPacotes");

const tituloFormulario = document.getElementById("tituloCriarPacote");

const botaoFormulario = document.getElementById("btnPublicar");

const inputNome = document.getElementById("ipt_nomePacote");

const inputCategoria = document.getElementById("ipt_categoria");

const inputDestino = document.getElementById("ipt_destino");

const inputDuracao = document.getElementById("ipt_duracao");

const inputPreco = document.getElementById("ipt_preco");

const inputDescricao = document.getElementById("ipt_descricao");

const inputVagas = document.getElementById("ipt_vagas");

const inputDataInicio = document.getElementById("ipt_dataInicio");

let listaDePacotes = [];

let idPacoteEditando = null;

function carregarPacotes() {

    fetch(`/pacotes/carregarPacotes`)

        .then(resposta => {

            if (resposta.status == 200) {

                resposta.json().then(resposta => {

                    console.log(`Dados recebidos: ${JSON.stringify(resposta)}`);

                    listaDePacotes = resposta;

                    listaPacotes.innerHTML = "";

                    resposta.forEach((pacote) => {

                        const card = document.createElement("div");

                        card.classList.add("cardPacote");

                        card.innerHTML = `

                        <div class="infoPacote">

                            <h3>${pacote.nomePacote}</h3>

                            <span class="categoria">
                                ${pacote.categoria}
                            </span>

                            <p>
                                ${pacote.descricao}
                            </p>

                        </div>

                        <div class="dadosPacote">

                            <span>
                                ${pacote.destino}
                            </span>

                            <span>
                                ${pacote.duracao} dias
                            </span>

                            <span>
                                ${pacote.vagas} vagas
                            </span>

                        </div>

                        <div class="precoPacote">

                            <h3>
                                R$ ${pacote.preco}
                            </h3>

                        </div>

                        <div class="acoesPacote">

                            <a href="#" onclick="editarPacote(${pacote.idPacote})">
                                Editar
                            </a>

                            <a href="#" onclick="deletarPacote(${pacote.idPacote})">
                                Excluir
                            </a>

                        </div>

                    `;

                        listaPacotes.appendChild(card);

                    });

                });

            } else {

                console.error(`Nenhum dado encontrado ou erro na API`);

            }

        })

        .catch(function (error) {

            console.error(`Erro na obtenção dos dados de pacote p/ lista: ${error.message}`);

        });

}

function editarPacote(id) {

    const pacote = listaDePacotes.find(
        p => p.idPacote == id
    );

    if (!pacote) {

        console.error("Pacote não encontrado");

        return;
    }

    idPacoteEditando = id;

    inputNome.value = pacote.nomePacote;

    inputCategoria.value = pacote.categoria;

    inputDestino.value = pacote.destino;

    inputDuracao.value = pacote.duracao;

    inputPreco.value = pacote.preco;

    inputDescricao.value = pacote.descricao;

    inputVagas.value = pacote.vagas;

    inputDataInicio.value = pacote.dataInicio;

    tituloFormulario.innerHTML = `
        Editar <b>Pacote</b>
    `;

    botaoFormulario.innerText = "Salvar alterações";

    window.scrollTo({

        top: document.body.scrollHeight,

        behavior: "smooth"

    });

}

function salvarPacote() {

    if (idPacoteEditando != null) {

        const pacoteAtualizado = {

            nomePacote: inputNome.value,

            categoria: inputCategoria.value,

            destino: inputDestino.value,

            duracao: inputDuracao.value,

            preco: inputPreco.value,

            descricao: inputDescricao.value,

            vagas: inputVagas.value,

            dataInicio: inputDataInicio.value

        };

        atualizarPacote(
            idPacoteEditando,
            pacoteAtualizado
        );

    } else {

        publicarPacote();

<<<<<<< HEAD
=======
        fetch("/pacotes/publicarPacote", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                nomePacoteServer: nomePacote,
                categoriaServer: categoria,
                destinoServer: destino,
                duracaoServer: duracao,
                precoServer: preco,
                descricaoServer: descricao,
                vagasServer: vagas,
                dataInicioServer: dataInicio
            }),
        })
            .then(function (resposta) {
                console.log("Resposta:", resposta);
                return resposta.json().then(function (dados) {
                    if (resposta.ok) {
                        alert("Pacote publicado com sucesso!");
                        carregarPacotes();
                    } else {
                        alert(dados.mensagem);
                    }
                });
            })
            .catch(function (erro) {
                console.log("ERRO:", erro);
            });
>>>>>>> main
    }

}

function limparFormulario() {

    inputNome.value = "";

    inputCategoria.value = "";

    inputDestino.value = "";

    inputDuracao.value = "";

    inputPreco.value = "";

    inputDescricao.value = "";

    inputVagas.value = "";

    inputDataInicio.value = "";

    idPacoteEditando = null;

    tituloFormulario.innerHTML = `
        Criar <b>Novo Pacote</b>
    `;

    botaoFormulario.innerText = "Publicar pacote";

}

function publicarPacote() {
    var nomePacote = ipt_nomePacote.value;
    var categoria = ipt_categoria.value;
    var destino = ipt_destino.value;
    var duracao = Number(ipt_duracao.value);
    var preco = Number(ipt_preco.value);
    var descricao = ipt_descricao.value;
    var vagas = Number(ipt_vagas.value);
    var dataInicio = ipt_dataInicio.value;

    if (nomePacote == null || nomePacote == "") {
        alert("O nome não pode estar vazio!")
        return;
    }
    if (categoria == null || categoria == "") {
        alert("A categoria não pode estar vazia!")
        return;
    }
    if (destino == null || destino == "") {
        alert("O destino não pode estar vazio!")
        return;
    }
    if (duracao == null || duracao <= 0) {
        alert("A duração não pode estar zerada ou menor que 0!")
        return;
    }
    if (preco == null || preco <= 0) {
        alert("O preço não pode estar zerado ou menor que 0!")
        return;
    }
    if (descricao == null || descricao == "") {
        alert("A descrição não pode estar vazia!")
        return;
    }
    if (vagas == null || vagas <= 0) {
        alert("O número de vagas não pode estar zerado ou menor que 0!")
        return;
    }
    if (dataInicio == null || dataInicio == "") {
        alert("A data de início não pode estar vazia!")
        return;
    }

    console.log(nomePacote, categoria, destino, duracao, preco, descricao, vagas, dataInicio);

    fetch("/pacotes/publicarPacote", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            nomePacoteServer: nomePacote,
            categoriaServer: categoria,
            destinoServer: destino,
            duracaoServer: duracao,
            precoServer: preco,
            descricaoServer: descricao,
            vagasServer: vagas,
            dataInicioServer: dataInicio
        }),
    })
        .then(function (resposta) {
            console.log("Resposta:", resposta);
            return resposta.json().then(function (dados) {
                if (resposta.ok) {
                    setTimeout(function () {
                        alert("Pacote publicado com sucesso!");
                        limparFormulario();
                    }, 1000);
                } else {
                    alert(dados.mensagem);
                }
            });
        })
        .catch(function (erro) {
            console.log("ERRO:", erro);
        });
    carregarPacotes();
}

function atualizarPacote(id, pacote) {

    fetch(`/pacotes/atualizarPacote/${id}`, {

        method: "PUT",

        headers: {

            "Content-Type": "application/json"

        },

        body: JSON.stringify(pacote)

    })

    .then(resposta => {

        if (resposta.ok) {

            alert("Pacote atualizado com sucesso!");

            limparFormulario();

            carregarPacotes();

        } else {

            alert("Erro ao atualizar pacote");

        }

    })

    .catch(erro => {

        console.error(erro);

    });

}

function deletarPacote(id) {

    const confirmar = confirm(

        "Tem certeza que deseja excluir este pacote?"

    );

    if (!confirmar) {

        return;

    }

    fetch(`/pacotes/deletarPacote/${id}`, {

        method: "DELETE"

    })

    .then(resposta => {

        if (resposta.ok) {

            alert("Pacote excluído com sucesso!");

            carregarPacotes();

        } else {

            alert("Erro ao excluir pacote");

        }

    })

    .catch(erro => {

        console.error(erro);

    });

}