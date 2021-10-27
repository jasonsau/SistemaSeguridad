const params = window.location.search;

const idFormAuthentication = document.getElementById("idFormAuthentication");

idFormAuthentication.setAttribute("action", `/verification-code${params}`);