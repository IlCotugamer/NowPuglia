package it.Gruppo1.EcoPuglia.serviceImp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.Gruppo1.EcoPuglia.component.AppCostants;
import it.Gruppo1.EcoPuglia.config.LocalDateTimeAdapter;
import it.Gruppo1.EcoPuglia.model.AriaModel;
import it.Gruppo1.EcoPuglia.model.EnergiaModel;
import it.Gruppo1.EcoPuglia.repository.IEnergiaRepository;
import it.Gruppo1.EcoPuglia.service.ILetturaFileService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LetturaFileService implements ILetturaFileService {
    private int totalLimit;
    private final IEnergiaRepository iEnergiaRepository;
    private static final Logger logger = LoggerFactory.getLogger(LetturaFileService.class);
    private final AppCostants appCostants;

    @Autowired
    public LetturaFileService(AppCostants appCostants, IEnergiaRepository iEnergiaRepository) {
        this.appCostants = appCostants;
        this.iEnergiaRepository = iEnergiaRepository;
    }

    @Override
    public void runService() {
        try {
            letturaFileCsv(appCostants.getPathEnergia());
        } catch (IOException e) {
            logger.error("Errore nella lettura del file | File: " + appCostants.getPathEnergia(), e);
        }
        letturaFileJson(appCostants.getPathAria());
    }

    @Transactional
    protected void letturaFileCsv(String path) throws IOException {
        try {
            FileReader fileReader = new FileReader(path);
            CSVReader csvReader = new CSVReader(fileReader);
            EnergiaModel energiaModel;
            String[] lettura;
            lettura = csvReader.readNext();
            while ((lettura = csvReader.readNext()) != null) {
                energiaModel = new EnergiaModel();
                energiaModel.setProvincia(lettura[0]);
                energiaModel.setComune(lettura[1]);
                energiaModel.setFonte(lettura[2]);
                energiaModel.setPotenza(Float.parseFloat(lettura[3].replace(",", ".")));
                iEnergiaRepository.save(energiaModel);
                System.out.println(energiaModel);
            }
        } catch (FileNotFoundException e) {
            logger.error("File non trovato o inesistente | Path fornita: " + appCostants.getPathEnergia(), e);
        } catch (CsvValidationException e) {
            logger.error("File csv non valido | File: " + appCostants.getPathEnergia(), e);
        }
    }

    private void letturaFileJson(String path) {
        try (Reader reader = new FileReader(path)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter(DateTimeFormatter.ISO_DATE_TIME))
                    .create();
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            JsonObject result =  json.getAsJsonObject("result");
            JsonArray records = result.getAsJsonArray("records");
            for (int i = 0; i < records.size(); i++) {
                AriaModel ariaModel = gson.fromJson(records.get(i), AriaModel.class);
                System.out.println(ariaModel + " " + i);
            }
        } catch (Exception e) {
            logger.error("Errore nella lettura del file | File: " + appCostants.getPathAria() + " | Errore: " + e);
        }
    }
}