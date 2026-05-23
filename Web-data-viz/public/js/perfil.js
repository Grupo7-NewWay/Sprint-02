var isPrimeiroAcesso = false;

function ativarBloqueio() {
  isPrimeiroAcesso = true;
  document.getElementById("modalPrimeiroAcesso").classList.add("open");

  document.querySelectorAll(".navbar a:not(.active)").forEach(function (link) {
    link.addEventListener("click", bloquearNavegacao);
  });

  window.addEventListener("beforeunload", bloquearUnload);
}

function desativarBloqueio() {
  isPrimeiroAcesso = false;
  sessionStorage.removeItem("PRIMEIRO_ACESSO");

  document.querySelectorAll(".navbar a:not(.active)").forEach(function (link) {
    link.removeEventListener("click", bloquearNavegacao);
  });

  window.removeEventListener("beforeunload", bloquearUnload);
}

function fecharPopupBoasVindas() {
  document.getElementById("modalPrimeiroAcesso").classList.remove("open");
  document.getElementById("nome").focus();
}

function bloquearNavegacao(e) {
  e.preventDefault();
  showToast("Complete seu cadastro antes de continuar.", true);
}

function bloquearUnload(e) {
  e.preventDefault();
  e.returnValue = "";
}

const fields = [
  "nome",
  "email",
  "nascimento",
  "telefone",
  "cep",
  "longradouro",
  "numero",
  "complemento",
  "bairro",
  "cidade",
  "estado",
];
let original = {};
let toastTimer = null;

function snapshot() {
  fields.forEach((id) => {
    const el = document.getElementById(id);
    original[id] = el ? el.value : "";
  });
}

function preencherCard(nome, email, nascimento, cidade) {
  const meses = ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"];
  document.getElementById("displayName").textContent  = nome  || "";
  document.getElementById("displayEmail").textContent = email || "";
  document.getElementById("displayCity").textContent  = cidade || "";
  document.getElementById("avatarEl").textContent     = nome ? nome.charAt(0).toUpperCase() : "?";
  if (nascimento) {
    const d = new Date(nascimento + "T00:00:00");
    document.getElementById("displayBirth").textContent =
      `${d.getDate()} de ${meses[d.getMonth()]} de ${d.getFullYear()}`;
  }
}

function carregarPerfil() {
  var id = sessionStorage.getItem("ID_USUARIO");
  if (!id) return;

  fetch("/usuarios/perfil/" + id)
    .then(function (res) {
      if (!res.ok) throw new Error("Perfil não encontrado");
      return res.json();
    })
    .then(function (dados) {
      document.getElementById("nome").value         = dados.nomeAgencia  || "";
      document.getElementById("email").value        = dados.email        || "";
      document.getElementById("nascimento").value   = dados.dataNascimento ? dados.dataNascimento.split("T")[0] : "";
      document.getElementById("telefone").value     = dados.telefone     || "";
      document.getElementById("cep").value          = dados.cep          || "";
      document.getElementById("longradouro").value  = dados.logradouro   || "";
      document.getElementById("numero").value       = dados.numero       || "";
      document.getElementById("complemento").value  = dados.complemento  || "";
      document.getElementById("bairro").value       = dados.bairro       || "";
      document.getElementById("cidade").value       = dados.cidade       || "";
      if (dados.estado) document.getElementById("estado").value = dados.estado;

      preencherCard(dados.nomeAgencia, dados.email, dados.dataNascimento ? dados.dataNascimento.split("T")[0] : null, dados.cidade);
      snapshot();

      var ehPrimeiro = sessionStorage.getItem("PRIMEIRO_ACESSO") === "true" || !dados.cep;
      if (ehPrimeiro) ativarBloqueio();
    })
    .catch(function (erro) {
      console.error("Erro ao carregar perfil:", erro);
    });
}

carregarPerfil();

fields.forEach((id) => {
  const el = document.getElementById(id);
  if (!el) return;
  el.addEventListener("input", () => {
    el.classList.remove("error");
    const hint = el.parentElement.querySelector(".field-error");
    if (hint) hint.remove();
    onInput();
  });
  el.addEventListener("change", onInput);
});

function onInput() {
  const hasChange = fields.some((id) => {
    const el = document.getElementById(id);
    return el && el.value !== original[id];
  });
  document
    .getElementById("changedBadge")
    .classList.toggle("visible", hasChange);

  fields.forEach((id) => {
    const el = document.getElementById(id);
    if (!el) return;
    el.classList.toggle("changed", el.value !== original[id]);
  });
}

