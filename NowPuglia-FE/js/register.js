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
    password: userType,
    abbonamentoInfo: password
  };

  // Crea una nuova richiesta XMLHttpRequest
  let xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4) {
      if (this.status == 200) {
        // Gestisci la risposta qui se necessario
        console.log("Richiesta POST completata con successo");
      } else {
        console.error("Si è verificato un errore durante la richiesta POST");
      }
    }
  };

  // Apre una richiesta POST verso l'URL desiderato
  xhttp.open("POST", "url_della_tua_risorsa_di_destinazione", true);
  xhttp.setRequestHeader("Content-Type", "application/json");

  // Invia i dati del form come JSON
  xhttp.send(JSON.stringify(userData));
}

// Aggiungi l'event listener al form quando il documento è pronto
document.addEventListener("DOMContentLoaded", function() {
  let registrationForm = document.getElementById("registrationForm");
  registrationForm.addEventListener("submit", handleFormSubmit);
});
