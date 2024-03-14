package it.Gruppo1.EcoPuglia.component;

import it.Gruppo1.EcoPuglia.service.IDataManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataManagerComponent {

    private final IDataManagerService iDataManagerService;

    @Autowired
    public DataManagerComponent(IDataManagerService iDataManagerService) {
        this.iDataManagerService = iDataManagerService;
    }

    @Scheduled(fixedRate = 60 * 60 * 24 * 1000) // (1s * 60 = 1m * 60 = 1h) * 1000 = 3600000ms == 1h
    private void fileManager(){
        iDataManagerService.runDataManager();
    }
}