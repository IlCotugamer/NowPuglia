package it.Gruppo1.EcoPuglia.component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class AppCostants {
    private final String pathAria = "src/main/resources/Aria/inq.json";
    private final String pathEnergia = "src/main/resources/Energia/energia.csv";
    private int totalLimit;
}
