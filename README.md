# EcoPuglia

Benvenuto nel progetto **EcoPuglia**! Questa applicazione Java Spring Boot è progettata per scaricare periodicamente dati da archivi publici della regione Puglia e salvarli localmente. Nel contesto di questo esempio, vengono scaricati file JSON e CSV relativi all'aria ed all'energia.

## Prerequisiti

- Java JDK 21
- Apache Maven
- Un ambiente di sviluppo integrato (IDE) come IntelliJ IDEA o Eclipse
- Git (opzionale)

## Configurazione

1. Clona il repository:

   ```bash
   git clone https://github.com/IlCotugamer/EcoPuglia-BE.git
   ```

2. Apri il progetto nel tuo IDE preferito.

3. Modifica la configurazione del database, se necessario, nel file `application.yml`.

4. Esegui l'applicazione.

## Struttura del Progetto

- `src/main/java`: Contiene il codice sorgente Java dell'applicazione.
- `src/main/resources`: Contiene le risorse, inclusi i file di configurazione.

## Utilizzo

L'applicazione scaricherà automaticamente i file ogni due ore. I dettagli dell'attività possono essere monitorati attraverso i log generati dall'applicazione.

## Licenza

Questo progetto è concesso in licenza con la Licenza MIT - consulta il file [LICENSE.md](LICENSE.md) per ulteriori dettagli.
