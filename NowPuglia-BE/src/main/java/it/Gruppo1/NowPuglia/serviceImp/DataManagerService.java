package it.Gruppo1.NowPuglia.serviceImp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.Gruppo1.NowPuglia.component.AppCostants;
import it.Gruppo1.NowPuglia.model.CittaModel;
import it.Gruppo1.NowPuglia.model.EnergiaModel;
import it.Gruppo1.NowPuglia.model.SensoriModel;
import it.Gruppo1.NowPuglia.model.ValoriInquinantiModel;
import it.Gruppo1.NowPuglia.repository.ICittaRepository;
import it.Gruppo1.NowPuglia.repository.IEnergiaRepository;
import it.Gruppo1.NowPuglia.repository.ISensoriRepository;
import it.Gruppo1.NowPuglia.repository.IValoriInquinantiRepository;
import it.Gruppo1.NowPuglia.service.IDataManagerService;
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
import java.util.List;

import static it.Gruppo1.NowPuglia.component.AppCostants.capitalize;

@Service
public class DataManagerService implements IDataManagerService {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(DataManagerService.class);
    private final IEnergiaRepository iEnergiaRepository;
    private final ICittaRepository iCittaRepository;
    private final IValoriInquinantiRepository iValoriInquinantiRepository;
    private final ISensoriRepository iSensoriRepository;
    private final AppCostants appCostants;
    private JsonNode ariaData;
    private byte[] energiaData;

    @Autowired
    public DataManagerService(WebClient.Builder webClientBuilder, IEnergiaRepository iEnergiaRepository, ICittaRepository iCittaRepository, IValoriInquinantiRepository iValoriInquinantiRepository, ISensoriRepository iSensoriRepository, AppCostants appCostants){
        this.webClient = webClientBuilder.build();
        this.iEnergiaRepository = iEnergiaRepository;
        this.iCittaRepository = iCittaRepository;
        this.iValoriInquinantiRepository = iValoriInquinantiRepository;
        this.iSensoriRepository = iSensoriRepository;
        this.appCostants = appCostants;
    }

    @Override
    public void runDataManager() {
        fetchAriaData(appCostants.getCorrectAriaUrl());
        fetchEnergiaData(appCostants.getEnergiaUrl());
        logger.info("Dati ricevuti correttamente | Time: {}", LocalTime.now());

        processEnergiaData();
        processAriaData();
        logger.info("Dati elaborati e salvati correttamente | Time: {}", LocalTime.now());
    }

    private void fetchAriaData(String url) {
        String data = webClient.get().uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    logger.error("Errore durante la richiesta HTTP", throwable);
                    return Mono.empty();
                }).block();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonObject = objectMapper.readTree(data);
            ariaData = jsonObject.get("result").get("records");
        } catch (JsonProcessingException e) {
            logger.error("Errore nella lettura del secondo json con nuovo limite");
        }
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
        assert energiaData != null;
        String csvData = new String(energiaData);
        try (CSVReader csvReader = new CSVReader(new StringReader(csvData))) {
            List<String[]> allRecords = csvReader.readAll();
            for (String[] lineRecords : allRecords) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                lineRecords[1] = capitalize(lineRecords[1]);
                if (!iCittaRepository.existsByNomeCitta(lineRecords[1])) {
                    iCittaRepository.save(new CittaModel(lineRecords[1]));
                }
                cittaModel = iCittaRepository.findByNomeCitta(lineRecords[1]);
                energiaModel = new EnergiaModel(
                        ("Solare".equals(lineRecords[2])) ? 0 : ("Eolico on-shore".equals(lineRecords[2])) ? 1 : 2,
                        lineRecords[3],
                        cittaModel
                );

                if (!iEnergiaRepository.existsByFonteAndPotenzaAndCittaInfo(energiaModel.getFonte(), energiaModel.getPotenza(), cittaModel)) {
                    iEnergiaRepository.save(energiaModel);
                }
            }
        } catch (IOException | CsvException e) {
            logger.error("Errore nella lettura dei dati .csv | Error: {}", e.getClass().getName());
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
                    comune = capitalize(comune);
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
                    logger.error("Errore nella struttura del Json | Errore: {}", e.getClass().getName());
                }
            }
        } while (controllo != null && controllo.getClass().getName().equals("NullPointerException"));

        if (flag == appCostants.getAriaLimit()) {
            logger.error("OGGI NON CI SONO MISURAZIONI PER L'ARIA");
        } else {
            logger.warn("Sono state saltate {} misurazioni nulle per gli inquinanti dell'aria", flag);
        }
    }
}