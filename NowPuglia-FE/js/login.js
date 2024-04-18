function handleFormSubmit(event) {
    event.preventDefault();

    let emailAddress = document.querySelector("#emailAddress").value;
    let password = document.querySelector("#password").value;

    let userData = {
        email: emailAddress,
        password: password
    };

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        let response = JSON.parse(xhttp.responseText);
        if (this.readyState == 4) {
            if (this.status == 201) {
                alredy.innerHTML = "Accesso avvenuto <span class=\"text-success\">correttamente</span>!"
                console.log("Risposta JSON:", xhttp.responseText);
                
                emailAddress.value = "";
                password.value = "";
            } else {
                console.error("Si è verificato un errore durante la richiesta POST");
            }
        }
    };

    xhttp.open("POST", "http://localhost:8080/api/auth/login", true);
    xhttp.setRequestHeader("Content-Type", "application/json");

    let jsonData = JSON.stringify(userData);
    xhttp.send(jsonData);
}

document.addEventListener("DOMContentLoaded", function () {
    let registrationForm = document.getElementById("registrationForm");
    registrationForm.addEventListener("submit", handleFormSubmit);
});
