/* === DATA === */
var STATE_DATA = {
  BRAC: { name: "Acre",                uf: "AC", sales:  320 },
  BRAL: { name: "Alagoas",             uf: "AL", sales:  870 },
  BRAP: { name: "Amapá",               uf: "AP", sales:  210 },
  BRAM: { name: "Amazonas",            uf: "AM", sales:  540 },
  BRBA: { name: "Bahia",               uf: "BA", sales: 2100 },
  BRCE: { name: "Ceará",               uf: "CE", sales: 1650 },
  BRDF: { name: "Distrito Federal",    uf: "DF", sales: 1980 },
  BRES: { name: "Espírito Santo",      uf: "ES", sales:  890 },
  BRGO: { name: "Goiás",               uf: "GO", sales: 1120 },
  BRMA: { name: "Maranhão",            uf: "MA", sales:  760 },
  BRMT: { name: "Mato Grosso",         uf: "MT", sales:  680 },
  BRMS: { name: "Mato Grosso do Sul",  uf: "MS", sales:  590 },
  BRMG: { name: "Minas Gerais",        uf: "MG", sales: 3400 },
  BRPA: { name: "Pará",                uf: "PA", sales:  950 },
  BRPB: { name: "Paraíba",             uf: "PB", sales:  740 },
  BRPR: { name: "Paraná",              uf: "PR", sales: 2200 },
  BRPE: { name: "Pernambuco",          uf: "PE", sales: 1430 },
  BRPI: { name: "Piauí",               uf: "PI", sales:  410 },
  BRRJ: { name: "Rio de Janeiro",      uf: "RJ", sales: 3100 },
  BRRN: { name: "Rio Grande do Norte", uf: "RN", sales:  690 },
  BRRS: { name: "Rio Grande do Sul",   uf: "RS", sales: 2050 },
  BRRO: { name: "Rondônia",            uf: "RO", sales:  380 },
  BRRR: { name: "Roraima",             uf: "RR", sales:  180 },
  BRSC: { name: "Santa Catarina",      uf: "SC", sales: 1760 },
  BRSP: { name: "São Paulo",           uf: "SP", sales: 5800 },
  BRSE: { name: "Sergipe",             uf: "SE", sales:  430 },
  BRTO: { name: "Tocantins",           uf: "TO", sales:  350 },
};

var HISTORICO_VISITAS_FALLBACK = (function () {
  var mesesPt = ["Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"];
  var agora   = new Date();
  var labels  = [];
  for (var i = 5; i >= 0; i--) {
    var d = new Date(agora.getFullYear(), agora.getMonth() - i, 1);
    labels.push(mesesPt[d.getMonth()]);
  }
  return { labels: labels, data: [0, 0, 0, 0, 0, 0] };
}());

var MAP_COLOR_STOPS = [
  [18,  44,  74 ],
  [21,  101, 160],
  [55,  138, 221],
  [245, 186, 90 ],
];

/* === UTILITIES === */
function formatNumber(n) {
  return n.toLocaleString("pt-BR");
}

function getSalesStats() {
  var values = Object.values(STATE_DATA).map(function(d) { return d.sales; });
  return {
    min:   Math.min.apply(null, values),
    max:   Math.max.apply(null, values),
    total: values.reduce(function(sum, v) { return sum + v; }, 0),
  };
}

function getSortedStates() {
  return Object.entries(STATE_DATA).sort(function(a, b) { return b[1].sales - a[1].sales; });
}

/* === MAP COLOR === */
function interpolateColor(t) {
  var segments = MAP_COLOR_STOPS.length - 1;
  var segIndex = Math.min(Math.floor(t * segments), segments - 1);
  var segT     = t * segments - segIndex;
  var from     = MAP_COLOR_STOPS[segIndex];
  var to       = MAP_COLOR_STOPS[segIndex + 1];
  var r = Math.round(from[0] + (to[0] - from[0]) * segT);
  var g = Math.round(from[1] + (to[1] - from[1]) * segT);
  var b = Math.round(from[2] + (to[2] - from[2]) * segT);
  return "rgb(" + r + "," + g + "," + b + ")";
}

/* === MAP RENDERING === */
function colorMapByData() {
  var stats = getSalesStats();
  var min = stats.min;
  var max = stats.max;
  document.getElementById("lmin").textContent = formatNumber(min);
  document.getElementById("lmax").textContent = formatNumber(max);

  Object.entries(STATE_DATA).forEach(function(entry) {
    var id    = entry[0];
    var state = entry[1];
    var el = document.getElementById(id);
    if (!el) return;
    var t = max === min ? 0.5 : (state.sales - min) / (max - min);
    el.style.fill = interpolateColor(t);
  });
}

