package it.Gruppo1.NowPuglia.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Setter
@ToString
@Component
public class AppCostants {
    private String energiaUrl = "http://www.opendataipres.it/dataset/85045ff8-5e20-4f26-8585-2f2ba0a5c3d5/resource/e5e12518-69d8-46b8-8068-9dc89d356cad/download/richieste-connessione-puglia.csv&type=resource";
    private String ariaUrl = "https://dati.puglia.it/ckan/api/3/action/datastore_search?resource_id=8c96ee29-f19f-4d2f-9bb7-27d057589050";
    private String finalAriaUrl;
    private int ariaLimit;
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(AppCostants.class);

    @Autowired
    public AppCostants(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getCorrectAriaUrl() {
        String data = webClient.get().uri(ariaUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonObject = objectMapper.readTree(data);
            int ariaLimit = jsonObject.get("result").get("total").asInt();
            finalAriaUrl = ariaUrl + "&limit=" + ariaLimit;
        } catch (JsonProcessingException e) {
            logger.error("Errore nella lettura del json | URL: {} | Error: {}", this.ariaUrl, e.getClass().getName());
        }

        return finalAriaUrl;
    }
}
