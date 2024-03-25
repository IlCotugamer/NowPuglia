package it.Gruppo1.EcoPuglia.component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Component
public class AppCostants {
    private final String pathAria = "src/main/resources/Aria/inq.json";
    private final String pathEnergia = "src/main/resources/Energia/energia.csv";
    private final String pathLimit = "src/main/resources/limit.txt";
    private int totalLimit;

    private static final Logger logger = Logger.getLogger(AppCostants.class.getName());

    private void saveLimit() {
        try {
            FileWriter fileWriter = new FileWriter(pathLimit);
            fileWriter.write(totalLimit);
            fileWriter.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Errore nella scrittura del file | File: " + pathLimit + " | Errore: " + e);
        }
    }

    public void setTotalLimit(int totalLimit) {
        this.totalLimit = totalLimit;
        saveLimit();
    }

    public int readTotalLimit() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathLimit));
            String line = reader.readLine();
            reader.close();

            return Integer.parseInt(line);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Errore nella lettura del file | File: " + pathLimit + " | Errore: " + e);
            return -1;
        }
    }
}
