function handleFormSubmit(event) {
  event.preventDefault();

  let firstName = document.querySelector("#firstName").value;
  let lastName = document.querySelector("#lastName").value;
  let birthdayDate = document.querySelector("#birthdayDate").value;
  let emailAddress = document.querySelector("#emailAddress").value;
  let userType = document.querySelector(".select").value;
  let password = document.querySelector("#password").value;
  let alredy = document.getElementById("alredy");
  
  let userData = {
    nome: firstName,
    cognome: lastName,
    dataNascita: birthdayDate,
    email: emailAddress,
    password: password,
    tipologiaUtente: parseInt(userType) - 1
  };

  let xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4) {
      if (this.status == 201) {
        let response = JSON.parse(xhttp.responseText);
        alredy.innerHTML = response.payload.nome + " ti sei registrato <span class=\"text-success\">correttamente</span>!"
        console.log("Risposta JSON:", xhttp.responseText);

        firstName.value = "";
        lastName.value = "";
        birthdayDate.value = "";
        emailAddress.value = "";
        userType.value = 0; // Assicurati che questo campo sia azzerato correttamente
        password.value = "";
      } else {
        console.error("Si è verificato un errore durante la richiesta POST");
      }
    }
  };

  xhttp.open("POST", "http://localhost:8080/api/auth/register", true);
  xhttp.setRequestHeader("Content-Type", "application/json");

  let jsonData = JSON.stringify(userData);
  xhttp.send(jsonData);
}

document.addEventListener("DOMContentLoaded", function () {
  let registrationForm = document.getElementById("registrationForm");
  registrationForm.addEventListener("submit", handleFormSubmit);
});
