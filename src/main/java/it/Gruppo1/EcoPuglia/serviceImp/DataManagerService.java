package it.Gruppo1.EcoPuglia.serviceImp;

import com.google.gson.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.Gruppo1.EcoPuglia.model.AriaModel;
import it.Gruppo1.EcoPuglia.model.CittaModel;
import it.Gruppo1.EcoPuglia.model.EnergiaModel;
import it.Gruppo1.EcoPuglia.model.ValoriInquinantiModel;
import it.Gruppo1.EcoPuglia.repository.*;
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
import java.util.Objects;

import static java.lang.String.*;

@Service
public class DataManagerService implements IDataManagerService {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(DataManagerService.class);
    private static final Gson gson = new Gson();
    private final List<EnergiaModel> energiaModelList = new ArrayList<>();
    private final List<AriaModel> ariaModelList = new ArrayList<>();
    private final IEnergiaRepository iEnergiaRepository;
    private final ICittaRepository iCittaRepository;
    private final IValoriInquinantiRepository iValoriInquinantiRepository;
    private final IUtentiRepository iUtentiRepository;
    private final IAriaRepository iAriaRepository;
    private JsonArray ariaData;
    private byte[] energiaData;

    @Autowired
    public DataManagerService(WebClient.Builder webClientBuilder, IEnergiaRepository iEnergiaRepository, ICittaRepository iCittaRepository, IValoriInquinantiRepository iValoriInquinantiRepository, IUtentiRepository iUtentiRepository, IAriaRepository iAriaRepository){
        this.webClient = webClientBuilder.build();
        this.iEnergiaRepository = iEnergiaRepository;
        this.iCittaRepository = iCittaRepository;
        this.iValoriInquinantiRepository = iValoriInquinantiRepository;
        this.iUtentiRepository = iUtentiRepository;
        this.iAriaRepository = iAriaRepository;
    }

    @Override
    public void runDataManager() {
        // Fase 1: ricevere i dati;
        downloadAria();
        downloadEnergia();
        logger.info("File ricevuti correttamente | Time: " + LocalDateTime.now()); // TEMP DEBUG

        // Fase 2: leggere i dati e crazione delle appModel apposite;
        letturaEnergia();
        letturaAria();

        // Fase 3: caricare i dati nel db
        iEnergiaRepository.saveAll(energiaModelList);
//        iAriaRepository.saveAll(ariaModelList);
//        iValoriInquinantiRepository.saveAll();
//        logger.info("Salvato con successo nel database");
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
                    getDataAria(newUrl);

                });
    }

    private void downloadEnergia() {
        String url = "http://www.opendataipres.it/dataset/85045ff8-5e20-4f26-8585-2f2ba0a5c3d5/resource/e5e12518-69d8-46b8-8068-9dc89d356cad/download/richieste-connessione-puglia.csv&type=resource";
        getDataEnergia(url);
    }

    private void getDataAria(String url) {
        webClient.get().uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    logger.error("Errore durante la richiesta HTTP", throwable);
                    return Mono.empty();
                })
                .subscribe(data -> {
                    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
                    JsonObject result = jsonObject.getAsJsonObject("result");
                    ariaData = result.getAsJsonArray("records");
                });

    }

    private void getDataEnergia(String url) {
        energiaData = webClient.get().uri(url)
                .accept(MediaType.ALL)
                .retrieve()
                .bodyToMono(byte[].class)
                .onErrorResume(throwable -> {
                    logger.error("Errore durante la richiesta HTTP", throwable);
                    return Mono.empty();
                })
                .block();
    }

    // FASE 2 ----------------------------------------------------------------------------------------------------------

    private void letturaEnergia() {
        boolean is_frist = true;
        CittaModel cittaModel;
        EnergiaModel energiaModel;

        String csvData = new String(energiaData);
        CSVReader csvReader = new CSVReader(new StringReader(csvData));
        try {
            List<String[]> allRecords = csvReader.readAll();
            for (String[] lineRecords : allRecords) {
                if (is_frist) {
                    is_frist = false;
                    continue;
                }

                cittaModel = new CittaModel(lineRecords[1], "en0", "en0");

                energiaModel = new EnergiaModel(
                        (Objects.equals(lineRecords[2], "Solare")) ? 0 : (Objects.equals(lineRecords[2], "Eolico on-shore")) ? 1 : 2,
                        lineRecords[3],
                        cittaModel
                );

                if (iCittaRepository.existsByNomeCittaAndLongitudeAndLatitude(cittaModel.getNomeCitta(), cittaModel.getLongitude(), cittaModel.getLatitude())) {
                    iCittaRepository.save(cittaModel);
                } else continue;

                if (!iEnergiaRepository.existsByFonteAndPotenzaAndCittaInfo(energiaModel.getFonte(), energiaModel.getPotenza(), cittaModel)) {
                    energiaModelList.add(energiaModel);
                }

            }
        } catch (IOException | CsvException e) {
            logger.error("Errore nella lettura dei dati .csv | Error: " + e);
        }

    }

    private void letturaAria() {
        Exception controllo = null;
        do {
            try {
                AriaModel ariaModel;
                ValoriInquinantiModel valoriInquinantiModel;
                CittaModel cittaModel;
                JsonObject record;
                int dataSize = ariaData.size();
                for (int i = 0; i < dataSize; i++) {
                    JsonElement jsonElement = ariaData.get(i);
                    record = jsonElement.getAsJsonObject();
                    System.out.println(valueOf(record.get("valore_inquinante_misurato")).getClass().getName() + " VALORE " + record.get("valore_inquinante_misurato"));
                    String valoreInquinante = valueOf(record.get("valore_inquinante_misurato"));
                    if (valoreInquinante.equals("null")) {
                        System.out.println("EVVIVA");
                        continue;
                    }

                    if (iCittaRepository.existsByNomeCittaAndLongitudeAndLatitude(valueOf(record.get("comune")), valueOf(record.get("Longitude")), valueOf(record.get("Latitude")))) {
                        cittaModel = new CittaModel(
                                valueOf(record.get("comune")),
                                valueOf(record.get("Longitude")),
                                valueOf(record.get("Latitude"))
                        );

                        iCittaRepository.save(cittaModel);
                    } else
                        cittaModel = iCittaRepository.findByLongitudeAndLatitude(valueOf(record.get("Longitude")), valueOf(record.get("Latitude")));

                    ariaModel = new AriaModel(
                            (Objects.equals(valueOf(record.get("tipologia_di_area")), "Suburbana")) ? 1 : 2,
                            LocalDateTime.parse(record.get("data_di_misurazione").getAsString()),
                            cittaModel
                    );

                    iAriaRepository.save(ariaModel);

                    System.out.println(record.get("comune") + " " + i);
                    //                valoriInquinantiModel = new ValoriInquinantiModel(
                    //                        valueOf(record.get("inquinante_misurato")),
                    //                        valueOf(record.get("valore_inquinante_misurato")),
                    //                        ariaModel
                    //                );

                    //                iValoriInquinantiRepository.save(valoriInquinantiModel);
                    //                ariaModelList.add(ariaModel);
                }
                System.out.println("Finito il for di " + dataSize);
            } catch (JsonSyntaxException | NullPointerException e) {
                controllo = e;
                if (e.getClass().getName().equals("NullPointerException"))
                    logger.info("Il server non ha risposto come speravamo, riprovo...");
                else
                    logger.error("Errore nella struttura del Json | Errore: " + e);
            }
        } while (controllo != null && controllo.getClass().getName().equals("NullPointerException"));
    }
}
