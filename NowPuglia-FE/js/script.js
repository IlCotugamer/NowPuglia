// Aggiungi la classe "active" al link corrente nella navbar
document.querySelectorAll('.nav-link').forEach(item => {
    item.addEventListener('click', event => {
      document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
      });
      event.target.classList.add('active');
    });
  });
  
  // Funzione per aggiungere la classe "active" al link della navbar quando la sezione è attiva
function setActiveLink() {
  const sections = document.querySelectorAll('section');
  const navLinks = document.querySelectorAll('.nav-link');

  sections.forEach((section, index) => {
      const top = section.offsetTop - 50;
      const bottom = top + section.clientHeight;

      if (window.scrollY >= top && window.scrollY < bottom) {
          navLinks.forEach(link => {
              link.classList.remove('active');
          });
          navLinks[index].classList.add('active');
      }
  });
}

// Funzione per gestire il click sul link "Servizi" nella navbar
document.getElementById('services-link').addEventListener('click', function(event) {
  event.preventDefault(); // Evita il comportamento predefinito del link
  
  // Recupera l'id della sezione "Servizi"
  const targetId = this.getAttribute('href');

  // Scrolla fino alla sezione "Servizi" con una transizione fluida
  document.querySelector(targetId).scrollIntoView({
      behavior: 'smooth'
  });

  // Aggiorna la classe "active" del link nella navbar
  document.querySelectorAll('.nav-link').forEach(link => {
      link.classList.remove('active');
  });
  this.classList.add('active');
});

// Event listener per aggiornare la classe "active" quando si scorre la pagina
window.addEventListener('scroll', setActiveLink);