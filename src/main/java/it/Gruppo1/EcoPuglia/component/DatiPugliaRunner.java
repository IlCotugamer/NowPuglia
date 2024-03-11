package it.Gruppo1.EcoPuglia.component;

import it.Gruppo1.EcoPuglia.serviceImp.FileDownloaderService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatiPugliaRunner implements ApplicationRunner {
    private final FileDownloaderService fileDownloaderService;

    public DatiPugliaRunner(FileDownloaderService fileDownloaderService){
        this.fileDownloaderService = fileDownloaderService;
    }

    @Override
    public void run(ApplicationArguments args) {
        fileDownloaderService.downloadAllData();
    }
}
