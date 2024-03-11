package it.Gruppo1.EcoPuglia.serviceImp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.Gruppo1.EcoPuglia.component.AppCostants;
import it.Gruppo1.EcoPuglia.model.AriaModel;
import it.Gruppo1.EcoPuglia.model.EnergiaModel;
import it.Gruppo1.EcoPuglia.repository.IEnergiaRepository;
import it.Gruppo1.EcoPuglia.service.ILetturaFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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
            letturaFileCsv();
        } catch (IOException e) {
            logger.error("Errore nella lettura del file | File: " + appCostants.getPathEnergia(), e);
        }
        letturaFileJson();
    }

    @Override
    public void letturaFileCsv() throws IOException {
        try {
            FileReader fileReader = new FileReader(appCostants.getPathEnergia());
            CSVReader csvReader = new CSVReader(fileReader);
            EnergiaModel energiaModel;
            String[] lettura;
            while ((lettura = csvReader.readNext()) != null) {
                energiaModel = new EnergiaModel();
                energiaModel.setProvincia(lettura[0]);
                energiaModel.setComune(lettura[1]);
                energiaModel.setFonte(lettura[2]);
                energiaModel.setPotenza(Float.parseFloat(lettura[3]));
                iEnergiaRepository.save(energiaModel);
            }
        } catch (FileNotFoundException e) {
            logger.error("File non trovato o inesistente | Path fornita: " + appCostants.getPathEnergia(), e);
        } catch (CsvValidationException e) {
            logger.error("File csv non valido | File: " + appCostants.getPathEnergia(), e);
        }
    }

    @Override
    public void letturaFileJson() {
        try (Reader reader = new FileReader(appCostants.getPathAria())) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            JsonObject result =  json.getAsJsonObject("result");
            JsonObject records = result.getAsJsonObject("records");
            List<AriaModel> recordList = new ArrayList<>();
            for (int i = 0; i < ; i++) {
                
            }
        } catch (Exception e) {
            logger.error("Errore nella lettura del file | File: " + appCostants.getPathAria());
        }
    }
}