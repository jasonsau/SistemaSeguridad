
 const idMainFirst = document.getElementById("idMainFirst");
 const idMainSecond = document.getElementById("idMainSecond");
 const idModalConfigurar = document.getElementById("idModalConfigurar");
 const idModalBarCode = document.getElementById("idModalBarCode");
 const idCloseFormModal = document.getElementById("idCloseFormModal");
 const idButtonCodigo = document.getElementById("idButtonCodigo");
 const idInputCodigo = document.getElementById("idInputCodigo");
 const idContainerError = document.getElementById("idContainerError");
 const idMessageError = document.getElementById("idMessageError");
 const idContainerEmail = document.getElementById("idContainerEmail");
 const idInputCorreo = document.getElementById("idInputCorreo");
 const idCloseFormModalEmail = document.getElementById("idCloseFormModalEmail")
 const idContainerEmailError = document.getElementById("idContainerEmailError")
 const idMessageEmailError = document.getElementById("idMessageEmailError")
 const idButtonEmail = document.getElementById("idButtonEmail");
 const idContainerMessage = document.getElementById("idContainerMessage");
 const idCloseFormMessage = document.getElementById("idCloseFormMessage");
 const idButtonAceptarMessage = document.getElementById("idButtonAceptarMessage");
 const idLinkHome = document.getElementById("idLinkHome");
 const idContainerSms = document.getElementById("idContainerSms");
 const idCloseFormModalSms = document.getElementById("idCloseFormModalSms");
 const idInputSms = document.getElementById("idInputSms");
 const idButtonSms = document.getElementById("idButtonSms");
 const idContainerSmsError = document.getElementById("idContainerSmsError");
 const idMessageSmsError = document.getElementById("idMessageSmsError");

 idButtonCodigo.disabled = true;
 idButtonEmail.disabled = true;
 idButtonCodigo.style.backgroundColor = "#a8a8ad";
 idButtonEmail.style.backgroundColor = "#a8a8ad";
 idButtonSms.style.backgroundColor = "#a8a8ad";

 idInputCodigo.addEventListener('input', (e) => {
     let regex = /\D/g;
     idInputCodigo.value = idInputCodigo.value.replaceAll(regex, '');
     let regexlimit = /\d{6}/;
     if(regexlimit.test(idInputCodigo.value)) {
         idButtonCodigo.disabled = false;
         idButtonCodigo.style.backgroundColor = "blue";
     } else {
         idButtonCodigo.disabled = true;
         idButtonCodigo.style.backgroundColor = "#a8a8ad";
     }
 });

 idInputCorreo.addEventListener('input', (e) => {
     let regex = /\D/g;
     idInputCorreo.value = idInputCorreo.value.replaceAll(regex, '');
     let regexlimit = /\d{6}/;
     if(regexlimit.test(idInputCorreo.value)) {
         idButtonEmail.disabled = false;
         idButtonEmail.style.backgroundColor = "blue";
     } else {
         idButtonEmail.disabled = true;
         idButtonEmail.style.backgroundColor = "#a8a8ad";
     }
 });

 idInputSms.addEventListener('input', (e) => {
     let regex = /\D/g;
     idInputSms.value = idInputSms.value.replaceAll(regex, '');
     let regexlimit = /\d{6}/;
     if(regexlimit.test(idInputSms.value)) {
         idButtonSms.disabled = false;
         idButtonSms.style.backgroundColor = "blue";
     } else {
         idButtonSms.disabled = true;
         idButtonSms.style.backgroundColor = "#a8a8ad"
     }
 })