function carregarVisitasPorEstado() {
  fetch("/dashboard/visitas-por-estado")
    .then(function (res) {
      if (res.status === 204) return null;
      if (!res.ok) throw new Error("Erro ao buscar visitas por estado");
      return res.json();
    })
    .then(function (dados) {
      if (dados) {
        Object.keys(STATE_DATA).forEach(function (id) {
          if (dados[id] !== undefined) {
            STATE_DATA[id].sales = dados[id];
          }
        });
      }
      colorMapByData();
      bindMapInteractions();
    })
    .catch(function (erro) {
      console.error("Erro ao carregar mapa:", erro);
      colorMapByData();
      bindMapInteractions();
    });
}

function buildTooltipHTML(state, rank) {
  return '<strong class="tooltip__state">' + state.name + ' (' + state.uf + ')</strong>' +
    '<div class="tooltip__value">' + formatNumber(state.sales) +
    '<span style="font-size:13px;font-weight:400;color:var(--muted)"> visitas</span></div>' +
    '<div class="tooltip__rank">#' + rank + ' no ranking</div>';
}

function bindMapInteractions() {
  var tooltip      = document.getElementById("map-tooltip");
  var sortedStates = getSortedStates();

  Object.entries(STATE_DATA).forEach(function(entry) {
    var id    = entry[0];
    var state = entry[1];
    var el    = document.getElementById(id);
    if (!el) return;
    var rank = sortedStates.findIndex(function(s) { return s[0] === id; }) + 1;

    el.addEventListener("mousemove", function(e) {
      tooltip.style.display = "block";
      tooltip.style.left    = (e.clientX + 14) + "px";
      tooltip.style.top     = (e.clientY - 70) + "px";
      tooltip.innerHTML     = buildTooltipHTML(state, rank);
    });

    el.addEventListener("mouseleave", function() {
      tooltip.style.display = "none";
    });

    el.addEventListener("click", function() {
      var item = document.querySelector('[data-id="' + id + '"]');
      if (item) {
        setTimeout(function() {
          item.scrollIntoView({ behavior: "smooth", block: "nearest" });
        }, 50);
      }
    });
  });
}

function carregarEventosDashboard() {

    const idAgencia = sessionStorage.ID_AGENCIA;

    fetch(`/eventos/listarDashboard/${idAgencia}`)

        .then(function(resposta) {

            if (!resposta.ok) {
                throw new Error("Erro ao buscar eventos");
            }

            return resposta.json();
        })

        .then(function(eventos) {

            const container =
                document.getElementById("lista-eventos-dashboard");

            container.innerHTML = "";

            eventos.forEach(function(evento) {

                const hoje = new Date();

                const dataEvento = new Date(evento.dataEvento);

                const diferencaMs =
                    dataEvento.getTime() - hoje.getTime();

                const dias =
                    Math.ceil(diferencaMs / (1000 * 60 * 60 * 24));

                container.innerHTML += `
                    <div class="event-item">
                        <p class="event-item__name">
                            ${evento.nomeEvento} — ${evento.localidade}
                        </p>
                        <span class="event-item__date">
                            em ${dias} dias
                        </span>
                    </div>
                `;
            });

        })

        .catch(function(erro) {
            console.error("Erro ao carregar eventos:", erro);
        });

}

/* === LINE CHART === */
var lineChartInstance = null;

function criarGraficoLinha(labels, data) {
  var ctx = document.getElementById("chartPacotes").getContext("2d");

  if (lineChartInstance) {
    lineChartInstance.destroy();
  }

  lineChartInstance = new Chart(ctx, {
    type: "line",
    data: {
      labels:   labels,
      datasets: [{
        label:                     "Visitas",
        data:                      data,
        borderColor:               "#F5BA5A",
        backgroundColor:           "rgba(245,186,90,0.08)",
        borderWidth:               2,
        pointBackgroundColor:      "#F5BA5A",
        pointRadius:               4,
        pointHoverRadius:          7,
        pointHoverBackgroundColor: "#F5BA5A",
        pointHoverBorderColor:     "#0D1B2A",
        pointHoverBorderWidth:     2,
        fill:                      true,
        tension:                   0.4,
      }],
    },
    options: {
      responsive:          true,
      maintainAspectRatio: false,
      plugins: {
        legend: { display: false },
        tooltip: {
          backgroundColor: "#1A3358",
          titleColor:      "#8FA6C1",
          bodyColor:       "#F5BA5A",
          borderColor:     "rgba(245,186,90,0.4)",
          borderWidth:     1,
          padding:         10,
          cornerRadius:    8,
          displayColors:   false,
          callbacks: {
            label: function(item) {
              return formatNumber(item.raw) + " visitas";
            },
          },
        },
      },
      scales: {
        x: {
          ticks: { color: "#8FA6C1", font: { size: 11 } },
          grid:  { color: "rgba(255,255,255,0.05)" },
        },
        y: {
          ticks:       { color: "#8FA6C1", font: { size: 11 } },
          grid:        { color: "rgba(255,255,255,0.05)" },
          beginAtZero: true,
        },
      },
    },
  });
}
function atualizarGraficoLinha() {
  fetch("/dashboard/historico-visitas")
    .then(function (res) {
      if (res.status === 204) return null;
      if (!res.ok) throw new Error("Erro ao buscar histórico de visitas");
      return res.json();
    })
    .then(function (dados) {
      if (!dados) {
        criarGraficoLinha(HISTORICO_VISITAS_FALLBACK.labels, HISTORICO_VISITAS_FALLBACK.data);
        return;
      }
      criarGraficoLinha(dados.labels, dados.data);
    })
    .catch(function (erro) {
      console.error("Erro ao carregar histórico de visitas:", erro);
      criarGraficoLinha(HISTORICO_VISITAS_FALLBACK.labels, HISTORICO_VISITAS_FALLBACK.data);
    });
}
function agendarProximaAtualizacaoSemestral() {
  var agora = new Date();
  var ano   = agora.getFullYear();
  var mes   = agora.getMonth();

  var proxima = mes < 6
    ? new Date(ano, 6, 1)
    : new Date(ano + 1, 0, 1);

  var delay = proxima.getTime() - agora.getTime();

  setTimeout(function () {
    atualizarGraficoLinha();
    agendarProximaAtualizacaoSemestral();
  }, delay);
}

