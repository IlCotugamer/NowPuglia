package it.Gruppo1.EcoPuglia.component;

import it.Gruppo1.EcoPuglia.service.IFileDownloaderService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class FileManagerComponent {

    private IFileDownloaderService iFileDownloaderService;

    @Autowired
    public FileManagerComponent(IFileDownloaderService iFileDownloaderService) {
        this.iFileDownloaderService = iFileDownloaderService;
    }

    @Scheduled(fixedRate = 1000) // (1s * 60 = 1m * 60 = 1h) * 1000 = 3600000ms == 1h
    private void fileManager(){
        iFileDownloaderService.downloadAllData();
    }

}
