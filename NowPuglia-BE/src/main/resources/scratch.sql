USE EcoPuglia;

CREATE TABLE Abbonamenti (
    IDAbbonamento INT(1) AUTO_INCREMENT NOT NULL,
    tipoAbbonamento VARCHAR(30) NOT NULL,
    PRIMARY KEY (IDAbbonamento)
);

CREATE TABLE Utenti (
    IDUtente INT(6) AUTO_INCREMENT NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(10) NOT NULL,
    dataNascita DATE NOT NULL,
    tipoUtente INT(1) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    CODAbbonamento INT(1) NOT NULL,
    PRIMARY KEY (IDUtente),
    FOREIGN KEY (CODAbbonamento) REFERENCES Abbonamenti(IDAbbonamento)
);

CREATE TABLE Citta (
    IDCitta INT(4) AUTO_INCREMENT NOT NULL,
    nomeCitta VARCHAR(50) NOT NULL,
    longitudine VARCHAR(10) NOT NULL,
    latitudine VARCHAR(10) NOT NULL,
    PRIMARY KEY (IDCitta)
);

CREATE TABLE Energia (
    IDEnergia INT(3) AUTO_INCREMENT NOT NULL,
    fonte INT(1) NOT NULL,
    potenza VARCHAR(4) NOT NULL,
    CODCitta INT(4) NOT NULL,
    PRIMARY KEY (IDEnergia),
    FOREIGN KEY (CODCitta) REFERENCES Citta(IDCitta)
);

CREATE TABLE Aria (
    IDAria INT(3) AUTO_INCREMENT NOT NULL,
    tipoArea INT(1) NOT NULL,
    dataMisurazione DATE NOT NULL,
    CODCitta INT(4) NOT NULL,
    PRIMARY KEY (IDAria),
    FOREIGN KEY (CODCitta) REFERENCES Citta(IDCitta)
);

CREATE TABLE ValoriInquinanti (
    IDValoreInquinante INT(3) AUTO_INCREMENT NOT NULL,
    tipoValore VARCHAR(5) NOT NULL,
    valore VARCHAR(3) NOT NULL,
    CODAria int (3) NOT NULL,
    PRIMARY KEY (IDValoreInquinante),
    FOREIGN KEY (CODAria) REFERENCES Aria(IDAria)
);

