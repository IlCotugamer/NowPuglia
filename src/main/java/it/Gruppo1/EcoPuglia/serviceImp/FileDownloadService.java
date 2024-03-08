package it.Gruppo1.EcoPuglia.serviceImp;

import it.Gruppo1.EcoPuglia.service.IDatiSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

@Service
public class FileDownloadService implements IDatiSerivce {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(FileDownloadService.class);
    @Autowired
    public FileDownloadService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
    public void downloadAllData() {
        downloadAriaData();
        downloadEnergiaData();
    }

    private void downloadAriaData() {
        String url = "https://dati.puglia.it/ckan/api/3/action/datastore_search?resource_id=8c96ee29-f19f-4d2f-9bb7-27d057589050";
        String file = "src/main/resources/Aria/inq.json";
        downloadAndSaveFile(url, file);
    }

    private void downloadEnergiaData() {
        String url = "http://www.opendataipres.it/dataset/85045ff8-5e20-4f26-8585-2f2ba0a5c3d5/resource/e5e12518-69d8-46b8-8068-9dc89d356cad/download/richieste-connessione-puglia.csv&type=resource";
        String file = "src/main/resources/Energia/energia.csv";
        downloadAndSaveFile(url, file);
    }

    private void downloadAndSaveFile(String url, String filePath) {
        String response = restTemplate.getForObject(url, String.class);
        if (response != null) {
            try (Writer writer = new FileWriter(filePath)) {
                writer.write(response);
                logger.info("File salvato correttamente: {} - {}", filePath, LocalDateTime.now());
            } catch (IOException e) {
                logger.error("Errore durante il salvataggio del file: {}", filePath, e);
            }
        } else {
            logger.error("Errore nella richiesta ({})", url);
        }
    }
}
