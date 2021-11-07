const params = window.location.search;

const idFormAuthentication = document.getElementById("idFormAuthentication");
const idCodigo = document.getElementById("idCodigo");
const buttonVerification = document.getElementById("buttonVerification");
buttonVerification.disabled = true;

idFormAuthentication.setAttribute("action", `/verification-code${params}`);

idCodigo.addEventListener("input", (e) => {
    let regex = /\D/g
    idCodigo.value =  idCodigo.value.replaceAll(regex, '');

    let regexlimit = /\d{6}/
    if(regexlimit.test(idCodigo.value)) {
        buttonVerification.disabled = false;
    }
});