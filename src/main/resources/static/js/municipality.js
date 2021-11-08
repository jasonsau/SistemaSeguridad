
const selectMunicipality = document.getElementById('selectMunicipality');

const selectDepartament = document.getElementById('selectDepartament');

selectDepartament.addEventListener('change', (e) => {
    selectMunicipality.innerHTML = "";
    fetch(`http://localhost:8080/api/getMunicipality/${e.target.value}`,{
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => {
            data.forEach((municipality) => {
                selectMunicipality.innerHTML +=
                    `<option value = "${municipality.idMunicipality}">${municipality.nameMunicipality}</option>`
            });
        });

});

