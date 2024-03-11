package it.Gruppo1.EcoPuglia.service;

import java.io.IOException;

public interface ILetturaFileService {
    void runService();
    void letturaFileCsv() throws IOException;
    void letturaFileJson();
}
