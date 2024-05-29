var map = L.map("map").setView([41.117143, 16.871871], 8);

L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
  maxZoom: 19,
  attribution:
    '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
}).addTo(map);

var marker = L.marker([40.828848, 16.561015], { alt: "Altamura" })
  .addTo(map)
  .bindPopup(
    "Altamura (BA)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> Via Santeramo:<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([41.113542, 16.888064], { alt: "Bari - Caldarola" })
  .addTo(map)
  .bindPopup(
    "Bari - Caldarola (BA)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([41.076666, 16.866944], { alt: "Bari - Carbonara" })
  .addTo(map)
  .bindPopup(
    "Bari - Carbonara (BA)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([41.122269, 16.872552], { alt: "Bari - Cavour" })
  .addTo(map)
  .bindPopup(
    "Bari - Cavour (BA)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([40.953154, 16.920731], { alt: "Casamassima" })
  .addTo(map)
  .bindPopup(
    "Casamassima (BA)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([41.107777, 16.766111], { alt: "Modugno" })
  .addTo(map)
  .bindPopup(
    "Modugno (BA)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([41.201101, 16.605253], { alt: "Molfetta" })
  .addTo(map)
  .bindPopup(
    "Molfetta (BA)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([41.318333, 16.293055], { alt: "Barletta" })
  .addTo(map)
  .bindPopup(
    "Barletta (BAT)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([40.630917, 17.921778], { alt: "Brindisi" })
  .addTo(map)
  .bindPopup(
    "Brindisi (BA)<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([40.459696, 18.116386], { alt: "Lecce" })
  .addTo(map)
  .bindPopup(
    "Lecce<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([40.460553, 17.263602], { alt: "Taranto" })
  .addTo(map)
  .bindPopup(
    "Taranto<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker = L.marker([41.455555, 15.548611], { alt: "Foggia" })
  .addTo(map)
  .bindPopup(
    "Foggia<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );

var marker1 = L.marker([41.117143, 16.871871], { alt: "Bari" })
  .addTo(map)
  .bindPopup(
    "Bari<br><br> Questo comune ha installato pannelli solari sui principali edifici pubblici.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 150 µg/m³<br> O3: 170 µg/m³<br> PM10: 40 µg/m³<br> PM2.5: 20 µg/m³<br>"
  );

var marker2 = L.marker([40.352208, 18.169567], { alt: "Lecce" })
  .addTo(map)
  .bindPopup(
    "Lecce<br><br> Questo comune sta sviluppando un progetto di energia solare a larga scala.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 130 µg/m³<br> O3: 160 µg/m³<br> PM10: 35 µg/m³<br> PM2.5: 18 µg/m³<br>"
  );

var marker3 = L.marker([40.632728, 17.936871], { alt: "Brindisi" })
  .addTo(map)
  .bindPopup(
    "Brindisi<br><br> Questo comune ha recentemente inaugurato una centrale a biomasse.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 140 µg/m³<br> O3: 175 µg/m³<br> PM10: 45 µg/m³<br> PM2.5: 22 µg/m³<br>"
  );

var marker4 = L.marker([40.464360, 17.247030], { alt: "Taranto" })
  .addTo(map)
  .bindPopup(
    "Taranto<br><br> Questo comune sta pianificando la costruzione di un parco eolico off-shore.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 160 µg/m³<br> O3: 190 µg/m³<br> PM10: 55 µg/m³<br> PM2.5: 30 µg/m³<br>"
  );

var marker5 = L.marker([41.275680, 16.416167], { alt: "Trani" })
  .addTo(map)
  .bindPopup(
    "Trani<br><br> Questo comune ha avviato un progetto di riduzione delle emissioni tramite il trasporto pubblico elettrico.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 120 µg/m³<br> O3: 150 µg/m³<br> PM10: 30 µg/m³<br> PM2.5: 15 µg/m³<br>"
  );

// Marker per Alberobello
var marker6 = L.marker([40.784389, 17.240078], { alt: "Alberobello" })
  .addTo(map)
  .bindPopup(
    "Alberobello<br><br> Questo comune sta sperimentando l'uso di turbine eoliche di piccola scala.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 100 µg/m³<br> O3: 140 µg/m³<br> PM10: 20 µg/m³<br> PM2.5: 10 µg/m³<br>"
  );

// Marker per Altamura
var marker7 = L.marker([40.827988, 16.550404], { alt: "Altamura" })
  .addTo(map)
  .bindPopup(
    "Altamura<br><br> Questo comune ha incrementato l'uso di veicoli elettrici per la raccolta dei rifiuti.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 110 µg/m³<br> O3: 155 µg/m³<br> PM10: 25 µg/m³<br> PM2.5: 12 µg/m³<br>"
  );

// Marker per Cisternino
var marker8 = L.marker([40.743547, 17.424745], { alt: "Cisternino" })
  .addTo(map)
  .bindPopup(
    "Cisternino<br><br> Questo comune ha implementato un sistema di illuminazione pubblica a LED.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 90 µg/m³<br> O3: 130 µg/m³<br> PM10: 15 µg/m³<br> PM2.5: 8 µg/m³<br>"
  );

// Marker per Fasano
var marker9 = L.marker([40.836715, 17.363013], { alt: "Fasano" })
  .addTo(map)
  .bindPopup(
    "Fasano<br><br> Questo comune sta incentivando la costruzione di case a basso consumo energetico.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 115 µg/m³<br> O3: 160 µg/m³<br> PM10: 28 µg/m³<br> PM2.5: 14 µg/m³<br>"
  );

// Marker per Ostuni
var marker10 = L.marker([40.729395, 17.577532], { alt: "Ostuni" })
  .addTo(map)
  .bindPopup(
    "Ostuni<br><br> Questo comune ha implementato un sistema di raccolta differenziata porta a porta.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 95 µg/m³<br> O3: 145 µg/m³<br> PM10: 18 µg/m³<br> PM2.5: 9 µg/m³<br>"
  );

// Marker per Martina Franca
var marker11 = L.marker([40.705374, 17.338591], { alt: "Martina Franca" })
  .addTo(map)
  .bindPopup(
    "Martina Franca<br><br> Questo comune ha avviato un progetto per la creazione di piste ciclabili.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 105 µg/m³<br> O3: 150 µg/m³<br> PM10: 22 µg/m³<br> PM2.5: 11 µg/m³<br>"
  );

// Marker per Grottaglie
var marker12 = L.marker([40.539852, 17.437422], { alt: "Grottaglie" })
  .addTo(map)
  .bindPopup(
    "Grottaglie<br><br> Questo comune ha promosso l'installazione di sistemi di riscaldamento geotermico.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 125 µg/m³<br> O3: 170 µg/m³<br> PM10: 30 µg/m³<br> PM2.5: 16 µg/m³<br>"
  );

// Marker per Ginosa
var marker13 = L.marker([40.578663, 16.757803], { alt: "Ginosa" })
  .addTo(map)
  .bindPopup(
    "Ginosa<br><br> Questo comune ha implementato un programma di riforestazione urbana.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 100 µg/m³<br> O3: 135 µg/m³<br> PM10: 20 µg/m³<br> PM2.5: 10 µg/m³<br>"
  );

// Marker per Monopoli
var marker14 = L.marker([40.951266, 17.300547], { alt: "Monopoli" })
  .addTo(map)
  .bindPopup(
    "Monopoli<br><br> Questo comune ha avviato un progetto di desalinizzazione per l'uso agricolo.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 110 µg/m³<br> O3: 150 µg/m³<br> PM10: 25 µg/m³<br> PM2.5: 12 µg/m³<br>"
  );

// Marker per Polignano a Mare
var marker15 = L.marker([40.995848, 17.216187], { alt: "Polignano a Mare" })
  .addTo(map)
  .bindPopup(
    "Polignano a Mare<br><br> Questo comune ha incrementato l'uso di energie rinnovabili nei settori turistici.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 95 µg/m³<br> O3: 140 µg/m³<br> PM10: 18 µg/m³<br> PM2.5: 9 µg/m³<br>"
  );

// Marker per Noci
var marker16 = L.marker([40.790594, 17.129486], { alt: "Noci" })
  .addTo(map)
  .bindPopup(
    "Noci<br><br> Questo comune ha adottato un programma di compostaggio domestico.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 100 µg/m³<br> O3: 145 µg/m³<br> PM10: 20 µg/m³<br> PM2.5: 10 µg/m³<br>"
  );

// Marker per Mottola
var marker17 = L.marker([40.636434, 17.035846], { alt: "Mottola" })
  .addTo(map)
  .bindPopup(
    "Mottola<br><br> Questo comune ha lanciato un'iniziativa di mobilità sostenibile con auto elettriche condivise.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 115 µg/m³<br> O3: 160 µg/m³<br> PM10: 28 µg/m³<br> PM2.5: 14 µg/m³<br>"
  );

// Marker per San Giovanni Rotondo
var marker18 = L.marker([41.707695, 15.706160], { alt: "San Giovanni Rotondo" })
  .addTo(map)
  .bindPopup(
    "San Giovanni Rotondo<br><br> Questo comune ha investito in infrastrutture per la ricarica di veicoli elettrici.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 120 µg/m³<br> O3: 150 µg/m³<br> PM10: 25 µg/m³<br> PM2.5: 13 µg/m³<br>"
  );

// Marker per Troia
var marker19 = L.marker([41.374192, 15.317402], { alt: "Troia" })
  .addTo(map)
  .bindPopup(
    "Troia<br><br> Questo comune sta sviluppando un progetto di microgrid per l'autosufficienza energetica.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 105 µg/m³<br> O3: 140 µg/m³<br> PM10: 22 µg/m³<br> PM2.5: 11 µg/m³<br>"
  );

// Marker per Bovino
var marker20 = L.marker([41.325764, 15.335219], { alt: "Bovino" })
  .addTo(map)
  .bindPopup(
    "Bovino<br><br> Questo comune ha implementato sistemi di irrigazione a basso consumo per l'agricoltura.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 90 µg/m³<br> O3: 130 µg/m³<br> PM10: 18 µg/m³<br> PM2.5: 9 µg/m³<br>"
  );

// Marker per San Nicandro Garganico
var marker21 = L.marker([41.836584, 15.562468], { alt: "San Nicandro Garganico" })
  .addTo(map)
  .bindPopup(
    "San Nicandro Garganico<br><br> Questo comune ha promosso l'uso di energie rinnovabili nelle abitazioni private.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 110 µg/m³<br> O3: 150 µg/m³<br> PM10: 25 µg/m³<br> PM2.5: 12 µg/m³<br>"
  );

// Marker per Mattinata
var marker22 = L.marker([41.713097, 16.060628], { alt: "Mattinata" })
  .addTo(map)
  .bindPopup(
    "Mattinata<br><br> Questo comune sta incentivando la creazione di orti urbani.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 95 µg/m³<br> O3: 140 µg/m³<br> PM10: 20 µg/m³<br> PM2.5: 10 µg/m³<br>"
  );

// Marker per Vieste
var marker23 = L.marker([41.882339, 16.175918], { alt: "Vieste" })
  .addTo(map)
  .bindPopup(
    "Vieste<br><br> Questo comune ha avviato un programma per la riduzione delle plastiche monouso.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 100 µg/m³<br> O3: 145 µg/m³<br> PM10: 22 µg/m³<br> PM2.5: 11 µg/m³<br>"
  );

// Marker per Manfredonia
var marker24 = L.marker([41.631972, 15.918821], { alt: "Manfredonia" })
  .addTo(map)
  .bindPopup(
    "Manfredonia<br><br> Questo comune ha investito in un parco solare.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 105 µg/m³<br> O3: 150 µg/m³<br> PM10: 24 µg/m³<br> PM2.5: 12 µg/m³<br>"
  );

// Marker per Rodi Garganico
var marker25 = L.marker([41.928357, 15.888520], { alt: "Rodi Garganico" })
  .addTo(map)
  .bindPopup(
    "Rodi Garganico<br><br> Questo comune ha installato stazioni di ricarica per biciclette elettriche.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 90 µg/m³<br> O3: 135 µg/m³<br> PM10: 18 µg/m³<br> PM2.5: 9 µg/m³<br>"
  );

// Marker per Peschici
var marker26 = L.marker([41.948236, 16.012321], { alt: "Peschici" })
  .addTo(map)
  .bindPopup(
    "Peschici<br><br> Questo comune ha avviato un progetto di riqualificazione delle aree costiere.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 95 µg/m³<br> O3: 140 µg/m³<br> PM10: 20 µg/m³<br> PM2.5: 10 µg/m³<br>"
  );

// Marker per Zapponeta
var marker27 = L.marker([41.453136, 15.916960], { alt: "Zapponeta" })
  .addTo(map)
  .bindPopup(
    "Zapponeta<br><br> Questo comune ha implementato una raccolta dei rifiuti basata su un sistema a premi.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 85 µg/m³<br> O3: 130 µg/m³<br> PM10: 17 µg/m³<br> PM2.5: 8 µg/m³<br>"
  );

// Marker per Torremaggiore
var marker28 = L.marker([41.691192, 15.288233], { alt: "Torremaggiore" })
  .addTo(map)
  .bindPopup(
    "Torremaggiore<br><br> Questo comune ha incentivato l'uso di sistemi di riscaldamento a pellet.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 105 µg/m³<br> O3: 145 µg/m³<br> PM10: 22 µg/m³<br> PM2.5: 11 µg/m³<br>"
  );

// Marker per Deliceto
var marker29 = L.marker([41.243721, 15.385109], { alt: "Deliceto" })
  .addTo(map)
  .bindPopup(
    "Deliceto<br><br> Questo comune sta sviluppando un progetto di bioedilizia per nuove abitazioni.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 95 µg/m³<br> O3: 135 µg/m³<br> PM10: 20 µg/m³<br> PM2.5: 10 µg/m³<br>"
  );

// Marker per Candela
var marker30 = L.marker([41.145349, 15.462145], { alt: "Candela" })
  .addTo(map)
  .bindPopup(
    "Candela<br><br> Questo comune ha adottato un sistema di monitoraggio ambientale avanzato.<br><br> Misurazioni inquinanti (dati di ieri):<br> NO2: 90 µg/m³<br> O3: 130 µg/m³<br> PM10: 18 µg/m³<br> PM2.5: 9 µg/m³<br>"
  );


var circle = L.circleMarker([41.455555, 15.548611], { radius: 20 }).addTo(map);
circle.setStyle({ color: 'green' });

var circle = L.circleMarker([40.828848, 16.561015], { radius: 20 }).addTo(map);
circle.setStyle({ color: 'green' });

var circle = L.circleMarker([41.122269, 16.872552], { radius: 20 }).addTo(map);
circle.setStyle({ color: 'green' });

var circle = L.circleMarker([40.630917, 17.921778], { radius: 20 }).addTo(map);
circle.setStyle({ color: 'green' });

var circle = L.circleMarker([41.318333, 16.293055], { radius: 20 }).addTo(map);
circle.setStyle({ color: 'green' });