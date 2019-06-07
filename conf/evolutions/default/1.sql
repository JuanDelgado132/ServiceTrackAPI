# Initial version

# --- !Ups
CREATE TABLE USERS(
    id CHAR(36) NOT NULL,
    firstName VARCHAR(40),
    lastName VARCHAR (40),
    email VARCHAR(50),
    admin BIT,
    address VARCHAR(50),
    phone VARCHAR(15),
    userPassword VARCHAR (255),
    PRIMARY KEY (id),
    UNIQUE KEY unique_email (email)
);

CREATE TABLE CLIENTS (
    id CHAR(36) NOT NULL,
    firstName VARCHAR(40),
    lastName VARCHAR (40),
    gender VARCHAR (15),
    comments VARCHAR(200),
    birthday varchar(100), active int(1),
    PRIMARY KEY (id)
);
CREATE TABLE SERVICES (
    id CHAR(36) NOT NULL,
    serviceName VARCHAR(50),
    serviceDescription VARCHAR(50),
    days VARCHAR(50),
    time VARCHAR(50),
    active bit,
    PRIMARY KEY (id)
);

CREATE TABLE CLIENT_SERVICE_REL
(
    id        CHAR(36),
    serviceId CHAR(36)

);

INSERT INTO USERS (id,firstName,lastName,email,admin,address,phone,userPassword)
VALUES('9ee0293b-36c7-4ae3-ad7a-c83e8afcbde6','John','Doe','admin@gmail.com',1,'785 W Street Brownsville, Tx', '7853359856','qwaszx');

INSERT INTO CLIENTS (id, firstName, lastName, gender, comments, birthday)
VALUES('f968e4f5-1afd-44db-883d-1dcd3915d761', 'Juan', 'Delgado', 'Male', 'Test', '02-14-1998');

INSERT INTO SERVICES (id, serviceName, serviceDescription, days, time, active)
VALUES('6b397900-e756-4fab-a9ce-3c6ba68c44f1','default service', 'Initial Service', 'M-F', '9-5',true);
-- !Downs

DROP TABLE USERS;
DROP TABLE CLIENTS;
DROP TABLE SERVICES;

