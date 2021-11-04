const idMainFirst = document.getElementById("idMainFirst");
const idMainSecond = document.getElementById("idMainSecond");
const idModalConfigurar = document.getElementById("idModalConfigurar");
const idModalBarCode = document.getElementById("idModalBarCode");
const idCloseFormModal = document.getElementById("idCloseFormModal");
const idButtonCodigo = document.getElementById("idButtonCodigo");
const idInputCodigo = document.getElementById("idInputCodigo");
const idContainerError = document.getElementById("idContainerError");
const idMessageError = document.getElementById("idMessageError");

const initMethods = () => {
    fetch("http://localhost:8080/api/getMethods2Fac", {
        method: "POST"
    })
        .then(response => response.json())
        .then((data) => {
            if(data.doubleAuthenticationApp) {
                methodOption("Aplicacion de Autenticacion", "Puede usar las aplicacion de Google Authenticator o Authy", "qrCode.png", "app")
            } else {
                methodOptionOptional("Aplicacion de Autenticacion",
                    "Puede usar las aplicacion de Google Authenticator o Authy",
                    "qrCode.png",
                    "app");
            }
            if(data.doubleAuthenticationEmail === true) {
                methodOption("Correo Electronico", "El codigo se le enviara a su correo", "correo.png", "correo")
            } else {
                methodOptionOptional("Correo Electronico", "El codigo se le enviara a su correo", "correo.png", "correo")
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
                    option = "editar"
                    tipo = "${tipo}"
                >
                    Editar
                </p>
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
   if(e.target.getAttribute("option")==='editar') {
       console.log("Se esta editando");
   }


});

idMainSecond.addEventListener('click', (e) => {
   if(e.target.getAttribute('option') === 'configurar') {
       if(e.target.getAttribute('tipo') === 'correo') {
           console.log("Configurare el metodo de correo");
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
   }
})


idCloseFormModal.addEventListener('click', () =>{
    idModalConfigurar.classList.add("close-modal-configurar");
    idModalConfigurar.classList.remove("open-modal-configurar");
});


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
})

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
                <img src = "/icons/password.svg"
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