//Se inicializa cuales metodos no estan configurados y cuales si
const initMethods = () => {
    fetch("http://localhost:8080/api/getMethods2Fac", {
        method: "POST"
    })
        .then(response => response.json())
        .then((data) => {
            let contadorMethods = 0;
            if(data.doubleAuthenticationApp) {
                methodOption("Aplicacion de Autenticacion",
                    "Puede usar las aplicacion de Google Authenticator o Authy",
                    "qrCode.png", "app")
                contadorMethods += 1;
            } else {
                methodOptionOptional("Aplicacion de Autenticacion",
                    "Puede usar las aplicacion de Google Authenticator o Authy",
                    "qrCode.png",
                    "app");
            }
            if(data.doubleAuthenticationEmail === true) {
                methodOption("Correo Electronico",
                    "El codigo se le enviara a su correo",
                    "correo.png",
                    "correo");
                contadorMethods += 1;
            } else {
                methodOptionOptional("Correo Electronico",
                    "El codigo se le enviara a su correo",
                    "correo.png",
                    "correo");
            }
            if(data.doubleAuthenticationSms === true) {
                methodOption("Mensaje de Texto",
                    "El codigo se le enviara por un mensaje de SMS",
                    "sms.jpg",
                    "sms");
                contador += 1;
            } else {
                methodOptionOptional("Mensaje de Texto",
                    "El codigo se le enviara por un mensaje de SMS",
                    "sms.jpg",
                    "sms");
            }

            if(contadorMethods === 0) {
                idMainFirst.innerHTML += `<p style="text-align: center; font-size: 15px">Aun no tiene registrado ningun metodo</p>`;
            }else {
                idLinkHome.setAttribute("href", "/userAccount");
            }

        })
        .catch(error => console.log(error))
}
initMethods();

const methodOption = (title, description, img, tipo) => {
    idMainFirst.innerHTML += `
        <div class = "main__methods">
            <div class = "main__method">
                <img 
                    class = "main__img" 
                    src = "/img/${img}"
                >
                <p class = "main__text">${title}</p>
                <p class = "main__text--description">${description}</p>
            </div>
            <div class = "main__options">
                <p 
                    class = "main__option"
                    option = "desactivar"
                    tipo = "${tipo}"
                >
                    Desactivar
                </p>
            </div>
        </div>
    `;
}

const methodOptionOptional = (title, description, img, tipo) => {
    idMainSecond.innerHTML += `
        <div class = "main__methods">
            <div class = "main__method">
                <img class = "main__img" src = "/img/${img}">
                <p class = "main__text">${title}</p>
                <p class = "main__text--description">${description}</p>
            </div>
            <div class = "main__options">
                <p 
                    class = "main__option"
                    option = "configurar"
                    tipo = "${tipo}"
                >
                    Configurar</p>
            </div>
        </div>
    `;
}

idMainFirst.addEventListener('click', (e) => {
    if(e.target.getAttribute("tipo") === 'app') {
        if(e.target.getAttribute("option") === 'desactivar') {
            idButtonAceptarMessage.setAttribute("option", "app");
            idContainerMessage.classList.add("open-modal-configurar");
            idContainerMessage.classList.remove("close-modal-configurar");
        }
    }
    if(e.target.getAttribute("tipo") === 'correo') {
        idButtonAceptarMessage.setAttribute("option", "correo");
        idContainerMessage.classList.add("open-modal-configurar");
        idContainerMessage.classList.remove("close-modal-configurar");
    }
    if(e.target.getAttribute('tipo') === 'sms') {
        idButtonAceptarMessage.setAttribute("option", "sms");
        idContainerMessage.classList.add("open-modal-configurar");
        idContainerMessage.classList.remove("close-modal-configurar");
    }
});

