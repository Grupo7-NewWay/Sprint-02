const listaPacotes = document.getElementById("listaPacotes");

function carregarPacotes() {

    fetch(`/pacotes/carregarPacotes`)
    
    .then(resposta => {

        if (resposta.status == 200) {

            resposta.json().then(resposta => {

                console.log(`Dados recebidos: ${JSON.stringify(resposta)}`);

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

                            <a href="#" onclick="excluirPacote(${pacote.idPacote})">
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

function publicarPacote() {
        var nomePacote = ipt_nomePacote.value;
        var categoria = ipt_categoria.value;
        var destino = ipt_destino.value;
        var duracao = Number(ipt_duracao.value);
        var preco = Number(ipt_preco.value);
        var descricao = ipt_descricao.value;
        var vagas = Number(ipt_vagas.value);
        var dataInicio = ipt_dataInicio.value;

        if (nomePacote == null) {
            alert("O nome não pode estar vazio!")
            return;
        }
        if (categoria == null) {
            alert("A categoria não pode estar vazia!")
            return;
        }
        if (destino == null) {
            alert("O destino não pode estar vazio!")
            return;
        }
        if (duracao == null) {
            alert("A duração não pode estar vazia!")
            return;
        }
        if (preco == null) {
            alert("O preço não pode estar vazio!")
            return;
        }
        if (descricao == null) {
            alert("A descrição não pode estar vazia!")
            return;
        }
        if (vagas == null || vagas < 0) {
            alert("O número de vagas não pode estar vazio ou menor que 0!")
            return;
        }
        if (dataInicio == null) {
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

carregarPacotes();