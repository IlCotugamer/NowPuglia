CREATE TABLE Energia (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    provincia VARCHAR(100) NOT NULL ,
    comune VARCHAR(100) NOT NULL ,
    fonte VARCHAR(100) NOT NULL ,
    potenza FLOAT NOT NULL
);

CREATE TABLE Aria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dataDiMisurazione DATETIME NOT NULL,
    idStation INT NOT NULL,
    denominazione VARCHAR(255) NOT NULL,
    comune VARCHAR(255) NOT NULL,
    provincia VARCHAR(255) NOT NULL,
    longitude VARCHAR(255) NOT NULL,
    latitude VARCHAR(255) NOT NULL,
    tipologiaDiArea VARCHAR(255) NOT NULL,
    tipologiaDiStazione VARCHAR(255) NOT NULL,
    rete VARCHAR(255) NOT NULL,
    interesseRete VARCHAR(255) NOT NULL,
    inquinanteMisurato VARCHAR(255) NOT NULL,
    valoreInquinanteMisurato INT NOT NULL,
    limite INT NOT NULL,
    unitaMisura VARCHAR(255) NOT NULL,
    superamenti INT NOT NULL,
    indiceQualita INT NOT NULL,
    classeQualita VARCHAR(255) NOT NULL
);
