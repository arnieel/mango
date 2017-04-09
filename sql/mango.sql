CREATE SCHEMA IF NOT EXISTS TGP;
USE TGP;

CREATE TABLE USER (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    FIRSTNAME VARCHAR(255),
    LASTNAME VARCHAR(255),
    DELETED BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE LOG (
	ID INT PRIMARY KEY AUTO_INCREMENT,
	USER_ID INT,
	TIME DATETIME,
	IN BOOLEAN,
	IMAGE_PATH VARCHAR(1000),
	FOREIGN KEY(USER_ID) REFERENCES USER(ID)
);

COMMIT;