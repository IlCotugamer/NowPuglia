package it.Gruppo1.EcoPuglia.serviceImp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.Gruppo1.EcoPuglia.component.AppCostants;
import it.Gruppo1.EcoPuglia.service.IDatiSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FileDownloaderService implements IDatiSerivce {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(FileDownloaderService.class);
    private static final Gson gson = new Gson();
    private final AppCostants appCostants;

    @Autowired
    public FileDownloaderService(WebClient.Builder webClientBuilder, AppCostants appCostants){
        this.webClient = webClientBuilder.build();
        this.appCostants = appCostants;
    }

    @Override
    @Scheduled(fixedRate = 60 * 60 * 1000) // (1s * 60 = 1m * 60 = 1h) * 1000 = 3600000ms == 1h
    public void downloadAllData() {
        int total = downloadAria().intValue();
        downloadEnergia();
        appCostants.setTotalLimit(total);

        System.out.println("Eseguito correttamente | Time: " + LocalDateTime.now()); // TEMP DEBUG
    }

    private AtomicInteger downloadAria() {
        String url = "https://dati.puglia.it/ckan/api/3/action/datastore_search?resource_id=8c96ee29-f19f-4d2f-9bb7-27d057589050";
        AtomicInteger total = new AtomicInteger();
        webClient.get().uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(data -> {
                    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
                    total.set(jsonObject.getAsJsonObject("result").get("total").getAsInt());
                    String newUrl = url + "&limit=" + total;
                    getData(newUrl, appCostants.getPathAria(), true);

                });
        return total;
    }

    private void downloadEnergia() {
        String url = "http://www.opendataipres.it/dataset/85045ff8-5e20-4f26-8585-2f2ba0a5c3d5/resource/e5e12518-69d8-46b8-8068-9dc89d356cad/download/richieste-connessione-puglia.csv&type=resource";
        getData(url, appCostants.getPathEnergia(), false);
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