function setError(id, msg) {
  const el = document.getElementById(id);
  if (!el) return;
  el.classList.add("error");
  let hint = el.parentElement.querySelector(".field-error");
  if (!hint) {
    hint = document.createElement("span");
    hint.className = "field-error";
    el.parentElement.appendChild(hint);
  }
  hint.textContent = msg;
}

function clearError(id) {
  const el = document.getElementById(id);
  if (!el) return;
  el.classList.remove("error");
  const hint = el.parentElement.querySelector(".field-error");
  if (hint) hint.remove();
}

function saveProfile() {
  let valid = true;

  fields.forEach(clearError);

  const nome = document.getElementById("nome").value.trim();
  const email = document.getElementById("email").value.trim();
  const nasc = document.getElementById("nascimento").value;
  const telefone = document.getElementById("telefone").value.trim();
  const longradouro = document.getElementById("longradouro").value.trim();
  const cep = document.getElementById("cep").value.trim();
  const cidade = document.getElementById("cidade").value.trim();

  if (!nome) {
    setError("nome", "Nome é obrigatório.");
    valid = false;
  } else if (nome.length < 3) {
    setError("nome", "Nome muito curto (mínimo 3 caracteres).");
    valid = false;
  }

  const emailRe = /^[^\s@]+@[^\s@]+\.com$/i;
  if (!email) {
    setError("email", "E-mail é obrigatório.");
    valid = false;
  } else if (!emailRe.test(email)) {
    setError("email", "E-mail deve ser válido e terminar em .com");
    valid = false;
  }

  if (!nasc) {
    setError("nascimento", "Data de nascimento é obrigatória.");
    valid = false;
  } else {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const birth = new Date(nasc + "T00:00:00");
    if (birth >= today) {
      setError("nascimento", "Data deve ser no passado.");
      valid = false;
    } else {
      let age = today.getFullYear() - birth.getFullYear();
      const m = today.getMonth() - birth.getMonth();
      if (m < 0 || (m === 0 && today.getDate() < birth.getDate())) age--;
      if (age < 18) {
        setError("nascimento", "Você precisa ter pelo menos 18 anos.");
        valid = false;
      }
    }
  }

  if (telefone) {
    const digits = telefone.replace(/\D/g, "");
    if (digits.length < 10) {
      setError("telefone", "Telefone incompleto.");
      valid = false;
    }
  }

  if (!longradouro) {
    setError("longradouro", "Logradouro é obrigatório.");
    valid = false;
  }

  // ── Validação de CEP aprimorada
  const cepDigits = cep.replace(/\D/g, "");
  if (!cep) {
    setError("cep", "CEP é obrigatório.");
    valid = false;
  } else if (cepDigits.length !== 8) {
    setError("cep", "CEP inválido (deve ter 8 dígitos).");
    valid = false;
  }

  if (!cidade) {
    setError("cidade", "Cidade é obrigatória.");
    valid = false;
  }

  if (!valid) {
    showToast("Corrija os campos destacados.", true);
    return;
  }

  var id = sessionStorage.getItem("ID_USUARIO");
  if (!id) { showToast("Sessão expirada. Faça login novamente.", true); return; }

  fetch("/usuarios/perfil/" + id, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      nomeAgencia:    nome,
      email:          email,
      telefone:       telefone,
      dataNascimento: nasc || null,
      cep:            cep,
      logradouro:     longradouro,
      numero:         document.getElementById("numero").value.trim(),
      complemento:    document.getElementById("complemento").value.trim(),
      bairro:         document.getElementById("bairro").value.trim(),
      cidade:         cidade,
      estado:         document.getElementById("estado").value
    })
  })
    .then(function (res) {
      if (!res.ok) return res.json().then(function (d) { throw new Error(d.mensagem); });
      return res.json();
    })
    .then(function () {
      preencherCard(nome, email, nasc, cidade);
      snapshot();
      onInput();
      if (isPrimeiroAcesso) desativarBloqueio();
      showToast("Perfil atualizado com sucesso!", false);
    })
    .catch(function (erro) {
      showToast("Erro ao salvar: " + erro.message, true);
    });
}

function cancelEdit() {
  fields.forEach((id) => {
    const el = document.getElementById(id);
    if (el) {
      el.value = original[id];
      el.classList.remove("changed", "error");
    }
    clearError(id);
  });
  document.getElementById("changedBadge").classList.remove("visible");
}

