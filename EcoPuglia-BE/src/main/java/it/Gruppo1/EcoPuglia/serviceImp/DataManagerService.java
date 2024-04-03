package it.Gruppo1.EcoPuglia.serviceImp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.Gruppo1.EcoPuglia.model.CittaModel;
import it.Gruppo1.EcoPuglia.model.EnergiaModel;
import it.Gruppo1.EcoPuglia.model.SensoriModel;
import it.Gruppo1.EcoPuglia.model.ValoriInquinantiModel;
import it.Gruppo1.EcoPuglia.repository.ICittaRepository;
import it.Gruppo1.EcoPuglia.repository.IEnergiaRepository;
import it.Gruppo1.EcoPuglia.repository.ISensoriRepository;
import it.Gruppo1.EcoPuglia.repository.IValoriInquinantiRepository;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataManagerService implements IDataManagerService {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(DataManagerService.class);
    private final List<EnergiaModel> energiaModelList = new ArrayList<>();
    private final IEnergiaRepository iEnergiaRepository;
    private final ICittaRepository iCittaRepository;
    private final IValoriInquinantiRepository iValoriInquinantiRepository;
    private final ISensoriRepository iSensoriRepository;
    private int limit;
    private JsonNode ariaData;
    private byte[] energiaData;

    @Autowired
    public DataManagerService(WebClient.Builder webClientBuilder, IEnergiaRepository iEnergiaRepository, ICittaRepository iCittaRepository, IValoriInquinantiRepository iValoriInquinantiRepository, ISensoriRepository iSensoriRepository){
        this.webClient = webClientBuilder.build();
        this.iEnergiaRepository = iEnergiaRepository;
        this.iCittaRepository = iCittaRepository;
        this.iValoriInquinantiRepository = iValoriInquinantiRepository;
        this.iSensoriRepository = iSensoriRepository;
    }

    @Override
    public void runDataManager() {
        downloadAria();
        downloadEnergia();
        logger.info("Dati ricevuti correttamente | Time: " + LocalTime.now());

        processEnergiaData();
        processAriaData();
        logger.info("Dati elaborati correttamente | Time: " + LocalTime.now());
        logger.info("Dati salvati con successo nel database | Time: " + LocalTime.now());

        System.out.println();
    }

    private void downloadAria() {
        String url = "https://dati.puglia.it/ckan/api/3/action/datastore_search?resource_id=8c96ee29-f19f-4d2f-9bb7-27d057589050";
        webClient.get().uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(data -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        JsonNode jsonObject = objectMapper.readTree(data);
                        limit = jsonObject.get("result").get("total").asInt();
                        String newUrl = url + "&limit=" + limit;
                        fetchAriaData(newUrl);
                    } catch (JsonProcessingException e) {
                        logger.error("Errore nella lettura del primo json | URL: https://dati.puglia.it/ckan/api/3/action/datastore_search?resource_id=8c96ee29-f19f-4d2f-9bb7-27d057589050");
                    }
                });
    }

    private void downloadEnergia() {
        String url = "http://www.opendataipres.it/dataset/85045ff8-5e20-4f26-8585-2f2ba0a5c3d5/resource/e5e12518-69d8-46b8-8068-9dc89d356cad/download/richieste-connessione-puglia.csv&type=resource";
        fetchEnergiaData(url);
    }

    private void fetchAriaData(String url) {
        webClient.get().uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    logger.error("Errore durante la richiesta HTTP", throwable);
                    return Mono.empty();
                })
                .subscribe(data -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        JsonNode jsonObject = objectMapper.readTree(data);
                        ariaData = jsonObject.get("result").get("records");
                    } catch (JsonProcessingException e) {
                        logger.error("Errore nella lettura del secondo json con nuovo limite");
                    }
                });
    }

    private void fetchEnergiaData(String url) {
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

    private void processEnergiaData() {
        boolean isFirst = true;
        CittaModel cittaModel;
        EnergiaModel energiaModel;

        String csvData = new String(energiaData);
        try (CSVReader csvReader = new CSVReader(new StringReader(csvData))) {
            List<String[]> allRecords = csvReader.readAll();
            for (String[] lineRecords : allRecords) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }

                if (!iCittaRepository.existsByNomeCitta(lineRecords[1])) {
                    cittaModel = new CittaModel(lineRecords[1]);
                    iCittaRepository.save(cittaModel);
                } else {
                    cittaModel = iCittaRepository.findByNomeCitta(lineRecords[1]);
                }

                energiaModel = new EnergiaModel(
                        ("Solare".equals(lineRecords[2])) ? 0 : ("Eolico on-shore".equals(lineRecords[2])) ? 1 : 2,
                        lineRecords[3],
                        cittaModel
                );

                if (!iEnergiaRepository.existsByFonteAndPotenzaAndCittaInfo(energiaModel.getFonte(), energiaModel.getPotenza(), cittaModel)) {
                    energiaModelList.add(energiaModel);
                }
            }
        } catch (IOException | CsvException e) {
            logger.error("Errore nella lettura dei dati .csv | Error: ", e);
        }
    }

    private void processAriaData() {
        int flag = 0;
        Exception controllo = null;
        do {
            try {
                SensoriModel sensoriModel;
                CittaModel cittaModel;

                int dataSize = ariaData.size();
                for (int i = 0; i < dataSize; i++) {
                    JsonNode record = ariaData.get(i);
                    if ("null".equals(record.get("valore_inquinante_misurato").asText())) {
                        flag++;
                        continue;
                    }
                    String comune = record.get("comune").asText();
                    if (!iCittaRepository.existsByNomeCitta(comune)) {
                        iCittaRepository.save(new CittaModel(comune));
                    }
                    cittaModel = iCittaRepository.findByNomeCitta(comune);

                    String longitude = record.get("Longitude").asText();
                    String latitude = record.get("Latitude").asText();
                    if (!iSensoriRepository.existsByLongitudeAndLatitudeAndCittaInfo(longitude, latitude, cittaModel)) {
                        iSensoriRepository.save(new SensoriModel(longitude, latitude, cittaModel));
                    }
                    sensoriModel = iSensoriRepository.findByLongitudeAndLatitudeAndCittaInfo(longitude, latitude, cittaModel);

                    String inquinanteMisurato = record.get("inquinante_misurato").asText();
                    String valoreInquinanteMisurato = record.get("valore_inquinante_misurato").asText();
                    if (!iValoriInquinantiRepository.existsByTipoValoreAndValoreAndSensoreInfo(inquinanteMisurato, valoreInquinanteMisurato, sensoriModel)) {
                        iValoriInquinantiRepository.save(new ValoriInquinantiModel(inquinanteMisurato, valoreInquinanteMisurato, sensoriModel));
                    }
                }
            } catch (Exception e) {
                controllo = e;
                if (e.getClass().getName().equals("java.lang.NullPointerException")) {
                    logger.error("Il server non ha risposto come speravamo, riprovo...");
                } else {
                    logger.error("Errore nella struttura del Json | Errore: " + e.getClass().getName());
                }
            }
        } while (controllo != null && controllo.getClass().getName().equals("NullPointerException"));

        if (flag == limit) {
            logger.error("OGGI NON CI SONO MISURAZIONI PER L'ARIA");
        } else {
            logger.warn("Sono state saltate " + flag + " misurazioni nulle per gli inquinanti dell'aria");
        }
    }
}