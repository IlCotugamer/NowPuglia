package it.Gruppo1.EcoPuglia.service;

public interface IFileDownloaderService {
    void downloadAllData();

    void getData(String url, String file, boolean is_json);
}
