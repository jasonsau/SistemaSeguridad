const idContainer = document.getElementById("idContainer");
const idTokenApp = document.getElementById("idTokenApp");
const idLinkTokenApp = document.getElementById("idLinkTokenApp");
const idLinkTokenCorreo = document.getElementById("idLinkTokenCorreo");
const idTokenEmail = document.getElementById("idTokenEmail");
const idTokenSms = document.getElementById("idTokenSms");
let contador = 0;

fetch("http://localhost:8080/api/getMethodsAuthentication", {
    method: "POST"
})
.then(response => response.json())
.then((data) => {
    if(data.secretKeyGoogleAuthenticator !== "") {
        contador = contador + 1;
        idTokenApp.style.display = "block";
        idLinkTokenApp.setAttribute("href", `/authentication/${data.idUser}?tipo=app`);
    }
    if(data.doubleAuthenticationEmail === true) {
        contador = contador + 1;
        idTokenEmail.style.display = "block";
        idLinkTokenCorreo.setAttribute("href", `/authentication/${data.idUser}?tipo=correo`);
    }
    idContainer.style.height = `${(100*contador)+100}px`;
    console.log(data);
})
.catch(error => console.log(error))
