
const maskCnpj = document.getElementById("input_cnpj");
const maskTel = document.getElementById("input_whatsapp");
var validPassword = false;
var validEmail = false;
var validTel = false;
var validCnpj = false;

function cnpj(value) {
    value = value.replace(/\D/g, "");
    value = value.replace(/(\d{2})(\d)/, "$1.$2");
    value = value.replace(/(\d{2}\.\d{3})(\d)/, "$1.$2");
    value = value.replace(/(\d{2}\.\d{3}\.\d{3})(\d)/, "$1/$2");
    value = value.replace(/(\d{2}\.\d{3}\.\d{3}\/\d{4})(\d)/, "$1-$2");
    return value;
}

maskCnpj.addEventListener("input", function (event) {
    const cleanedInput = event.target.value.replace(/\D/g, "");
    event.target.value = cnpj(cleanedInput);
});

function telefone(value) {
    value = value.replace(/\D/g, "");
    value = value.replace(/(\d{2})(\d)/, "($1) $2");
    value = value.replace(/(\(\d{2}\) \d{5})(\d)/, "$1-$2");
    return value;
}

maskTel.addEventListener("input", function (event) {
    const cleanedInput = event.target.value.replace(/\D/g, "");
    event.target.value = telefone(cleanedInput);
});

const emailInput = document.getElementById("input_email");
const emailError = document.getElementById("email-error");

emailInput.addEventListener("input", function () {
    const value = emailInput.value.trim();

    if (!value) {
        emailError.textContent = "O e-mail é obrigatório.";
        validEmail = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
        emailError.textContent = "Digite um e-mail válido.";
        validEmail = false;
    } else {
        emailError.textContent = "";
        validEmail = true;
    }
});

const passwordInput = document.getElementById("input_password");
const passwordError = document.getElementById("password-error");

passwordInput.addEventListener("input", function () {
    const value = passwordInput.value;

    if (!value) {
        passwordError.textContent = "Insira uma senha.";
        validPassword = false;
    } else if (value.length < 8) {
        passwordError.textContent = "A senha deve ter no mínimo 8 caracteres.";
        validPassword = false;
    } else if (!/[A-Z]/.test(value)) {
        passwordError.textContent = "A senha deve conter pelo menos 1 letra maiúscula.";
        validPassword = false;
    } else if (!/[^a-zA-Z0-9]/.test(value)) {
        passwordError.textContent = "A senha deve conter pelo menos 1 caractere especial.";
        validPassword = false;
    } else {
        passwordError.textContent = "";
        validPassword = true;
    }
});

function register() {
        var name = document.getElementById("input_agency").value
        var cnpj = document.getElementById("input_cnpj").value
        var tel = document.getElementById("input_whatsapp").value

        if (cnpj != "" && cnpj.length == 18) {
            validCnpj = true;
        } else {
            validCnpj = false;
        }

        if (tel != "" && tel.length == 15) {
            validTel = true;
        } else {
            validTel = false;
        }

        if (name == "" || validEmail == false || validPassword == false || validCnpj == false || validTel == false) {
            alert('Cadastro Inválido! Confira os campos');
        } else {
            alert('Cadastro Efetuado!');
            window.location.href = 'login.html';
        }

        
            
        
}