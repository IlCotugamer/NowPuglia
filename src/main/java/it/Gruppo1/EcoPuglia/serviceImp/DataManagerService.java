package it.Gruppo1.EcoPuglia.serviceImp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.Gruppo1.EcoPuglia.model.AriaModel;
import it.Gruppo1.EcoPuglia.model.EnergiaModel;
import it.Gruppo1.EcoPuglia.repository.IAriaRepository;
import it.Gruppo1.EcoPuglia.repository.IEnergiaRepository;
import it.Gruppo1.EcoPuglia.service.IDataManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataManagerService implements IDataManagerService {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(DataManagerService.class);
    private static final Gson gson = new Gson();
    private final List<EnergiaModel> energiaModelList = new ArrayList<>();
    private final List<AriaModel> ariaModelList = new ArrayList<>();
    private final IEnergiaRepository iEnergiaRepository;
    private final IAriaRepository iAriaRepository;
    private byte[] ariaData;
    private byte[] energiaData;

    @Autowired
    public DataManagerService(WebClient.Builder webClientBuilder, IEnergiaRepository iEnergiaRepository, IAriaRepository iAriaRepository){
        this.webClient = webClientBuilder.build();
        this.iEnergiaRepository = iEnergiaRepository;
        this.iAriaRepository = iAriaRepository;
    }

    @Override
    public void runDataManager() {
        // Fase 1: ricevere i dati;
        downloadAria();
        downloadEnergia();
        logger.info("File ricevuti correttamente | Time: " + LocalDateTime.now()); // TEMP DEBUG

        // Fase 2: leggere i dati e crazione delle appModel apposite;


        // Fase 3: caricare i dati nel db
        iEnergiaRepository.saveAll(energiaModelList);
        iAriaRepository.saveAll(ariaModelList);
        logger.info("Salvato con successo nel database");
    }

    // FASE 1 ----------------------------------------------------------------------------------------------------------
    private void downloadAria() {
        String url = "https://dati.puglia.it/ckan/api/3/action/datastore_search?resource_id=8c96ee29-f19f-4d2f-9bb7-27d057589050";
        webClient.get().uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(data -> {
                    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
                    String newUrl = url + "&limit=" + jsonObject.getAsJsonObject("result").get("total").getAsInt();
                    getData(newUrl, true);

                });
    }

    private void downloadEnergia() {
        String url = "http://www.opendataipres.it/dataset/85045ff8-5e20-4f26-8585-2f2ba0a5c3d5/resource/e5e12518-69d8-46b8-8068-9dc89d356cad/download/richieste-connessione-puglia.csv&type=resource";
        getData(url, false);
    }

    private void getData(String url, boolean is_json) {
        webClient.get().uri(url)
                .accept(is_json ? MediaType.APPLICATION_JSON : MediaType.ALL)
                .retrieve()
                .bodyToMono(byte[].class)
                .onErrorResume(throwable -> {
                    logger.error("Errore durante la richiesta HTTP", throwable);
                    return Mono.empty();
                })
                .subscribe(data -> {
                    if (is_json) ariaData = data;
                    else energiaData = data;
                });
    }

    // FASE 2 ----------------------------------------------------------------------------------------------------------

    private void letturaEnergia() {
        int i;
        EnergiaModel energiaModel;
        String csvData = new String(energiaData);
        CSVReader csvReader = new CSVReader(new StringReader(csvData));
        try {
            List<String[]> allRecords = csvReader.readAll();
            for (String[] lineRecords : allRecords) {
                for (i = 1; i < lineRecords.length; i++) {
                    energiaModel = new EnergiaModel();
                    energiaModel.setId(i);
                    energiaModel.setProvincia(lineRecords[0]);
                    energiaModel.setComune(lineRecords[1]);
                    energiaModel.setFonte(lineRecords[2]);
                    energiaModel.setPotenza(Float.parseFloat(lineRecords[3]));
                }
            }
        } catch (IOException | CsvException e) {
            logger.error("Errore nella lettura dei dati .csv | Error: " + e);
        }

    }

    private void letturaAria() {

    }
}
