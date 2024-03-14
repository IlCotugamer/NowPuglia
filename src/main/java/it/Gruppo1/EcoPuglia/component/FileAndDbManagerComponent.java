package it.Gruppo1.EcoPuglia.component;

import it.Gruppo1.EcoPuglia.service.IFileDownloaderService;
import it.Gruppo1.EcoPuglia.service.ILetturaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileAndDbManagerComponent {

    private final IFileDownloaderService iFileDownloaderService;
    private final ILetturaFileService iLetturaFileService;

    @Autowired
    public FileAndDbManagerComponent(IFileDownloaderService iFileDownloaderService, ILetturaFileService iLetturaFileService) {
        this.iFileDownloaderService = iFileDownloaderService;
        this.iLetturaFileService = iLetturaFileService;
    }

    @Scheduled(fixedRate = 100 * 1000) // (1s * 60 = 1m * 60 = 1h) * 1000 = 3600000ms == 1h
    private void fileManager(){
        iFileDownloaderService.downloadAllData();
        iLetturaFileService.runService();
    }

}
