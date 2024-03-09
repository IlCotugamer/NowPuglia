package it.Gruppo1.EcoPuglia.component;

import it.Gruppo1.EcoPuglia.serviceImp.FileDownloadService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatiPugliaRunner implements ApplicationRunner {
    private final FileDownloadService fileDownloadService;

    public DatiPugliaRunner(FileDownloadService fileDownloadService){
        this.fileDownloadService = fileDownloadService;
    }

    @Override
    public void run(ApplicationArguments args) {
        fileDownloadService.downloadAllData();
    }
}
