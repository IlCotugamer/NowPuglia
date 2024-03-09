package it.Gruppo1.EcoPuglia.serviceImp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.Gruppo1.EcoPuglia.service.IDatiSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileDownloadService implements IDatiSerivce {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(FileDownloadService.class);
    private static final Gson gson = new Gson();

    public FileDownloadService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.build();
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void downloadAllData() {
        downloadAria();
        downloadEnergia();
        System.out.println("ESEGUITO CORRETTAMENTE");
    }

    private void downloadAria() {
        String url = "https://dati.puglia.it/ckan/api/3/action/datastore_search?resource_id=8c96ee29-f19f-4d2f-9bb7-27d057589050";
        String file = "src/main/resources/Aria/inq.json";

        webClient.get().uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(data -> {
                    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
                    int total = jsonObject.getAsJsonObject("result").get("total").getAsInt();
                    String newUrl = url + "&limit=" + total;
                    getData(newUrl, file, true);
                });
    }

    private void downloadEnergia() {
        String url = "http://www.opendataipres.it/dataset/85045ff8-5e20-4f26-8585-2f2ba0a5c3d5/resource/e5e12518-69d8-46b8-8068-9dc89d356cad/download/richieste-connessione-puglia.csv&type=resource";
        String file = "src/main/resources/Energia/energia.csv";
        getData(url, file, false);
    }

    private void getData(String url, String file, boolean is_json) {
        webClient.get().uri(url)
                .accept(is_json ? MediaType.APPLICATION_JSON : MediaType.ALL)
                .retrieve()
                .bodyToMono(byte[].class)
                .subscribe(data -> {
                    try {
                        Files.write(Paths.get(file), data);
                    } catch (IOException e) {
                        logger.error("Errore durante la scrittura del file", e);
                    }
                });
    }
}
