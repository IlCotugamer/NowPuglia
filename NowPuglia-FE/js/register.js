const form = document.getElementById('registrationForm');

form.addEventListener('submit', (event) => {
  event.preventDefault();

  let formData = new FormData(form);

  let data = {
    nome: formData.get('firstName'),
    cognome: formData.get('cognome'),
    email: formData.get('email'),
    password: formData.get('password'),
  };

  console.table(data)
});
