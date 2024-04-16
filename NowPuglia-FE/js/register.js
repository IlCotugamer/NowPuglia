function handleFormSubmit(event) {
  event.preventDefault();

  let firstName = document.querySelector("#firstName").value;
  let lastName = document.querySelector("#lastName").value;
  let birthdayDate = document.querySelector("#birthdayDate").value;
  let emailAddress = document.querySelector("#emailAddress").value;
  let userType = document.querySelector(".select").value;
  let password = document.querySelector("#password").value;

  let userData = {
    nome: firstName,
    cognome: lastName,
    dataNascita: birthdayDate,
    email: emailAddress,
    password: password,
    abbonamentoInfo: parseInt(userType)
  };


  let xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4) {
      if (this.status == 200) {
        console.log("Richiesta POST completata con successo");
      } else {
        console.error("Si è verificato un errore durante la richiesta POST");
      }
    }
  };

  // Apre una richiesta POST verso l'URL desiderato
  xhttp.open("POST", "http://localhost:8080/api/auth/register", true);
  xhttp.setRequestHeader("Content-Type", "application/json");

  // Invia i dati del form come JSON
  xhttp.send(userData);
}

// Aggiungi l'event listener al form quando il documento è pronto
document.addEventListener("DOMContentLoaded", function() {
  let registrationForm = document.getElementById("registrationForm");
  registrationForm.addEventListener("submit", handleFormSubmit);
});
