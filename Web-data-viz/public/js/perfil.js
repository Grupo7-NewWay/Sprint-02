const fields = [
  "nome",
  "email",
  "nascimento",
  "telefone",
  "endereco",
  "cep",
  "cidade",
];
let original = {};
let toastTimer = null;

function snapshot() {
  fields.forEach((id) => {
    const el = document.getElementById(id);
    original[id] = el ? el.value : "";
  });
}
snapshot();

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
  const endereco = document.getElementById("endereco").value.trim();
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

  if (!endereco) {
    setError("endereco", "Endereço é obrigatório.");
    valid = false;
  }
  if (!cep) {
    setError("cep", "CEP é obrigatório.");
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

  // ── Update display card
  document.getElementById("displayName").textContent = nome;
  document.getElementById("displayEmail").textContent = email;
  document.getElementById("displayCity").textContent = cidade;
  document.getElementById("avatarEl").textContent = nome
    .charAt(0)
    .toUpperCase();

  if (nasc) {
    const d = new Date(nasc + "T00:00:00");
    const months = [
      "Janeiro",
      "Fevereiro",
      "Março",
      "Abril",
      "Maio",
      "Junho",
      "Julho",
      "Agosto",
      "Setembro",
      "Outubro",
      "Novembro",
      "Dezembro",
    ];
    document.getElementById("displayBirth").textContent =
      `${d.getDate()} de ${months[d.getMonth()]} de ${d.getFullYear()}`;
  }

  snapshot();
  onInput();
  showToast("Perfil atualizado com sucesso!", false);
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

document.getElementById("cep").addEventListener("input", function () {
  let v = this.value.replace(/\D/g, "").slice(0, 8);
  if (v.length > 5) v = v.slice(0, 5) + "-" + v.slice(5);
  if (!this.value.includes("SP")) this.value = v;
});

document.getElementById("telefone").addEventListener("input", function () {
  let v = this.value.replace(/\D/g, "").slice(0, 11);
  if (v.length >= 11)
    v = `(${v.slice(0, 2)}) ${v.slice(2, 3)} ${v.slice(3, 7)}-${v.slice(7)}`;
  else if (v.length >= 7)
    v = `(${v.slice(0, 2)}) ${v.slice(2, 6)}-${v.slice(6)}`;
  else if (v.length >= 3) v = `(${v.slice(0, 2)}) ${v.slice(2)}`;
  if (!this.value.includes("+")) this.value = v;
});