idButtonAceptarMessage.addEventListener('click', () => {
    if(idButtonAceptarMessage.getAttribute('option') === 'app') {
        fetch("http://localhost:8080/api/disabled-method/app", {
            method: "POST"
        })
            .then(response => response.json())
            .then(data => {
                if(data.res) {
                    idContainerMessage.classList.add("close-modal-configurar");
                    idContainerMessage.classList.remove("open-modal-configurar");
                    location.reload(true);
                }
            });
    }
    if(idButtonAceptarMessage.getAttribute('option') === 'correo') {
        fetch("http://localhost:8080/api/disabled-method/correo", {
            method: "POST"
        })
            .then(response => response.json())
            .then(data => {
                if(data.res) {
                    idContainerMessage.classList.add("close-modal-configurar");
                    idContainerMessage.classList.remove("open-modal-configurar");
                    location.reload(true);
                }
            });

    }

    if(idButtonAceptarMessage.getAttribute('option') === 'sms') {
        fetch("http://localhost:8080/api/disabled-method/sms", {
            method: "POST"
        })
            .then(response => response.json())
            .then(data => {
                if(data.res) {
                    idContainerMessage.classList.add("close-modal-configurar");
                    idContainerMessage.classList.remove("open-modal-configurar");
                    location.reload(true);
                }
            });

    }
});

idMainSecond.addEventListener('click', (e) => {
    if(e.target.getAttribute('option') === 'configurar') {
        if(e.target.getAttribute('tipo') === 'correo') {
            idContainerEmail.classList.add("open-modal-configurar");
            idContainerEmail.classList.remove("close-modal-configurar");
            setTimeout(() => {
                fetch("http://localhost:8080/api/send-email-token", {
                    method: "POST"
                })
                    .then(response => response.json())
                    .catch(error => console.log(error));
            }, 1000)
        }
        if(e.target.getAttribute('tipo') === "app") {
            fetch("http://localhost:8080/create-secret-key")
                .then(response => response.json())
                .then((data) => {
                    if(data.message === 'Creado') {
                        idModalBarCode.setAttribute("src", "/barCode");
                        idModalConfigurar.classList.add("open-modal-configurar");
                        idModalConfigurar.classList.remove("close-modal-configurar");
                    }
                })
                .catch(error => console.log(error));
            console.log("Configuraemos el metodo de app");
        }
        if(e.target.getAttribute('tipo') === 'sms') {
            idContainerSms.classList.add("open-modal-configurar");
            idContainerSms.classList.remove("close-modal-configurar");

            setTimeout( () => {
                fetch("http://localhost:8080/api/send-sms-token", {
                    method: "POST"
                })
                    .then(response => response.json())
                    .then(data => console.log(data))
                    .catch(error => console.log(error));

            },1000)
            console.log("Se quiere configurar");
        }
    }
});

idButtonSms.addEventListener('click', () => {
    data = {"code": idInputSms.value};
    fetch("http://localhost:8080/api/verified-code-sms", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            if(data.res === "Correcto") {
                idContainerSmsError.style.backgroundColor = "green";
                idContainerSmsError.style.display = "block";
                idMessageSmsError.innerText = "Se ha configurado";
                idInputSms.value = "";
                idInputSms.dissable = true;
                idMainFirst.innerHTML = `<h2 class = "main__subtitle">Tus metodos de Seguridad</h2>`;
                idMainSecond.innerHTML = `<h2 class = "main__subtitle">Metodos Adicionales</h2>`;
                setInterval(() => {
                    idContainerSms.classList.remove("open-modal-configurar");
                    idContainerSms.classList.add("close-modal-configurar")
                }, 1000)
                initMethods();
            }

            if(data.res === "Incorrecto") {
                idContainerSmsError.style.backgroundColor = "red";
                idContainerSmsError.style.display = "block";
                idMessageSmsError.innerText = "El codigo es incorrecto";
            }
        })
 });


idButtonEmail.addEventListener('click', () => {
    const data = {"code": idInputCorreo.value};
    fetch("http://localhost:8080/api/verified-code-email", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if(data.messageT === 'correcto') {
                idContainerEmailError.style.background = "green";
                idContainerEmailError.style.display = "block";
                idMessageEmailError.innerText = "Se ha configurado";
                idInputCorreo.value=""
                idInputCorreo.dissable=true
                idMainFirst.innerHTML = `<h2 class = "main__subtitle">Tus metodos de Seguridad</h2>`;
                idMainSecond.innerHTML = `<h2 class = "main__subtitle">Metodos Adicionales</h2>`;
                setInterval(() => {
                    idContainerEmail.classList.remove("open-modal-configurar");
                    idContainerEmail.classList.add("close-modal-configurar")
                }, 1000)
                initMethods();
            } else {
                idContainerEmailError.style.display = "block";
                idMessageEmailError.innerText = "El codigo es incorrecto";
            }

        })
        .catch(error => console.log(error));
});

