USE EcoPuglia;
CREATE TABLE Utenti (
    IDUtente INT(6) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(10) NOT NULL,
    dataNascita DATE NOT NULL,
    tipoUtente INT(1) NOT NULL,
    username VARCHAR(20) NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE Citta (
    IDCitta INT(4) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nomeCitta VARCHAR(50) NOT NULL,
    longitudine VARCHAR(10) NOT NULL,
    latitudine VARCHAR(10) NOT NULL
);

CREATE TABLE Energia (
    IDEnergia INT(3) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    fonte INT(1) NOT NULL,
    potenza VARCHAR(4) NOT NULL,
    CODCitta INT(4) NOT NULL,
    Foreign Key (CODCitta) REFERENCES Citta(IDCitta)
);

CREATE TABLE Aria (
    IDAria INT(3) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    tipoArea INT(1) NOT NULL,
    dataMisurazione DATE NOT NULL
);

CREATE TABLE ValoriInquinanti (
    IDValoreInquinante INT(3) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    tipoValore VARCHAR(5) NOT NULL,
    valore VARCHAR(3) NOT NULL,
    CODAria int (3) NOT NULL,
    Foreign Key (CODAria) REFERENCES Aria(IDAria)
);

