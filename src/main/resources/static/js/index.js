const idButtonModalUnlocked = document.getElementById("idButtonModalUnlocked");
const idFormModal = document.getElementById("idFormModal");
const idCloseFormModal = document.getElementById("idCloseFormModal");

const idInputUnlockedEmail = document.getElementById("idInputUnlockedEmail");
const idButtonUnlockedUser = document.getElementById("idButtonUnlockedUser");
const idErrorEmail = document.getElementById("idErrorEmail");
const idErrorContainerEmail = document.getElementById("idErrorContainerEmail");

idButtonModalUnlocked.addEventListener('click', (e) => {
    e.preventDefault();
    idFormModal.classList.add("open__modal");
    idFormModal.classList.remove("close_modal");

})


idCloseFormModal.addEventListener('click', (e) => {
    e.preventDefault();
    idFormModal.classList.add("close_modal");
    idErrorContainerEmail.style.display = "none";
    idInputUnlockedEmail.value = "";
})


idButtonUnlockedUser.addEventListener('click', (e) => {
    e.preventDefault();
    const data = {"email": idInputUnlockedEmail.value};
    fetch('http://localhost:8080/unlocked-user', {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {'Content-Type': 'application/json'}
    })
        .then((response) => response.json())
        .then((data) => {
            if(data.number === '1') {
                idErrorContainerEmail.style.background = "green";
            } else if(data.number === '2' || data.number === '3') {
                idErrorContainerEmail.style.background = "red";
            }

            idErrorContainerEmail.style.display = "block";
            idErrorEmail.innerText = data.message;
            console.log(data.message);
        })
        .catch((error) => {
            console.log(error)
        });
})

