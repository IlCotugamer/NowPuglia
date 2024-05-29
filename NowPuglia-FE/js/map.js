var map = L.map("map").setView([41.117143, 16.871871], 8);

L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
  maxZoom: 19,
  attribution:
    '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
}).addTo(map);

var marker = L.marker([40.828848, 16.561015], { alt: "Altamura" })
  .addTo(map)
  .bindPopup(
    "Altamura<br><br> Questo comune HA richiesto la connessione alle fonti di energia rinnovabile (eolico on-shore)<br><br> Misurazioni inquinanti:<br> Via Santeramo:<br> NO2: 200 µg/m³<br> O3: 180 µg/m³<br> PM10: 50 µg/m³<br> PM2.5: 25 µg/m³<br>"
  );