idCloseFormModal.addEventListener('click', () =>{
    idModalConfigurar.classList.add("close-modal-configurar");
    idModalConfigurar.classList.remove("open-modal-configurar");
    window.location.reload(true);
});


idCloseFormMessage.addEventListener('click', () => {
    idContainerMessage.classList.add("close-modal-configurar");
    idContainerMessage.classList.remove("open-modal-configurar");
})

idCloseFormModalEmail.addEventListener('click', () => {
    idContainerEmail.classList.add("close-modal-configurar");
    idContainerEmail.classList.remove("open-modal-configurar");
});

idCloseFormModalSms.addEventListener('click', () => {
    idContainerSms.classList.add("close-modal-configurar");
    idContainerSms.classList.remove("open-modal-configurar");
})

idButtonCodigo.addEventListener('click', (e) => {
    e.preventDefault()
    const data = {"code": idInputCodigo.value};

    fetch("http://localhost:8080/api/verification-code-app", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then((data) => {
            if(data.messageSend === "NoValido") {
                console.log("Entra al if");
                idContainerError.style.display = "block";
                idMessageError.innerText = "El codigo es incorrecto";
            }
            if(data.messageSend === "creado") {
                idModalConfigurar.style.height= "200px";
                idModalConfigurar.innerHTML = modalMessage();
                const idButtonOk = document.getElementById("idButtonOk");
                idButtonOk.addEventListener('click', () => {
                    idModalConfigurar.classList.add("close-modal-configurar");
                    idModalConfigurar.classList.remove("open-modal-configurar");
                    idModalConfigurar.innerHTML = modalConfigurarApp();
                    idMainFirst.innerHTML = `<h2 class = "main__subtitle">Tus metodos de Seguridad</h2>`;
                    idMainSecond.innerHTML = `<h2 class = "main__subtitle">Metodos Adicionales</h2>`;
                    initMethods();
                });
            }
        })
        .catch((error) => console.log(error));
});

const modalMessage = () => {
    return `
        <div style = "position: relative; top: 20px;">
            <h2>Se ha registrado el metodo</h2>
       </div>
       <div style="position: relative; top: 40px">
            <input id = "idButtonOk" type = "submit" class = "form__button" value = "Aceptar" style="position: relative">
       </div>
    `;
}


const modalConfigurarApp = () => {
    return `
        <img
                class = "form__close"
                src = "/icons/close.svg"
                id = "idCloseFormModal"
        >
        <h2 class = "modal__subtitle">Autenticacion App</h2>
        <div>
            <img
                    id = "idModalBarCode"
                    class = "modal__img"
            >
        </div>
        <div
                class = "container__input"
                style="margin-top: 15px"
        >
            <label for = "idInputCodigo" class = "form__label">
                <img 
                src = "/icons/password.svg"
                >
            </label>
            <input
                    type = "text"
                    id = "idInputCodigo"
                    placeholder="Codigo"
                    class = "form__input"
            >
        </div>
        <div
                class = "container__input container__input--error"
                style="margin-top: 15px; margin-bottom: -15px;"
                id = "idContainerError"
        >
            <p id = "idMessageError">Error</p>
        </div>
        <div
                class = "container__input container__input--modal"
                style="margin-top: 15px"
        >
            <input
                    type = "submit"
                    id = "idButtonCodigo"
                    value = "Configurar"
                    class = "form__button"
                    style="margin-top: 5px"
            >
        </div>
    `
}