function showToast(msg, isError) {
  const toast = document.getElementById("toast");
  document.getElementById("toastMsg").textContent = msg;
  toast.style.borderColor = isError ? "#f06b6b" : "var(--success)";
  toast.querySelector(".toast-icon").textContent = isError ? "✕" : "✓";
  toast.querySelector(".toast-icon").style.color = isError
    ? "#f06b6b"
    : "var(--success)";
  toast.classList.add("show");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => toast.classList.remove("show"), 3200);
}

// ── Máscara do CEP
document.getElementById("cep").addEventListener("input", function () {
  let v = this.value.replace(/\D/g, "").slice(0, 8);
  if (v.length > 5) v = v.slice(0, 5) + "-" + v.slice(5);
  if (!this.value.includes("SP")) this.value = v;
});

// ── Integração ViaCEP: dispara ao sair do campo (blur)
document.getElementById("cep").addEventListener("blur", async function () {
  const cepDigits = this.value.replace(/\D/g, "");

  if (cepDigits.length !== 8) return;

  // Feedback visual de carregamento
  this.disabled = true;
  this.style.opacity = "0.6";

  try {
    const response = await fetch(
      `https://viacep.com.br/ws/${cepDigits}/json/`
    );

    if (!response.ok) throw new Error("Erro na requisição");

    const data = await response.json();

    if (data.erro) {
      setError("cep", "CEP não encontrado.");
      return;
    }

    // Preenche campos automaticamente
    if (data.logradouro) {
      document.getElementById("longradouro").value = data.logradouro;
    }
    if (data.bairro) {
      document.getElementById("bairro").value = data.bairro;
    }
    if (data.localidade) {
      document.getElementById("cidade").value = data.localidade;
    }
    if (data.uf) {
      document.getElementById("estado").value = data.uf;
    }

    // Trava os campos preenchidos pelo ViaCEP
    ["longradouro", "bairro", "cidade", "estado"].forEach((id) => {
      const el = document.getElementById(id);
      el.readOnly = true;
      el.classList.add("readonly-viacep");
    });

    // Limpa número e complemento para o usuário preencher
    document.getElementById("numero").value = "";
    document.getElementById("complemento").value = "";

    clearError("cep");
    onInput(); // atualiza badge de alterações

    // Foca no campo número para o usuário completar
    document.getElementById("numero").focus();
  } catch (e) {
    setError("cep", "Erro ao buscar CEP. Tente novamente.");
  } finally {
    // Remove feedback visual de carregamento
    this.disabled = false;
    this.style.opacity = "";
  }
});

function openDeleteModal() {
  document.getElementById("deleteModal").classList.add("open");
}

function closeDeleteModal() {
  document.getElementById("deleteModal").classList.remove("open");
}

function confirmDelete() {
  var id = sessionStorage.getItem("ID_USUARIO");

  if (!id) {
    closeDeleteModal();
    showToast("Sessão expirada. Faça login novamente.", true);
    return;
  }

  fetch("/usuarios/excluir/" + id, { method: "DELETE" })
    .then(function (res) {
      if (!res.ok) return res.json().then(function (d) { throw new Error(d.mensagem); });
      return res.json();
    })
    .then(function () {
      closeDeleteModal();
      sessionStorage.clear();
      showToast("Conta excluída com sucesso.", false);
      setTimeout(function () { window.location.href = "login.html"; }, 2000);
    })
    .catch(function (erro) {
      closeDeleteModal();
      showToast("Erro ao excluir conta: " + erro.message, true);
    });
}

// ── Fecha modal ao clicar fora
document.getElementById("deleteModal").addEventListener("click", function (e) {
  if (e.target === this) closeDeleteModal();
});

// ── Máscara do telefone
document.getElementById("telefone").addEventListener("input", function () {
  let v = this.value.replace(/\D/g, "").slice(0, 11);
  if (v.length >= 11)
    v = `(${v.slice(0, 2)}) ${v.slice(2, 3)} ${v.slice(3, 7)}-${v.slice(7)}`;
  else if (v.length >= 7)
    v = `(${v.slice(0, 2)}) ${v.slice(2, 6)}-${v.slice(6)}`;
  else if (v.length >= 3) v = `(${v.slice(0, 2)}) ${v.slice(2)}`;
  if (!this.value.includes("+")) this.value = v;
});