function renderMonthlySalesChart() {
  atualizarGraficoLinha();
  agendarProximaAtualizacaoSemestral();
}


function carregarTopRegiao() {
  fetch("/dashboard/top-regiao")
    .then(function (res) {
      if (res.status === 204) return null;
      if (!res.ok) throw new Error("Erro ao buscar top região");
      return res.json();
    })
    .then(function (dados) {
      if (!dados || dados.length === 0) return;
      var el = document.getElementById("kpi-top-regiao");
      if (el) el.textContent = dados[0].municipio;
    })
    .catch(function (erro) {
      console.error("Erro ao carregar top região:", erro);
    });
}

function carregarPermanenciaMedia() {
  fetch("/dashboard/duracao-media-evento")
    .then(function (res) {
      if (res.status === 204) return null;
      if (!res.ok) throw new Error("Erro ao buscar duracao media");
      return res.json();
    })
    .then(function (dados) {
      if (!dados) return;
      var el = document.getElementById("kpi-permanencia-media");
      if (el) el.textContent = dados.duracaoMedia + " Dias";
    })
    .catch(function (erro) {
      console.error("Erro ao carregar duracao media:", erro);
    });
}

function carregarGastoMedio() {
  fetch("/dashboard/media-publico-evento")
    .then(function (res) {
      if (res.status === 204) return null;
      if (!res.ok) throw new Error("Erro ao buscar media de publico");
      return res.json();
    })
    .then(function (dados) {
      if (!dados) return;
      var el = document.getElementById("kpi-gasto-medio");
      if (el) el.textContent = dados.mediaPublico.toLocaleString("pt-BR");
    })
    .catch(function (erro) {
      console.error("Erro ao carregar media de publico:", erro);
    });
}

function carregarTaxaConversao() {
  fetch("/dashboard/motivacao-principal")
    .then(function (res) {
      if (res.status === 204) return null;
      if (!res.ok) throw new Error("Erro ao buscar motivacao principal");
      return res.json();
    })
    .then(function (dados) {
      if (!dados) return;
      var el = document.getElementById("kpi-taxa-conversao");
      if (el) el.textContent = dados.motivacao;
    })
    .catch(function (erro) {
      console.error("Erro ao carregar motivacao principal:", erro);
    });
}

function carregarTotalMensalVisitas() {
  fetch("/dashboard/total-mensal-visitas")
    .then(function (res) {
      if (res.status === 204) return null;
      if (!res.ok) throw new Error("Erro ao buscar total mensal de visitas");
      return res.json();
    })
    .then(function (dados) {
      if (!dados) return;
      var el = document.getElementById("kpi-total");
      if (el) el.textContent = dados.totalVisitas.toLocaleString("pt-BR");
    })
    .catch(function (erro) {
      console.error("Erro ao carregar total mensal de visitas:", erro);
    });
}

function initDashboard() {
  colorMapByData();
  bindMapInteractions();
  carregarVisitasPorEstado();
  criarGraficoLinha(HISTORICO_VISITAS_FALLBACK.labels, HISTORICO_VISITAS_FALLBACK.data);
  renderMonthlySalesChart();
  carregarTopRegiao();
  carregarPermanenciaMedia();
  carregarGastoMedio();
  carregarTaxaConversao();
  carregarTotalMensalVisitas();
  carregarEventosDashboard();
}

initDashboard();