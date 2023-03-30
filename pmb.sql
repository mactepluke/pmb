/* Setting up PMB DB */
CREATE DATABASE PMB_DB;
USE PMB_DB;


CREATE TABLE IF NOT EXISTS USER
(
    USER_ID    INT PRIMARY KEY AUTO_INCREMENT,
    EMAIL      VARCHAR(40) NOT NULL,
    PASSWORD   VARCHAR(20) NOT NULL,
    FIRST_NAME VARCHAR(30) NOT NULL,
    LAST_NAME  VARCHAR(30) NOT NULL,
    VERIFIED   BOOL        NOT NULL,
    ENABLED    BOOL        NOT NULL
);

CREATE TABLE IF NOT EXISTS SPOT_ACCOUNT
(
    SPOT_ACCOUNT_ID INT PRIMARY KEY AUTO_INCREMENT,
    USER_ID         INT        NOT NULL,
    CURRENCY        VARCHAR(3) NOT NULL,
    CREDIT          FLOAT      NOT NULL,
    ENABLED         BOOL       NOT NULL
);

CREATE TABLE IF NOT EXISTS BANK_ACCOUNT
(
    BANK_ACCOUNT_ID INT PRIMARY KEY AUTO_INCREMENT,
    USER_ID         INT         NOT NULL,
    NAME            VARCHAR(20) NOT NULL,
    IBAN            VARCHAR(34) NOT NULL,
    VERIFIED        BOOL        NOT NULL,
    ENABLED         BOOL        NOT NULL
);

CREATE TABLE IF NOT EXISTS RECIPIENT
(
    RECIPIENT_ID INT PRIMARY KEY AUTO_INCREMENT,
    RECIPIENT_USER_ID  INT       NOT NULL,
    USER_ID      INT             NOT NULL,
    ENABLED      BOOL            NOT NULL
);

CREATE TABLE IF NOT EXISTS PAYMENT
(
    PAYMENT_ID   INT PRIMARY KEY AUTO_INCREMENT,
    RECIPIENT_ID INT         NOT NULL,
    DESCRIPTION  VARCHAR(50) NOT NULL,
    GROSS_AMOUNT FLOAT       NOT NULL,
    NET_AMOUNT   FLOAT       NOT NULL,
    FEE_PERCENT  FLOAT       NOT NULL,
    CURRENCY     VARCHAR(3)  NOT NULL,
    DATE_TIME    DATETIME    NOT NULL,
    PROCESSED    BOOL        NOT NULL
);

CREATE TABLE IF NOT EXISTS OPERATION
(
    OPERATION_ID     INT PRIMARY KEY AUTO_INCREMENT,
    BANK_ACCOUNT_ID  INT      NOT NULL,
    SPOT_ACCOUNT_ID  INT      NOT NULL,
    DATE_TIME        DATETIME NOT NULL,
    AMOUNT           FLOAT    NOT NULL,
    WITHDRAWAL       BOOL     NOT NULL,
    PROCESSED        BOOL     NOT NULL
);

ALTER TABLE SPOT_ACCOUNT
    ADD FOREIGN KEY (USER_ID)
        REFERENCES USER (USER_ID);

ALTER TABLE BANK_ACCOUNT
    ADD FOREIGN KEY (USER_ID)
        REFERENCES USER (USER_ID);

ALTER TABLE RECIPIENT
    ADD FOREIGN KEY (USER_ID)
        REFERENCES USER (USER_ID),
    ADD FOREIGN KEY (RECIPIENT_USER_ID)
        REFERENCES USER (USER_ID);

ALTER TABLE PAYMENT
    ADD FOREIGN KEY (RECIPIENT_ID)
        REFERENCES RECIPIENT (RECIPIENT_ID);

ALTER TABLE OPERATION
    ADD FOREIGN KEY (BANK_ACCOUNT_ID)
        REFERENCES BANK_ACCOUNT (BANK_ACCOUNT_ID),
    ADD FOREIGN KEY (SPOT_ACCOUNT_ID)
        REFERENCES SPOT_ACCOUNT (SPOT_ACCOUNT_ID);


